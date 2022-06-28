package com.example.newtp2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newtp2.adapters.TodolistChoiceAdapter
import kotlinx.android.synthetic.main.activity_choix_list.*

class ChoixListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        val todolists = mutableListOf("Choix 1", "Choix 2", "Choix 3")

        val adapter = TodolistChoiceAdapter(todolists, this)
        rvTodos.adapter = adapter
        rvTodos.layoutManager = LinearLayoutManager(this)


        btnAddTodo.setOnClickListener {
            val new_todo = etNewList.text.toString()
            if (new_todo.isBlank()) {
                Toast.makeText(this, "Text cannot be blank", Toast.LENGTH_SHORT).show()
            }
            else if (repeatedElement(new_todo, todolists)) {
                Toast.makeText(this, "This todolist exists already", Toast.LENGTH_SHORT).show()
            }
            else {
                todolists.add(new_todo)
                adapter.notifyItemInserted(todolists.size - 1)
                hideSoftKeyboard(it)
            }
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun repeatedElement(elementName: String, list: MutableList<String>): Boolean {
        return list.filter{it == elementName}.isNotEmpty()
    }
}