package com.example.newtp2

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val loginPref = findPreference<EditTextPreference>("login")
        val passPref = findPreference<EditTextPreference>("passe")
        val loginEntered = requireArguments().getString("lastPseudo")
        val mdp = requireArguments().getString("password")
        if (loginPref != null) {
            loginPref.text=loginEntered
        }
        if (passPref != null) {
            passPref.text=mdp
        }
    }
}