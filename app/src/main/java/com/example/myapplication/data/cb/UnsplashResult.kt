package com.example.myapplication.data.cb

import com.example.myapplication.data.UnsplashDataItem

interface UnsplashResult {

    fun onDataFetchedSuccess(images: List<UnsplashDataItem>)

    fun onDataFetchedFailed()
}