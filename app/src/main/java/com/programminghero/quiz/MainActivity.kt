package com.programminghero.quiz


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @OptIn(ExperimentalStdlibApi::class)
            setContent {
                App()
        }
    }
}

@Composable
fun App() {
    Navigation()
}


