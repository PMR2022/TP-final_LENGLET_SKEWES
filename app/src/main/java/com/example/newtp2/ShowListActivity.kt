package com.example.newtp2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.newtp2.adapters.ItemListAdapter
import com.example.newtp2.api.RetrofitInstance
import com.example.newtp2.models.*
import kotlinx.android.synthetic.main.activity_show_list.*
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.awaitResponse

class ShowListActivity : AppCompatActivity() {

    private var TAG = "ShowListActivity"
    private lateinit var sharedPrefTokens: SharedPreferences
    private lateinit var currentUser: String
    private var myitems = mutableListOf<Item>()
    private lateinit var adapter: ItemListAdapter
    private var idList : Int = 1991

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        sharedPrefTokens = getSharedPreferences("tokens", 0)

        setupRecyclerView()
        idList = intent.getIntExtra("id_list", 1991)
        //val pseudo: String? = intent.getStringExtra("EXTRA_pseudo")
        //currentUser = pseudo!!
        val pseudo : String? = sharedPrefTokens.getString("EXTRA_pseudo","")
        currentUser = pseudo!!
        getItems()

        btnAddItem.setOnClickListener{
            /*val title = etNewItem.text.toString()
            if (title.isBlank()) {
                Toast.makeText(this, "Text cannot be blank", Toast.LENGTH_SHORT).show()
            }
            else if (repeatedElement(title, myitems)) {
                Toast.makeText(this, "This item exists already", Toast.LENGTH_SHORT).show()
            }
            else {
                val todo = Item(title, false)
                myitems.add(todo)
                adapter.notifyItemInserted(myitems.size - 1)
                hideSoftKeyboard(it)
                etNewItem.text.clear()
            }*/

        }
    }


    private fun setupRecyclerView() {
        adapter = ItemListAdapter(todos = mutableListOf<Item>(), this)
        val item = findViewById<RecyclerView>(R.id.rvItems)
        item.adapter = adapter
        item.layoutManager = LinearLayoutManager(this, VERTICAL, false)
    }

    private fun hideSoftKeyboard(view: View) {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun repeatedElement(elementName: String, list: MutableList<Item>): Boolean {
        return list.filter{it.label == elementName}.isNotEmpty()
    }


    private fun getItems(){
        val hash = sharedPrefTokens.getString("token", "")
        //val id_list = sharedPrefTokens.getInt("id_list",1991)
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch{
            try {
                val response: Response<ItemResponse> = RetrofitInstance.api.getItems(idList, hash!!).awaitResponse()
                Log.d(TAG, "response found : \n " + response.toString() + " hash " + hash)
                if (response.isSuccessful) {
                    val data: ItemResponse = response.body()!!
                    Log.d(TAG, data.items.toString())
                    val itemsFound = data.items
                    Log.e(TAG, "todolists found : \n" + itemsFound.toString())
                    withContext(Dispatchers.Main) {
                        /*
                        for (newItem in itemsFound) {
                            myitems.add(newItem)
                            adapter.notifyItemInserted(myitems.size - 1)
                        }*/
                        adapter.display(itemsFound)
                    }
                }
            } catch(e : Exception){
                Log.e(TAG, "Exception found :\n $e")
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext,"ça marche pas",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}

