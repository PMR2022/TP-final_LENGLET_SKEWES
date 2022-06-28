package com.example.newtp2.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

/*
interface API {

    @GET("/users/403")
    fun getUsers(
        @Header("hash") hash : String
    ) : Call<User>
}*/

interface API {

    @GET("/facts/random")
    fun getCatFacts(): Call<RandomCatFacts>
}