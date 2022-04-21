package com.example.cvealert.fragment.subscription

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.cvealert.R
import com.example.cvealert.Cpe
import com.example.cvealert.api.MainViewModel
import com.example.cvealert.api.MainViewModelFactory
import com.example.cvealert.api.Repository
import com.example.cvealert.api.model.cves.MyCves
import com.example.cvealert.database.MyViewModel
import com.example.cvealert.database.cve.Cve
import com.example.cvealert.database.subscription.Part
import com.example.cvealert.database.subscription.Subscription

class AddOrEditSubscriptionFragment : Fragment() {

    private val args by navArgs<AddOrEditSubscriptionFragmentArgs>()

    private lateinit var myViewModel: MyViewModel
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
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

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
        val cpe = Cpe(
            vendor = cpeVendorET.text.toString().ifEmpty { "*" },
            product = cpeProductET.text.toString().ifEmpty { "*" },
            version = cpeVersionET.text.toString().ifEmpty { "*" },
            swEdition = cpeSwEditionET.text.toString().ifEmpty { "*" },
            update = cpeUpdateET.text.toString().ifEmpty { "*" },
            part = getSelectedPart()
        )

        cpeStringTV.text = cpe.generateString()
    }

    private fun saveSubscription() {

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

        if (isEditSubscription()) {
            subscription.id = args.selectedSubscription!!.id
            toastText = "Subscription saved!"
            myViewModel.updateSubscription(subscription)

            //todo: delete no more used cves from DB if cpe-string is edit

        } else {
            toastText = "Subscription added!"
            myViewModel.insertSubscription(subscription)
        }

        searchAndInsertCves(subscription)
        clearInputs()
        Toast.makeText(requireContext(), toastText, Toast.LENGTH_LONG).show()

        //todo: update cves, find cves with this subscription


        //todo: background job, observer, will be killed if the navigation is changed
//        view?.findNavController()?.navigate(
//            R.id.action_addSubscriptionFragment_to_subscriptionsFragment
//        )
    }

    private fun searchAndInsertCves(subscription: Subscription) {
        val repository = Repository()
        val mainViewModelFactory = MainViewModelFactory(repository)
        val mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        mainViewModel.getCves(
            resultsPerPage = 20,
            apiKey = null,
            //todo: do not escape cpe string
            cpeMatchString = Cpe.generateStringFromSubscription(subscription),
            pubStartDate = null,
            pubEndDate = null,
            startIndex = null
        )

        //todo: do not use this observer. Job will be killed if navigation changed
        mainViewModel.cvesResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful && response.body() != null && response.body()?.result != null) {

                Log.v("Response", "got response!!!")
                Log.v("Response", response.body()?.resultsPerPage.toString())
                Log.v("Response", response.body()?.startIndex.toString())
                Log.v("Response", response.body()?.totalResults.toString())
                val myCves: MyCves = response.body()!!
                val generatedDbCves: List<Cve> = myCves.generateDbCves()
                myViewModel.insertCves(generatedDbCves)
            } else {
                Log.v("Response", response.errorBody().toString())
                Log.v("Response", response.code().toString())
            }

            //todo: call this function again if numberOfPages > 1
        })
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