package com.example.newtp2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.newtp2.api.API
import com.example.newtp2.api.RandomCatFacts
import com.example.newtp2.api.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.awaitResponse as awaitResponse1

//const val BASE_URL = "http://tomnab.fr/todo-api/"
const val BASE_URL = "https://cat-fact.herokuapp.com"

class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = getSharedPreferences("lastPseudo", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val sharedPref_settings = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor_pref = sharedPref_settings.edit()

        val lastPseudo = sharedPref.getString("lastPseudo", null)
        etPseudo.setText(lastPseudo)

        getRandomCatFacts()

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
            Toast.makeText(this, "Pseudo $pseudo saved in Shared Preferences", Toast.LENGTH_SHORT).show()
            Intent(this, ChoixListActivity::class.java).also {
                startActivity(it)
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

    private fun getRandomCatFacts(){
        val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
        GlobalScope.launch(Dispatchers.IO){
            try {
                val response: Response<RandomCatFacts> = api.getCatFacts().awaitResponse1()
                if (response.isSuccessful) {
                    val data: RandomCatFacts = response.body()!!
                    //Log.d(TAG, data.toString())
                    Log.d(TAG, data.text)
                    withContext(Dispatchers.Main) {
                        test_textView.text = data.text.toString()
                    }
                }
            } catch(e : Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext,"ça marche pas",Toast.LENGTH_LONG)
                    test_textView.text = "ça marche pas"
                }
            }
        }
    }


}
