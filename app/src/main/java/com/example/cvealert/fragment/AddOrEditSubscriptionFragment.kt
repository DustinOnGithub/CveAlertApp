package com.example.cvealert.fragment

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
import androidx.navigation.fragment.navArgs
import com.example.cvealert.R
import com.example.cvealert.data.Cpe
import com.example.cvealert.data.MyViewModel
import com.example.cvealert.data.Part
import com.example.cvealert.data.Subscription

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

        //todo: add ini fun
        clearInputs()

        if (args.selectedSubscription != null) {
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
        }

        updateCpeTV()

        saveSubscriptionBtn.text = resources.getString(R.string.add)

        bindListener()

        return view
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

        //todo: add update functionality

        myViewModel.insertSubscription(
            Subscription(
                id = 0,
                vendor = cpeVendorET.text.toString(),
                product = cpeProductET.text.toString(),
                pushUpNotification = pushUpNotificationCB.isChecked,
                part = getSelectedPart(),
                version = cpeVersionET.text.toString(),
                update = cpeUpdateET.text.toString(),
                isActive = isActiveCB.isChecked,
                edition = "",
                language = "",
                swEdition = cpeSwEditionET.text.toString(),
                targetSoftware = "",
                targetHardware = "",
                other = ""
            )
        )

        clearInputs()

        Toast.makeText(requireContext(), "Subscription added!", Toast.LENGTH_SHORT).show()
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