package com.example.newtp2.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newtp2.R
import com.example.newtp2.ShowListActivity
import com.example.newtp2.models.Item
import kotlinx.android.synthetic.main.todo_item.view.*

class ItemListAdapter (
    var todos: MutableList<Item>,
    var mContext: Context
) : RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {
    inner class ItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ItemListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.itemView.apply {
            tvItem.text = todos[position].label
            cbDone.isChecked = todos[position].done
        }
    }

    fun display(new_todos : List<Item>){
        todos.addAll(new_todos)
        notifyDataSetChanged()
    }

    fun addItem(item : Item) {
        todos.add(item)
        notifyDataSetChanged()
    }
}