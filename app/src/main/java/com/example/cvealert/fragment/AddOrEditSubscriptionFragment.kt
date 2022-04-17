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
import com.example.cvealert.R
import com.example.cvealert.data.MyViewModel
import com.example.cvealert.data.Part
import com.example.cvealert.data.Subscription

class AddOrEditSubscriptionFragment : Fragment() {

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

        clearInputs()
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

        val cpeVersion = "cpe:2.3.:"

        val part = when (getSelectedPart()) {
            Part.APPLICATIONS -> "a"
            Part.UNDEFINED -> "*"
            Part.HARDWARE -> "h"
            else -> "o"
        }

        val vendor = cpeVendorET.text.ifEmpty { "*" }
        val product = cpeProductET.text.ifEmpty { "*" }
        val version = cpeVersionET.text.ifEmpty { "*" }
        val update = cpeUpdateET.text.ifEmpty { "*" }
        val edition = "*"
        val language = "*"
        val swEdition = cpeSwEditionET.text.ifEmpty { "*" }
        val targetSoftware = "*"
        val targetHardware = "*"
        val other = "*"

        cpeStringTV.text =
            "${cpeVersion}:${part}:${vendor}:${product}:${version}:${update}:${edition}:${language}:${swEdition}:$targetSoftware:$targetHardware:$other"
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