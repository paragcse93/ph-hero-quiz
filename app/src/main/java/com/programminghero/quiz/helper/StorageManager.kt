package com.programminghero.quiz.helper

import android.content.Context
import android.content.SharedPreferences

class StorageManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "ph_quiz"
        private const val HIGH_SCORE_KEY = "high_score"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getHighScore(): Int {
        return sharedPreferences.getInt(HIGH_SCORE_KEY, 0)
    }

    fun setHighScore(score: Int) {
        val currentHighScore = getHighScore()
        if (score > currentHighScore) {
            sharedPreferences.edit().putInt(HIGH_SCORE_KEY, score).apply()
        }
    }
}
