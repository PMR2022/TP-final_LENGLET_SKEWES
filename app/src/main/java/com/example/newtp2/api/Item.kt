package com.example.newtp2.api


class Item(private var label: String = "",private var done: Boolean = false) {
    constructor(desc: String) : this(desc, false)

    fun setDesc(newDesc: String) {
        label = newDesc;
    }

    override fun toString(): String{
        return "- "+label+": ["+ (if(done) "x" else " ") + "]";
    }
}