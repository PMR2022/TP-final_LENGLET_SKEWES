package com.example.newtp2.models

import com.example.newtp2.api.Status
import com.google.gson.annotations.SerializedName


data class RandomCatFacts(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val deleted: Boolean,
    val source: String,
    val status: Status,
    val text: String,
    val type: String,
    val updatedAt: String,
    val used: Boolean,
    val user: String
)

data class ResponseCats (
    @SerializedName("text")
    val text: String
)