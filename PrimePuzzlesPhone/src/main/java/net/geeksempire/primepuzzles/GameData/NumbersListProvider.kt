/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/19/20 2:01 PM
 * Last modified 3/19/20 2:00 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameData

import net.geeksempire.primepuzzles.GameLogic.GameLevel

class NumbersListProvider {

    companion object {
        val listOfNumbers: ArrayList<Int> = ArrayList<Int>()
    }

    fun generateListOfNumbers() : ArrayList<Int> {

        return when (GameLevel().getGameDifficultyLevel()) {
            GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {
                listOfNumbers.clear()
                listOfNumbers.addAll(2..9)

                listOfNumbers
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {
                listOfNumbers.clear()
                listOfNumbers.addAll(10..99)

                listOfNumbers
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_THREE_DIGIT-> {
                listOfNumbers.clear()
                listOfNumbers.addAll(100..999)

                listOfNumbers
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_FOUR_DIGIT-> {
                listOfNumbers.clear()
                listOfNumbers.addAll(1000..9999)

                listOfNumbers
            }
            else -> {
                listOfNumbers.clear()
                listOfNumbers.addAll(2..9)

                listOfNumbers
            }
        }
    }
}