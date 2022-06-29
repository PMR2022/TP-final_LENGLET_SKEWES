package com.example.newtp2.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newtp2.R
import com.example.newtp2.ShowListActivity
import com.example.newtp2.models.TodoList
import kotlinx.android.synthetic.main.todolist_choice.view.*

class TodolistChoiceAdapter (
    var todolists: List<TodoList>,
    var mContext: Context
) : RecyclerView.Adapter<TodolistChoiceAdapter.TodolistChoiceViewHolder>() {
    inner class TodolistChoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                Intent(mContext, ShowListActivity::class.java).also {
                    // TODO ADD necessary info to intent: id list
                    //val id_list = 1;
                    //it.putExtra("id_list", id_list)
                    mContext.startActivity(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodolistChoiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todolist_choice, parent, false)
        return TodolistChoiceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todolists.size
    }

    override fun onBindViewHolder(holder: TodolistChoiceViewHolder, position: Int) {
        holder.itemView.apply {
            tvTodolistChoice.text = todolists[position].label
        }
    }
}