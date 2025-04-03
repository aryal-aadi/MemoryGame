package com.example.aadityaaryalfinalmobile

//data class representing the player and their score
data class PlayerScore(val name: String, val score: Int)

//object that manages scores in-memory
object ScoreManager {
    private val scores = mutableListOf<PlayerScore>()

    //adding score to the list
    fun addScore(name: String, score: Int) {
        if (name.isNotBlank()) {
            scores.add(PlayerScore(name, score))
        }
    }

    //returns top 3 score in descending order
    fun getTopScores(limit: Int = 3): List<PlayerScore> {
        return scores.sortedByDescending { it.score }.take(limit)
    }

    //clears all scores
//    fun clearScores() {
//        scores.clear()
//    }
}
