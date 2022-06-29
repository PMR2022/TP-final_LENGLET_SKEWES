package com.example.newtp2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.room.Room
import com.example.newtp2.api.RetrofitInstance.api
import com.example.newtp2.bdd.DataBase
import com.example.newtp2.bdd.ListeDao
import com.example.newtp2.models.AuthenticationResponse
import com.example.newtp2.models.Item
import com.example.newtp2.models.User
import com.example.newtp2.models.UsersResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.awaitResponse



class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"
    private lateinit var sharedPrefTokens: SharedPreferences
    private var canLogin: Boolean = false
    private lateinit var listDao : ListeDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = this.application
        // SEQUENCE 3
        val database = Room.databaseBuilder(
            app,
            DataBase::class.java,
            "data-base"
        ).build()

        listDao = database.listDao()

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

            val connectedInfo = if (checkForInternet(this)) {
                "Logging with user $pseudo"
            } else {
                "No internet connection -> Offline Mode"
            }

            editor.apply {
                putString("lastPseudo", pseudo)
                apply()
            }
            editor_pref.apply {
                putString("login", pseudo)
                putString("passe", password)
                apply()
            }
//            Toast.makeText(this, "Pseudo $pseudo saved in Shared Preferences", Toast.LENGTH_SHORT).show()
            getUsers()
            authenticate(pseudo, password)

            // On peut recuperer le token dès sharedPreferences comme ça :
            val testtoken = sharedPrefTokens.getString(pseudo, "NO TOKEN")
            Log.e(TAG, "TEST TOKEN : " + testtoken)

            Log.e(TAG, "able to login?" + canLogin.toString())

            if (canLogin) {
                Intent(this, ChoixListActivity::class.java).also {
                    Toast.makeText(this@MainActivity, connectedInfo, Toast.LENGTH_LONG).show()
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
                    val users = data.users
                    saveUsers(users)
                    //Log.d(TAG, data.toString())
                    Log.e(TAG, data.toString())
                    withContext(Dispatchers.Main) {
                        //test_textView.text = data.users.toString()
                    }
                }
            } catch(e : Exception){
                val usersFound = listDao.getUsers()
                //saveUsers(usersFound)
                withContext(Dispatchers.Main) {
                    //test_textView.text = data.users.toString()
                }
                Log.e(TAG, "Exception found :\n $e")
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext,"ça marche pas",Toast.LENGTH_LONG).show()
                    //test_textView.text = "ça marche pas"
                }
            }
        }
    }

    private fun authenticate(user: String, pass: String) {
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch{
            try {
                val response: Response<AuthenticationResponse> = api.authenticate(user, pass)
                Log.e(TAG, response.toString())
                if (response.isSuccessful) {
                    val data: AuthenticationResponse = response.body()!!
                    //Log.d(TAG, data.toString())
                    Log.e(TAG, "HAAAASH : " + data.hash)
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

//  TODO: Soit on efface les commentaires et faison de notre façon, soit on laisse comme ça et on cite la source
//  Source : https://www.geeksforgeeks.org/how-to-check-internet-connection-in-kotlin/
    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private suspend fun saveUsers(usersFound: List<User>) {
        listDao.saveOrUpdateUsers(usersFound)
    }

}
