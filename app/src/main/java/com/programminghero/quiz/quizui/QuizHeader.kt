package com.programminghero.quiz.quizui

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuizHeader(
    currentQuestion: Int,
    totalQuestions: Int,
    currentScore: Int,
    onTimeout: () -> Unit
) {
    var progress by remember { mutableStateOf(1.0f) }

    DisposableEffect(currentQuestion) {
        val timer = QuizCountDownTimer(
            totalTime = 10000,
            countDownInterval = 1000,
            onTimeout = { onTimeout() },
            onTick = { newProgress -> progress = newProgress }
        )
        timer.start()

        onDispose {
            timer.cancel()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),

        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp),
                progress = progress,
                color = Color.Red
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Questions: $currentQuestion/$totalQuestions",
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Score: $currentScore",
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

class QuizCountDownTimer(
    private val totalTime: Long, // Total time in milliseconds
    countDownInterval: Long,
    private val onTimeout: () -> Unit,
    private val onTick: (Float) -> Unit // Callback for progress updates
) : CountDownTimer(totalTime, countDownInterval) {

    // Mutable state for progress
    private var progress by mutableStateOf(1.0f)

    override fun onTick(millisUntilFinished: Long) {
        // Calculate elapsed time and update progress
        val elapsedTime = totalTime - millisUntilFinished
        progress = elapsedTime.toFloat() / totalTime
        onTick(progress)
    }

    override fun onFinish() {
        progress = 0.0f
        onTimeout()
    }
}

