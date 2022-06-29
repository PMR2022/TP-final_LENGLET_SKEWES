package com.example.newtp2.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//class User(private var login: String = "", private var Lists: MutableList<TodoList> = mutableListOf<TodoList>()) {
//    constructor(Lists: MutableList<TodoList>) : this("", Lists);
//    fun getLists(): MutableList<TodoList> {
//        return Lists;
//    }
//
//    override fun toString(): String{
//        return "User "+login+": "+Lists.size+" lists";
//    }
//}

@Entity
data class User(
    @PrimaryKey
    val id: Int,
    @SerializedName("pseudo")
    val pseudo: String
)

data class UsersResponse(
    val users: List<User>
)

data class AuthenticationResponse(
    @SerializedName("hash")
    val hash: String
)