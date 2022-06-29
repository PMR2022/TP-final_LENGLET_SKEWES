package com.example.newtp2.models

import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity
data class TodoList(
    @PrimaryKey
    val id: String,
    var idUser : Int = 403,
    val label: String
)

data class GetTodoListResponse(
    val lists: List<TodoList>
)