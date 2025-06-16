package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.MainScreen
import com.example.myapplication.ui.DetailScreen
import com.example.myapplication.ui.ImageScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("detail_screen/{imageName}") { backStackEntry ->
            val imageName = backStackEntry.arguments?.getString("imageName")
            DetailScreen(navController, imageName=imageName)
        }
        composable("image_screen/{imageName}") { backStackEntry ->
            val imageName = backStackEntry.arguments?.getString("imageName")
            ImageScreen(navController, imageName=imageName)
        }
    }
}