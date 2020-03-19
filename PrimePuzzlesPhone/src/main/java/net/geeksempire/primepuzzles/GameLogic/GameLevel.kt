/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/19/20 3:14 PM
 * Last modified 3/19/20 3:10 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameLogic

class GameLevel {

    companion object {
        var GAME_DIFFICULTY_LEVEL = 1

        const val GAME_DIFFICULTY_LEVEL_ONE_DIGIT = 1
        const val GAME_DIFFICULTY_LEVEL_TWO_DIGIT = 2
        const val GAME_DIFFICULTY_LEVEL_THREE_DIGIT = 3
        const val GAME_DIFFICULTY_LEVEL_FOUR_DIGIT = 4
    }

    fun getGameDifficultyLevel() : Int {

        return GameLevel.GAME_DIFFICULTY_LEVEL
    }

    fun getPointMultiplier() : Int{

        return when (getGameDifficultyLevel()) {
            GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {
                1
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {
                2
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_THREE_DIGIT-> {
                3
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_FOUR_DIGIT -> {
                4
            }
            else -> {
                1
            }
        }
    }
}