package com.example.myapplication.api

import com.example.myapplication.data.SearchResponse
import com.example.myapplication.data.UnsplashDataItem
import com.example.myapplication.data.cb.UnsplashResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


private const val BASE_URL = "https://api.unsplash.com/"

private val retrofit by lazy {

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create<UnsplashApi>()
}

class UnsplashProvider() {
    fun fetchImages(cb: UnsplashResult) {
        retrofit.fetchPhotos().enqueue(object :
            Callback<List<UnsplashDataItem>> {
            override fun onResponse(
                call: Call<List<UnsplashDataItem>>,
                response: Response<List<UnsplashDataItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccess(response.body() !!)
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<List<UnsplashDataItem>>, t: Throwable) {
                cb.onDataFetchedFailed()
            }
        })
    }
    fun searchImages(query: String, cb: UnsplashResult) {
        retrofit.searchPhotos(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccess(response.body()!!.results)
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                cb.onDataFetchedFailed()
            }
        })
    }
    fun getPhotoDetails(photoId: String, cb: UnsplashResult) {
        retrofit.getPhotoDetails(photoId).enqueue(object : Callback<UnsplashDataItem> {
            override fun onResponse(
                call: Call<UnsplashDataItem>,
                response: Response<UnsplashDataItem>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccess(listOf(response.body()!!))
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<UnsplashDataItem>, t: Throwable) {
                cb.onDataFetchedFailed()
            }
        })
    }

}