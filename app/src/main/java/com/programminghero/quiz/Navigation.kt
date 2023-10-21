package com.programminghero.quiz

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "Dashboard") {
        composable("Dashboard") {
            Dashboard(navController)
        }
        composable("QuizScreen") {
            QuizScreen(navController)
        }
    }
}