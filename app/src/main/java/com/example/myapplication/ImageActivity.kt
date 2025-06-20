package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.example.myapplication.data.UnsplashDataItem

class ImageActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val image = intent.extras!!.get("image") as UnsplashDataItem
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.urls?.regular)
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .clickable (onClick = { finish() })
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}