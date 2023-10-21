package com.programminghero.quiz.model

data class QuizData(
    val questions: List<Question>
)

data class Question(
    val question: String,
    val answers: Map<String, String>,
    val questionImageUrl: String?,
    val correctAnswer: String,
    val score: Int
)