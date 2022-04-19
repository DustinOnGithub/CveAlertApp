package com.example.cvealert.fragment.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cvealert.R
import com.example.cvealert.data.MyViewModel
import com.example.cvealert.data.setting.Setting

class SettingsFragment : Fragment() {

    private lateinit var myViewModel: MyViewModel
    private lateinit var apiKeyTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        apiKeyTextView = view.findViewById<TextView>(R.id.apiKeyView)

        // display api key
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        myViewModel.getSetting.observe(viewLifecycleOwner, Observer { setting ->
            if (setting != null) {
                apiKeyTextView.text = setting.apiKey
            }
        })

        view.findViewById<Button>(R.id.saveSettingsBtn).setOnClickListener {
            updateSettings()
        }

        return view
    }

    private fun updateSettings() {

        var currentSetting: Setting? = null
        myViewModel.getSetting.observe(viewLifecycleOwner) { setting ->
            currentSetting = setting
        }

        if (currentSetting != null) {
            currentSetting?.apiKey = apiKeyTextView.text.toString()
            myViewModel.updateSetting(currentSetting!!)
        } else {
            val apiKey = apiKeyTextView.text.toString()
            val setting = Setting(0, apiKey)
            myViewModel.insertSetting(setting)
        }

        Toast.makeText(requireContext(), "Setting saved!", Toast.LENGTH_SHORT).show()
    }

}