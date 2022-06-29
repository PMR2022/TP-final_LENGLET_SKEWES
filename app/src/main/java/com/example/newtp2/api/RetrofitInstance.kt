package com.example.newtp2.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

const val BASE_URL = "http://tomnab.fr/todo-api/"
//    const val BASE_URL = "https://cat-fact.herokuapp.com"

    val api: API by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }
}