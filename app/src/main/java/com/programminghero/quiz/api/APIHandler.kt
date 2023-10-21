package com.programminghero.quiz.api

import com.google.gson.Gson
import com.programminghero.quiz.model.QuizData
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.Collections.shuffle

class APIHandler {
    fun loadQuizDataFromUrl(url: String, callback: (QuizData?, Exception?) -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val jsonString = response.body?.string()
                    val gson = Gson()
                    val quizData = gson.fromJson(jsonString, QuizData::class.java)
                    val shuffledQuizData = quizData.copy(questions = quizData.questions.map { question ->
                        val shuffledAnswers = question.answers.entries.shuffled().associate { it.key to it.value }
                        question.copy(answers = shuffledAnswers)
                    })

                    callback(shuffledQuizData, null)
                } else {
                    callback(null, Exception("Request failed with code: ${response.code}"))
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                callback(null, e)
            }
        })
    }
}