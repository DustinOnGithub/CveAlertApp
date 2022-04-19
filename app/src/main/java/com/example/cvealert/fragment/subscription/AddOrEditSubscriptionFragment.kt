package com.example.cvealert.fragment.subscription

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cvealert.R
import com.example.cvealert.Cpe
import com.example.cvealert.data.MyViewModel
import com.example.cvealert.data.subscription.Part
import com.example.cvealert.data.subscription.Subscription

class AddOrEditSubscriptionFragment : Fragment() {

    private val args by navArgs<AddOrEditSubscriptionFragmentArgs>()

    private lateinit var myViewModel: MyViewModel
    private lateinit var cpePartRG: RadioGroup
    private lateinit var cpeVendorET: EditText
    private lateinit var cpeProductET: EditText
    private lateinit var cpeUpdateET: EditText
    private lateinit var cpeVersionET: EditText
    private lateinit var cpeSwEditionET: EditText
    private lateinit var pushUpNotificationCB: CheckBox
    private lateinit var isActiveCB: CheckBox
    private lateinit var cpeStringTV: TextView
    private lateinit var saveSubscriptionBtn: Button

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

        cpePartRG = view.findViewById(R.id.HardOrSoftwareRG)
        cpeVendorET = view.findViewById(R.id.vendorET)
        cpeProductET = view.findViewById(R.id.productET)
        cpeUpdateET = view.findViewById(R.id.updateET)
        cpeVersionET = view.findViewById(R.id.versionET)
        cpeSwEditionET = view.findViewById(R.id.swEditionET)
        pushUpNotificationCB = view.findViewById(R.id.pushUpCB)
        isActiveCB = view.findViewById(R.id.isActiveCB)
        cpeStringTV = view.findViewById(R.id.cpeStringTV)
    }

    /**
     * clear input fields; if edit-mode, fill input fields
     */
    private fun ini() {

        clearInputs()

        if (isEditSubscription()) {
            cpeVendorET.setText(args.selectedSubscription!!.vendor)
            cpeSwEditionET.setText(args.selectedSubscription!!.swEdition)
            cpeProductET.setText(args.selectedSubscription!!.product)
            cpeUpdateET.setText(args.selectedSubscription!!.update)
            cpeVersionET.setText(args.selectedSubscription!!.version)

            pushUpNotificationCB.isChecked = args.selectedSubscription!!.pushUpNotification
            isActiveCB.isChecked = args.selectedSubscription!!.isActive

            cpePartRG.check(
                when (args.selectedSubscription!!.part) {
                    Part.HARDWARE -> R.id.HardwareRB
                    Part.APPLICATIONS -> R.id.ApplicationRB
                    Part.UNDEFINED -> R.id.ApplicationRB
                    else -> R.id.OsRB
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

        cpePartRG.setOnCheckedChangeListener { _, _ -> updateCpeTV() }
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

        if (isEditSubscription()) {
            subscription.id = args.selectedSubscription!!.id
            toastText = "Subscription saved!"
            myViewModel.updateSubscription(subscription)
        } else {
            toastText = "Subscription added!"
            myViewModel.insertSubscription(subscription)
        }

        //todo: check for duplications or already included subscription

        clearInputs()
        Toast.makeText(requireContext(), toastText, Toast.LENGTH_LONG).show()

        view?.findNavController()?.navigate(
            R.id.action_addSubscriptionFragment_to_subscriptionsFragment
        )
    }

    private fun getSelectedPart(): Part {
        return when (cpePartRG.checkedRadioButtonId) {
            R.id.partUndefinedRB -> Part.UNDEFINED
            R.id.ApplicationRB -> Part.APPLICATIONS
            R.id.HardwareRB -> Part.HARDWARE
            else -> Part.OPERATING_SYSTEM
        }
    }

    private fun clearInputs() {
        cpePartRG.check(R.id.partUndefinedRB)
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