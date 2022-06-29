package com.example.newtp2.api

import com.example.newtp2.models.AuthenticationResponse
import com.example.newtp2.models.GetTodoListResponse
import com.example.newtp2.models.ResponseCats
import com.example.newtp2.models.UsersResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/*
interface API {

    @GET("/users/403")
    fun getUsers(
        @Header("hash") hash : String
    ) : Call<User>
}*/

interface API {

    @GET("/facts/random")
    fun getCatFacts(): Call<ResponseCats>

    @GET("users")
    fun getUsers(@Header("hash") hash: String): Call<UsersResponse>

    @POST("authenticate")
    suspend fun authenticate(@Query("user") user: String, @Query("password") password: String): Response<AuthenticationResponse>

    @GET("lists")
    fun getTodoLists(@Header("hash") hash: String): Call<GetTodoListResponse>

}