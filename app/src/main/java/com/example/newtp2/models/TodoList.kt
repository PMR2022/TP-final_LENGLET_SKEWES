package com.example.newtp2.models

import com.google.gson.annotations.SerializedName


//class TodoList(private var titre: String = "") {
//    private var Items = mutableListOf<Item>();
//
//    fun setTitle(newTitle: String) {
//        titre = newTitle;
//    }
//
//    override fun toString(): String{
//        return "List "+titre+": "+Items.size+" items";
//    }
//}

data class TodoList(
    val id: String,
    val label: String
)

data class GetTodoListResponse(
    val lists: List<TodoList>
)