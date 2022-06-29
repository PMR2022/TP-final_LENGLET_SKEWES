package com.example.newtp2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.newtp2.api.RetrofitInstance.api
import com.example.newtp2.models.AuthenticationResponse
import com.example.newtp2.models.UsersResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.awaitResponse



class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"
    private lateinit var sharedPrefTokens: SharedPreferences
    private var canLogin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = getSharedPreferences("lastPseudo", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val sharedPref_settings = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor_pref = sharedPref_settings.edit()

        val lastPseudo = sharedPref.getString("lastPseudo", null)
        sharedPrefTokens = getSharedPreferences("tokens", 0)
        etPseudo.setText(lastPseudo)

        btnOk.setOnClickListener{
            val pseudo = etPseudo.text.toString()
            val password = etPassword.text.toString()
            editor.apply{
                putString("lastPseudo", pseudo)
                apply()
            }
            editor_pref.apply{
                putString("login",pseudo)
                putString("passe", password)
                apply()
            }
//            Toast.makeText(this, "Pseudo $pseudo saved in Shared Preferences", Toast.LENGTH_SHORT).show()
            authenticate(pseudo, password, this)

            // On peut recuperer le token dès sharedPreferences comme ça :
            val testtoken = sharedPrefTokens.getString(pseudo, "NO TOKEN")
            Log.e(TAG, "TEST TOKEN : " + testtoken)

            Log.e(TAG, "able to login?" + canLogin.toString())

            if (canLogin) {
                Intent(this, ChoixListActivity::class.java).also {
                    it.putExtra("EXTRA_pseudo", pseudo)
                    startActivity(it)
                }
            } else {
                Toast.makeText(this, "Unable to login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.miPreferences -> {
                val pseudo = etPseudo.text.toString()
                val password = etPassword.text.toString()
                Intent(this, SettingsActivity::class.java).also {
                    it.putExtra("EXTRA_PSEUDO", pseudo)
                    it.putExtra("PASSWORD", password)
                    startActivity(it)
                }
            }
        }
        return true
    }

    private fun getUsers(){
        val hash = sharedPrefTokens.getString("token", "")
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch{
            try {
                val response: Response<UsersResponse> = api.getUsers(hash!!).awaitResponse()
                if (response.isSuccessful) {
                    val data: UsersResponse = response.body()!!
                    //Log.d(TAG, data.toString())
                    Log.e(TAG, data.toString())
                    withContext(Dispatchers.Main) {
                        test_textView.text = data.users.toString()
                    }
                }
            } catch(e : Exception){
                Log.e(TAG, "Exception found :\n $e")
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext,"ça marche pas",Toast.LENGTH_LONG).show()
                    test_textView.text = "ça marche pas"
                }
            }
        }
    }

    private fun authenticate(user: String, pass: String, context: Context) {
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch{
            try {
                val response: Response<AuthenticationResponse> = api.authenticate(user, pass)
                Log.e(TAG, response.toString())
                if (response.isSuccessful) {
                    val data: AuthenticationResponse = response.body()!!
                    //Log.d(TAG, data.toString())
                    Log.e(TAG, "HAAAASH : " + data.hash)

//                  Saving token for specific user + "general" token (getting users for example)
                    sharedPrefTokens.edit().putString("token", data.hash).apply()
                    sharedPrefTokens.edit().putString(user, data.hash).apply()
                    canLogin = true
                    return@launch
                } else {
                    Log.e(TAG, "INVALID USER")
                    return@launch
                }
            } catch(e : Exception){
                Log.e(TAG, "Exception found :\n $e")
                return@launch
            }
        }
    }

}
