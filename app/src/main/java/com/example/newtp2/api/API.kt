package com.example.newtp2.api

import com.example.newtp2.models.*
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

    @GET("lists/{id_list}/items")
    fun getItems(
        @Path(value = "id_list", encoded = true) idList : Int,
        @Header("hash") hash: String
    ) : Call<ItemResponse>

    @POST("lists/{id_list}/items")
    fun postItem(
        @Path(value = "id_list", encoded = true) idList : Int,
        @Query(value = "label", encoded = true) label : String,
        @Query(value = "url", encoded = true) url : String,
        @Header("hash") hash: String,
    ) : Call<Item>

}