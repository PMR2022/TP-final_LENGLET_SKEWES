package com.example.newtp2.api

class User(private var login: String = "", private var Lists: MutableList<TodoList> = mutableListOf<TodoList>()) {
    constructor(Lists: MutableList<TodoList>) : this("", Lists);
    fun getLists(): MutableList<TodoList> {
        return Lists;
    }

    override fun toString(): String{
        return "User "+login+": "+Lists.size+" lists";
    }
}