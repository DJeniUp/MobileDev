package com.example.myapplication.api

import com.example.myapplication.data.SearchResponse
import com.example.myapplication.data.UnsplashDataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val AUTHORIZATION_CLIENT_ID = "Client-ID"
private const val ACCESS_KEY = "asCtHGIGV5aPzQ4q1ULUqfLvDrwpjJfQYdrm-sI6IXg"

interface UnsplashApi {

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("photos")
    fun fetchPhotos() : Call<List<UnsplashDataItem>>

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("search/photos")
    fun searchPhotos(@Query("query") query: String): Call<SearchResponse>

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("photos/{id}")
    fun getPhotoDetails(@Path("id") id: String): Call<UnsplashDataItem>
}