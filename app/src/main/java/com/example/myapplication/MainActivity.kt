package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api // Додано
import androidx.compose.material3.pulltorefresh.PullToRefreshBox // Переконайтесь, що це з material3
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState // Додано для більш сучасного підходу
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue // Додано
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue // Додано
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.example.myapplication.api.UnsplashProvider
import com.example.myapplication.data.UnsplashDataItem
import com.example.myapplication.data.cb.UnsplashResult


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity(), UnsplashResult {

    private var images by mutableStateOf<List<UnsplashDataItem>>(emptyList())
    private var loading by mutableStateOf(false)
    private lateinit var provider: UnsplashProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        provider = UnsplashProvider()

        setContent {
            LaunchedEffect(Unit) {
                loading = true
                provider.fetchImages(this@MainActivity)
            }

            val currentContext = LocalContext.current
            var searchText by rememberSaveable { mutableStateOf("") }

            PullToRefreshBox(
                isRefreshing = loading,
                onRefresh = {
                    loading = true
                    provider.fetchImages(this@MainActivity)
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            value = searchText,
                            onValueChange = { searchText = it },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search Icon",
                                    tint = Color.White
                                )
                            },
                            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                            label = { androidx.compose.material.Text("Search...", color = Color.Gray) },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions {
                                onSearchAction(searchText)
                            },
                        )
                    }


                    items(images) { image ->
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 16.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable(onClick = {
                                    val intent = Intent(currentContext, DetailsActivity::class.java)
                                    intent.putExtra("image", image)
                                    intent.putExtra("photo_id", image.id)
                                    currentContext.startActivity(intent)
                                }),
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(currentContext)
                                    .data(image.urls?.regular)
                                    .build()
                            ),
                            contentDescription = null ?: "Unsplash Image",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }

    override fun onDataFetchedSuccess(imagesData: List<UnsplashDataItem>) {
        this.images = imagesData
        loading = false
    }

    override fun onDataFetchedFailed() {
        loading = false
    }

    fun onSearchAction(query: String) {
        if (query.isNotBlank()) {
            loading = true
            provider.searchImages(query, this)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        androidx.compose.material3.Text("Preview Mode", color = Color.White, modifier = Modifier.align(Alignment.Center))
    }
}