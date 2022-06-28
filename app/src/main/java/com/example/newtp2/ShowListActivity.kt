package com.example.newtp2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newtp2.adapters.ItemListAdapter
import kotlinx.android.synthetic.main.activity_show_list.*

class ShowListActivity : AppCompatActivity() {
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
}

