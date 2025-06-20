package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.example.myapplication.data.UnsplashDataItem


const val EXTRA_UNSPLASH_IMAGE = "extra_unsplash_image"

class DetailsActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val image = intent.extras!!.get("image") as UnsplashDataItem
            val id = intent.getStringExtra("photo_id")
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(image.urls?.regular)
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                                .clickable(onClick = {
                                    val intent = Intent(this@DetailsActivity, ImageActivity::class.java)
                                    intent.putExtra("image", image)

                                    startActivity(intent)
                                }),
                            contentScale = ContentScale.Crop
                        )
                        Row(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                                    .background(Color(0xAA000000), shape = RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                        ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location_pin),
                            contentDescription = "Location pin",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = image.created_at?:"Unknown",
                            color = Color.White
                        )
                    }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(image.user?.profile_image?.small)
                                    .build()
                            ),
                            contentDescription = "User avatar",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(image.user?.name?:"Unknown", color = Color.White)
                        }
                        IconButton(onClick = { /* download action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_download),
                                contentDescription = "Download",
                                tint = Color.White

                            )
                        }
                        IconButton(onClick = { /* like action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_favorite),
                                contentDescription = "Like",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { /* bookmark action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_bookmark),
                                contentDescription = "Bookmark",
                                tint = Color.White
                            )
                        }
                    }
                    Divider(color = Color.DarkGray, thickness = 1.dp)
                    Row{
                        Column(
                            modifier = Modifier
                                .weight(0.1f)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Column {
                                Text(text = "Camera", color = Color.White)
                                Text(text = "NIKON D3200", color = Color.Gray)
                            }

                            Column {
                                Text(text = "Focal Length", color = Color.White)
                                Text(text = "18.0mm", color = Color.Gray)
                            }
                            Column {
                                Text(text = "ISO", color = Color.White)
                                Text(text = "100", color = Color.Gray)
                            }
                        }

                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Column {
                                Text(text = "Aperture", color = Color.White)
                                Text(text = "f/5.0", color = Color.Gray)
                            }

                            Column {
                                Text(text = "Shutter Speed", color = Color.White)
                                Text(text = "1/125s", color = Color.Gray)
                            }

                            Column {
                                Text(text = "Dimensions", color = Color.White)
                                Text(text = "${image.width} x ${image.height}", color = Color.Gray)
                            }
                        }

                    }
                    Divider(color = Color.DarkGray, thickness = 1.dp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Views",
                                color = Color.White
                            )
                            Text(
                                text = "999999.9M",
                                color = Color.Gray
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Downloads",
                                color = Color.White
                            )
                            Text(
                                text = "9999.9M",
                                color = Color.Gray
                            )
                        }
                        Column() {
                            Text(
                                text = "Likes",
                                color = Color.White
                            )
                            Text(
                                text = image.likes.toString(),
                                color = Color.Gray
                            )
                        }
                    }
                    Divider(color = Color.DarkGray, thickness = 1.dp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { /* TODO */ },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            elevation = null
                        ) {
                            Text(text = "barcelona")
                        }
                        Button(
                            onClick = { /* TODO */ },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.DarkGray,
                                contentColor = Color.White
                            ),contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            elevation = null
                        ) {
                            Text(text = "spain")
                        }
                    }
                }


                Surface(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart),
                    shape = CircleShape,
                    color = Color.LightGray.copy(alpha = 0.5f),
                    elevation = 4.dp
                ) {
                    IconButton(onClick = { finish() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }

            }
        }
    }
}