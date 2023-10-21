package com.programminghero.quiz.quizui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programminghero.quiz.R
import com.programminghero.quiz.model.Question

@Composable
fun QuestionArea(question: Question) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "${question.score} Point",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )


            Spacer(modifier = Modifier.height(10.dp))
            val url = question.questionImageUrl ?: ""

            // Wrap UrlImageView in a Box and set the size to 200dp
            Box(
                modifier = Modifier.size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                UrlImageView(
                    imageUrl = url,
                    placeholderResId = R.drawable.ic_question_mark
                )
            }

            Text(
                text = question.question,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp, top = 15.dp)
            )
        }
    }
}

