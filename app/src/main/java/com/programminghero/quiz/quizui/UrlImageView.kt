package com.programminghero.quiz.quizui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import coil.compose.ImagePainter
import coil.compose.rememberImagePainter


@Composable
fun UrlImageView(
    imageUrl: String,
    placeholderResId: Int,
    modifier: Modifier = Modifier.fillMaxSize(),
) {
    val painter: ImagePainter = rememberImagePainter(
        data = imageUrl,
        builder = {
            crossfade(true)
            placeholder(placeholderResId) // Placeholder image resource
        }
    )

    Box(
        modifier = modifier
    ) {
        if (painter.state is ImagePainter.State.Loading) {
            // Show a blurred placeholder or loading spinner
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(androidx.compose.ui.graphics.Color.Gray)
            )
        } else {
            if (painter.state is ImagePainter.State.Error || imageUrl.isEmpty()) {
                // Show the placeholder image if loading fails or if imageUrl is empty
                Image(
                    painter = painterResource(placeholderResId),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Load and display the image
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

