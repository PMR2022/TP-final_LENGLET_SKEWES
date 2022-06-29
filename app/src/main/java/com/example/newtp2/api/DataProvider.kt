package com.example.newtp2.api

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.newtp2.bdd.DataBase
import com.example.newtp2.models.Item
import com.example.newtp2.models.TodoList

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class DataProvider(app: Application) {

    private var baseUrl = "http://tomnab.fr/todo-api/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun changeBaseUrl(newUrl : String) {
        // Pour changer le url ver le nouveau string -> newUrl
        baseUrl = newUrl
    }

    private val database = Room.databaseBuilder(
        app,
        DataBase::class.java,
        "data-base"
    ).build()

    private val listDao = database.listDao()



}
