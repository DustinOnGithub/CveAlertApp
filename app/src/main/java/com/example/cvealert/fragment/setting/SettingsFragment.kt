package com.example.cvealert.fragment.setting

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvealert.R
import com.example.cvealert.database.MyViewModelDb
import com.example.cvealert.database.setting.Setting
import com.example.cvealert.util.Constants


class SettingsFragment : Fragment() {

    private lateinit var myViewModelDb: MyViewModelDb
    private lateinit var apiKeyTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        apiKeyTextView = view.findViewById(R.id.apiKeyView)

        // display api key
        myViewModelDb = ViewModelProvider(this)[MyViewModelDb::class.java]
        myViewModelDb.getSetting.observe(viewLifecycleOwner) { setting ->
            if (setting != null) {
                apiKeyTextView.text = setting.apiKey
            }
        }

        view.findViewById<Button>(R.id.saveSettingsBtn).setOnClickListener { updateSettings() }

        val isDarkThemeOn =
            resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

        val gitHubIv: ImageView = view.findViewById(R.id.gitHubIV)

        gitHubIv.setImageResource(
            if (isDarkThemeOn) R.drawable.github_mark_light_64px
            else R.drawable.github_mark_64px
        )

        gitHubIv.setOnClickListener { redirectToGitHub() }
        view.findViewById<TextView>(R.id.gitHubTV).setOnClickListener { redirectToGitHub() }

        return view
    }

    private fun redirectToGitHub() {

        val viewIntent = Intent(
            "android.intent.action.VIEW",
            Uri.parse(Constants.GITHUB_REPO)
        )
        startActivity(viewIntent)
    }

    private fun updateSettings() {

        var currentSetting: Setting? = null
        myViewModelDb.getSetting.observe(viewLifecycleOwner) { setting ->
            currentSetting = setting
        }

        if (currentSetting != null) {
            currentSetting?.apiKey = apiKeyTextView.text.toString()
            myViewModelDb.updateSetting(currentSetting!!)
        } else {
            val apiKey = apiKeyTextView.text.toString()
            val setting = Setting(0, apiKey)
            myViewModelDb.insertSetting(setting)
        }

        Toast.makeText(requireContext(), "Setting saved!", Toast.LENGTH_SHORT).show()
    }

}