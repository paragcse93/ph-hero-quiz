package com.programminghero.quiz

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.programminghero.quiz.api.APIHandler
import com.programminghero.quiz.helper.StorageManager

import com.programminghero.quiz.model.QuizData
import com.programminghero.quiz.quizui.AnswersList
import com.programminghero.quiz.quizui.CustomCircularProgressIndicator
import com.programminghero.quiz.quizui.QuestionArea
import com.programminghero.quiz.quizui.QuizHeader
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(navController: NavController) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var currentScore by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val storageManager = remember { StorageManager(context) }

    // URL to fetch quiz data from
    val url = "https://herosapp.nyc3.digitaloceanspaces.com/quiz.json"

    var quizData by remember { mutableStateOf<QuizData?>(null) }
    var loadingError by remember { mutableStateOf<Exception?>(null) }

    val showDialog = remember { mutableStateOf(false) }

    // Fetch quiz data from the specified URL
    LaunchedEffect(Unit) {
        val apiHandler = APIHandler()
        apiHandler.loadQuizDataFromUrl(url) { data, error ->
            if (data != null) {
                quizData = data
            } else {
                showDialog.value = false
                //loadingError = error
            }
        }
    }


    // Get the list of questions from quizData
    val questions = quizData?.questions ?: emptyList()
    val totalQuestions = questions.size

    // Get the current question from the list based on the index
    val currentQuestion = questions.getOrNull(currentQuestionIndex)

    fun moveToNextQuestion() {
        currentQuestionIndex++
        selectedAnswer = null

        // Check if all questions are answered
        if (currentQuestionIndex >= totalQuestions) {
            // All questions answered, navigate back to the dashboard
            storageManager.setHighScore(currentScore)
            navController.navigate("dashboard")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Opps") },
                    text = { Text("Something Went wrong.") },
                    confirmButton = {
                        Button(
                            onClick = { showDialog.value = false },
                        ) {
                            Text("OK")
                        }
                    }
                )
            }
            when {
                // When quizData is loaded and there is a current question
                quizData != null && currentQuestion != null -> {
                    // Display the quiz header with the question number, total questions, and score
                    Spacer(modifier = Modifier.height(20.dp))
                    QuizHeader(
                        currentQuestion = currentQuestionIndex + 1,
                        totalQuestions = totalQuestions,
                        currentScore = currentScore,
                        onTimeout = { moveToNextQuestion() }

                    )

                    // Display the current question
                    QuestionArea(currentQuestion)

                    // Display the list of answer buttons
                    AnswersList(
                        question = currentQuestion,
                        shuffledOptions = currentQuestion.answers,
                        selectedAnswer = selectedAnswer,
                        onAnswerSelected = { answer ->
                            if (selectedAnswer == null) {
                                selectedAnswer = answer
                                if (answer == currentQuestion.correctAnswer) {
                                    currentScore += currentQuestion.score
                                }
                            }
                        }
                    )
                    if (selectedAnswer != null) {
                        // After a user selects an answer, delay for 2 seconds before moving to the next question
                        LaunchedEffect(selectedAnswer) {
                            delay(2000) // 2000 milliseconds = 2 seconds
                            // Clear the selected answer and move to the next question
                            selectedAnswer = null
                            currentQuestionIndex++

                            // Check if all questions are answered
                            if (currentQuestionIndex >= totalQuestions) {
                                // All questions answered, navigate back to the dashboard
                                storageManager.setHighScore(currentScore)
                                navController.navigate("dashboard")
                            }
                        }
                    }
                }
                // Handle loading error
                loadingError != null -> {
                    showDialog.value = false
                    // Text("Failed to load quiz data: ${loadingError?.message}")
                }
                // Display a loading indicator while waiting for data
                else -> {
                    CustomCircularProgressIndicator(color = Color.Green, text = "")
                }
            }
        }
    }
}

