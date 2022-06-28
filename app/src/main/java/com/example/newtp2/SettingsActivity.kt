package com.example.newtp2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: Utiliser des fragments plutot qu'une activité 'préférences'
        setContentView(R.layout.activity_settings)

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction();
        val pref_frag = SettingsFragment()
        fragmentTransaction.replace(android.R.id.content,pref_frag)
        fragmentTransaction.commit()
        val lastPseudo = intent.getStringExtra("EXTRA_PSEUDO")
        val password = intent.getStringExtra("PASSWORD")


        val bundle = Bundle()
        bundle.putString("lastPseudo",lastPseudo)
        bundle.putString("password",password)
        pref_frag.setArguments(bundle)
        /*
        val lastPseudo = intent.getStringExtra("EXTRA_PSEUDO")
        val lastPseudoMsg = "Dernier pseudo utilisé: $lastPseudo"
        tvLastPseudo.text = lastPseudoMsg*/
    }
}