package com.cvealert.cvealert.fragment.subscription

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.work.*
import com.cvealert.cvealert.R
import com.cvealert.cvealert.database.MyDatabase
import com.cvealert.cvealert.database.MyRepository
import com.cvealert.cvealert.database.MyViewModelDb
import com.cvealert.cvealert.database.subscription.Part
import com.cvealert.cvealert.database.subscription.Subscription
import com.cvealert.cvealert.workers.GetAndStoreCVEsByCPEWorker

class AddOrEditSubscriptionFragment : Fragment() {

    private val args by navArgs<AddOrEditSubscriptionFragmentArgs>()

    private lateinit var myViewModelDb: MyViewModelDb
    private lateinit var cpeVendorET: EditText
    private lateinit var cpeProductET: EditText
    private lateinit var cpeUpdateET: EditText
    private lateinit var cpeVersionET: EditText
    private lateinit var cpeSwEditionET: EditText
    private lateinit var pushUpNotificationCB: CheckBox
    private lateinit var isActiveCB: CheckBox
    private lateinit var cpeStringTV: TextView
    private lateinit var saveSubscriptionBtn: Button
    private lateinit var cpePartSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_or_edit_subscription, container, false)

        findViews(view)
        ini()

        return view
    }

    private fun findViews(view: View) {
        saveSubscriptionBtn = view.findViewById(R.id.saveSubscriptionBtn)
        myViewModelDb = ViewModelProvider(this)[MyViewModelDb::class.java]

        cpeVendorET = view.findViewById(R.id.vendorET)
        cpeProductET = view.findViewById(R.id.productET)
        cpeUpdateET = view.findViewById(R.id.updateET)
        cpeVersionET = view.findViewById(R.id.versionET)
        cpeSwEditionET = view.findViewById(R.id.swEditionET)
        pushUpNotificationCB = view.findViewById(R.id.pushUpCB)
        isActiveCB = view.findViewById(R.id.isActiveCB)
        cpeStringTV = view.findViewById(R.id.cpeStringTV)
        cpePartSpinner = view.findViewById(R.id.partSpinner)
    }

    /**
     * clear input fields; if edit-mode, fill input fields
     */
    private fun ini() {

        val spinnerOptions = resources.getStringArray(R.array.partOptions)
        clearInputs()
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerOptions)

        cpePartSpinner.adapter = spinnerAdapter

        if (isEditSubscription()) {
            cpeVendorET.setText(args.selectedSubscription!!.vendor)
            cpeSwEditionET.setText(args.selectedSubscription!!.swEdition)
            cpeProductET.setText(args.selectedSubscription!!.product)
            cpeUpdateET.setText(args.selectedSubscription!!.update)
            cpeVersionET.setText(args.selectedSubscription!!.version)

            pushUpNotificationCB.isChecked = args.selectedSubscription!!.pushUpNotification
            isActiveCB.isChecked = args.selectedSubscription!!.isActive

            cpePartSpinner.setSelection(
                when (args.selectedSubscription!!.part) {
                    Part.HARDWARE -> spinnerAdapter.getPosition(resources.getString(R.string.hardware))
                    Part.APPLICATIONS -> spinnerAdapter.getPosition(resources.getString(R.string.application))
                    Part.UNDEFINED -> spinnerAdapter.getPosition(resources.getString(R.string.undefined))
                    else -> spinnerAdapter.getPosition(resources.getString(R.string.operatingSystem))
                }
            )

            saveSubscriptionBtn.text = resources.getString(R.string.save)
        } else {
            saveSubscriptionBtn.text = resources.getString(R.string.add)
        }

        updateCpeTV()
        bindListener()
    }

    private fun isEditSubscription(): Boolean {
        return args.selectedSubscription != null
    }

    private fun bindListener() {

        cpePartSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                updateCpeTV()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateCpeTV()
            }
        }
        isActiveCB.setOnCheckedChangeListener { _, _ -> updateCpeTV() }
        pushUpNotificationCB.setOnCheckedChangeListener { _, _ -> updateCpeTV() }
        cpeVendorET.afterTextChanged { updateCpeTV() }
        cpeProductET.afterTextChanged { updateCpeTV() }
        cpeVersionET.afterTextChanged { updateCpeTV() }
        cpeUpdateET.afterTextChanged { updateCpeTV() }
        cpeSwEditionET.afterTextChanged { updateCpeTV() }

        saveSubscriptionBtn.setOnClickListener { saveSubscription() }
    }

    @SuppressLint("SetTextI18n")
    private fun updateCpeTV() {

        // DO NOT DELETE 'to.String()'; required because args
        val tempSubscription = Subscription(
            vendor = cpeVendorET.text.toString(),
            product = cpeProductET.text.toString(),
            version = cpeVersionET.text.toString(),
            swEdition = cpeSwEditionET.text.toString(),
            update = cpeUpdateET.text.toString(),
            part = getSelectedPart()
        )

        cpeStringTV.text = tempSubscription.getCPE23URL()
    }

    private fun saveSubscription() {

        //todo: check if api-key is valid
        val toastText: String
        val subscription = Subscription()
        subscription.vendor = cpeVendorET.text.toString()
        subscription.product = cpeProductET.text.toString()
        subscription.pushUpNotification = pushUpNotificationCB.isChecked
        subscription.part = getSelectedPart()
        subscription.version = cpeVersionET.text.toString()
        subscription.update = cpeUpdateET.text.toString()
        subscription.isActive = isActiveCB.isChecked
        subscription.swEdition = cpeSwEditionET.text.toString()

        if (!subscription.isValid()) {
            Toast.makeText(
                requireContext(),
                "Please fill in all required fields! This is Part, Vendor and Product",
                Toast.LENGTH_LONG
            )
                .show()

            return
        }

        //todo: check for duplications or already included subscription

        val myRepository = MyRepository(
            MyDatabase.getDatabase(requireContext()).settingDao(),
            MyDatabase.getDatabase(requireContext()).subscriptionDao(),
            MyDatabase.getDatabase(requireContext()).cveDao()
        )

        if (isEditSubscription()) {
            subscription.id = args.selectedSubscription!!.id
            toastText = "Subscription saved!"
            myViewModelDb.updateSubscription(subscription)
            myRepository.deleteCveWithSubscriptionSync(subscription)

        } else {

            subscription.id = myRepository.insertSubscriptionSync(subscription)

            toastText = if (subscription.id == -1) {
                "Subscription could not added!"
            } else {
                "Subscription added!"
            }
        }

        enqueueGetAndStoreCVEsByCPEWorker(subscription)

        clearInputs()
        Toast.makeText(requireContext(), toastText, Toast.LENGTH_LONG).show()

        view?.findNavController()?.navigate(
            R.id.action_addSubscriptionFragment_to_subscriptionsFragment
        )
    }

    private fun enqueueGetAndStoreCVEsByCPEWorker(subscription: Subscription) {
        val data = Data.Builder()
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
        val worker = OneTimeWorkRequestBuilder<GetAndStoreCVEsByCPEWorker>()
            .setConstraints(constraints.build())

        data.putString("cpe_string", subscription.getCPE23URL())
        data.putString("setting_id", subscription.id.toString())

        worker.setInputData(data.build())
        WorkManager.getInstance(requireContext()).enqueue(worker.build())
    }

    private fun getSelectedPart(): Part {

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.partOptions)
        )

        return when (cpePartSpinner.selectedItemId.toInt()) {
            spinnerAdapter.getPosition(resources.getString(R.string.hardware)) -> Part.HARDWARE
            spinnerAdapter.getPosition(resources.getString(R.string.application)) -> Part.APPLICATIONS
            spinnerAdapter.getPosition(resources.getString(R.string.undefined)) -> Part.UNDEFINED
            else -> Part.OPERATING_SYSTEM
        }
    }

    private fun clearInputs() {
        cpePartSpinner.setSelection(0)
        cpeVendorET.text.clear()
        cpeProductET.text.clear()
        cpeUpdateET.text.clear()
        cpeVersionET.text.clear()
        cpeSwEditionET.text.clear()
        pushUpNotificationCB.isChecked = true
        isActiveCB.isChecked = true
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}