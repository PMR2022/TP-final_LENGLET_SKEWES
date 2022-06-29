package com.example.newtp2.models

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

data class User(
    val id: Int,
    val pseudo: String
)

data class UsersResponse(
    val users: List<User>
)

data class AuthenticationResponse(
    @SerializedName("hash")
    val hash: String
)