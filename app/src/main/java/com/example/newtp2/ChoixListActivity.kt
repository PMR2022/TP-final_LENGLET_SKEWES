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
import com.example.newtp2.adapters.TodolistChoiceAdapter
import com.example.newtp2.api.RetrofitInstance
import com.example.newtp2.models.GetTodoListResponse
import com.example.newtp2.models.TodoList
import kotlinx.android.synthetic.main.activity_choix_list.*
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.awaitResponse

class ChoixListActivity : AppCompatActivity() {

    private var TAG = "ChoixListActivity"
    private lateinit var sharedPrefTokens: SharedPreferences
    private lateinit var currentUser: String
    private var todolists = mutableListOf<TodoList>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        sharedPrefTokens = getSharedPreferences("tokens", 0)

//      Setting up Recycler View
        val adapter = TodolistChoiceAdapter(todolists, this)
        rvTodos.adapter = adapter
        rvTodos.layoutManager = LinearLayoutManager(this)

        val pseudo: String? = intent.getStringExtra("EXTRA_pseudo")
        pseudo?.let { tvWelcomeUser.text = "$pseudo's ToDoLists" }
        currentUser = pseudo!!

        getTodoLists()

        btnAddTodo.setOnClickListener {
            val newTodoLabel = etNewList.text.toString()
            if (newTodoLabel.isBlank()) {
                Toast.makeText(this, "Text cannot be blank", Toast.LENGTH_SHORT).show()
            }
            else if (repeatedElement(newTodoLabel, todolists)) {
                Toast.makeText(this, "This todolist exists already", Toast.LENGTH_SHORT).show()
            }
            else {
                  // TODO: Ici je ferais ça: on fait le post de la nouvelle liste (tu peux t'inspirer de Aunthenticate), et puis on re appele "getTodoLists"
                  // TODO: C'est pas efficace mais ça devrait marcher, et bon, on fera mieux la prochaine fois :)
                todolists.add(TodoList("XXX", newTodoLabel))
                adapter.notifyItemInserted(todolists.size - 1)

                hideSoftKeyboard(it)
                etNewList.text.clear()

//                getTodoLists()
            }
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun repeatedElement(elementName: String, list: MutableList<TodoList>): Boolean {
        return list.filter{it.label == elementName}.isNotEmpty()
    }

    private fun getTodoLists(){
        val hash = sharedPrefTokens.getString(currentUser, "")
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch{
            try {
                val response: Response<GetTodoListResponse> = RetrofitInstance.api.getTodoLists(hash!!).awaitResponse()
                Log.e(TAG, "response found : \n " + response.toString())
                if (response.isSuccessful) {
                    val data: GetTodoListResponse = response.body()!!
                    //Log.d(TAG, data.toString())
                    val todoListsFound = data.lists
                    Log.e(TAG, "todolists found : \n" + todoListsFound.toString())
                    withContext(Dispatchers.Main) {
                        for (newTodoList in todoListsFound) {
                            todolists.add(newTodoList)
                            rvTodos.adapter!!.notifyItemInserted(todolists.size - 1)
                        }
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