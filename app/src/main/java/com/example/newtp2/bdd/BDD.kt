package com.example.newtp2.bdd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newtp2.models.Item
import com.example.newtp2.models.TodoList
import com.example.newtp2.models.User


@Database(entities = [TodoList::class, Item::class, User::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun listDao() : ListeDao

}


