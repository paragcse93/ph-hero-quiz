package com.programminghero.quiz.quizui


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programminghero.quiz.model.Question

@Composable
fun AnswersList(
    question: Question,
    shuffledOptions: Map<String, String>,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit
) {
       Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        shuffledOptions.forEach { (answer, text) ->
            val isCorrect = answer == question.correctAnswer
            val isSelected = answer == selectedAnswer

            val backgroundColor = when {
                //                isSelected && isCorrect -> Color.Green
                //                isSelected && !isCorrect -> Color.Red
                else -> Color.White
            }

            val borderColor = if (isSelected && !isCorrect) {
                Color.Red
            } else if (isCorrect && selectedAnswer != null) {
                Color.Green
            } else {
                Color.White
            }

            Button(
                onClick = { onAnswerSelected(answer) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 4.dp
                    ) // Adjust the horizontal value as needed
                    .border(4.dp, borderColor, RoundedCornerShape(1.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor,
                ),
                shape = MaterialTheme.shapes.small,
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(
                    text = text,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}




