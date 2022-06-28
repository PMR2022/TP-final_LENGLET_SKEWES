package com.example.newtp2.api


class TodoList(private var titre: String = "") {
    private var Items = mutableListOf<Item>();

    fun setTitle(newTitle: String) {
        titre = newTitle;
    }

    override fun toString(): String{
        return "List "+titre+": "+Items.size+" items";
    }
}