package com.example.aadityaaryalfinalmobile

import kotlin.random.Random

class GameController {
    var round = 1
    var score = 0
    var gameOver = false

    //getting number of tiles to highlight based on current round
    fun getTilesToHighlight(): List<Int> {
        val tileCount = if (round <= 3) 4 else 5
        val totalTiles = 36
        val selected = mutableSetOf<Int>()
        while (selected.size < tileCount) {
            selected.add(Random.nextInt(0, totalTiles))
        }
        return selected.toList()
    }

    //evaluating player's selections match the correct tiles
    fun checkAnswer(correct: List<Int>, selected: List<Int>): Boolean {
        return correct.sorted() == selected.sorted()
    }

    //updating score based on round
    fun updateScore(): Int {
        val points = if (round <= 3) 10 else 20
        score += points
        round++
        return points
    }

    //handling game over
    fun endGame(playerName: String) {
        gameOver = true
        ScoreManager.addScore(playerName, score)
    }
}
