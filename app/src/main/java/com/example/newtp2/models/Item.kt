package com.example.newtp2.models


class Item(var label: String = "", var done: Boolean = false) {
    constructor(desc: String) : this(desc, false)

    fun setDesc(newDesc: String) {
        label = newDesc;
    }

    override fun toString(): String{
        return "- "+label+": ["+ (if(done) "x" else " ") + "]";
    }
}

data class ItemResponse(
    val items: List<Item>
)