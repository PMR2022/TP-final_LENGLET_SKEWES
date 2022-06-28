package com.example.newtp2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newtp2.R
import com.example.newtp2.Todo
import kotlinx.android.synthetic.main.todo_item.view.*

class ItemListAdapter (
    var todos: List<Todo>
) : RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {
    inner class ItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ItemListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.itemView.apply {
            tvItem.text = todos[position].title
            cbDone.isChecked = todos[position].isChecked
        }
    }
}