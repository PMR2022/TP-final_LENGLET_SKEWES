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
import com.example.newtp2.adapters.ItemListAdapter
import com.example.newtp2.api.RetrofitInstance
import com.example.newtp2.models.UsersResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show_list.*
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.awaitResponse

class ShowListActivity : AppCompatActivity() {

    private var TAG = "ShowListActivity"
    private var sharedPrefTokens = getSharedPreferences("tokens", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        var todoList = mutableListOf(
            Todo("todo 1", false),
            Todo("todo 2", true)
        )

        val adapter = ItemListAdapter(todoList)
        rvItems.adapter = adapter
        rvItems.layoutManager = LinearLayoutManager(this)

        btnAddItem.setOnClickListener{
            val title = etNewItem.text.toString()
            if (title.isBlank()) {
                Toast.makeText(this, "Text cannot be blank", Toast.LENGTH_SHORT).show()
            }
            else if (repeatedElement(title, todoList)) {
                Toast.makeText(this, "This item exists already", Toast.LENGTH_SHORT).show()
            }
            else {
                val todo = Todo(title, false)
                todoList.add(todo)
                adapter.notifyItemInserted(todoList.size - 1)
                hideSoftKeyboard(it)
                etNewItem.text.clear()
            }
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun repeatedElement(elementName: String, list: MutableList<Todo>): Boolean {
        return list.filter{it.title == elementName}.isNotEmpty()
    }

    // TODO Replace
    private fun getUsers(){
        val hash = sharedPrefTokens.getString("token", "")
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch{
            try {
                val response: Response<UsersResponse> = RetrofitInstance.api.getUsers(hash!!).awaitResponse()
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

}

