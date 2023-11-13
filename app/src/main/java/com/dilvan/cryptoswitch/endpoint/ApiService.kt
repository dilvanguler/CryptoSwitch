package com.dilvan.cryptoswitch.endpoint

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class Post(
    val id: Int,
    val title: String,
    val body: String
)

interface ApiService {
    @GET("posts/{id}")
    fun getPostById(@Path("id") postId: Int): Call<Post>
}