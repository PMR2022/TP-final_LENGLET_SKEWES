package com.example.newtp2.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Item(
    @PrimaryKey
    var id : Int,
    @ColumnInfo(name = "list_id")
    var idList : Int,
    var done: Boolean = false,
    var label: String = ""
    ) {
    constructor(label: String) : this(0,1992,false, label)

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