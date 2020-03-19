/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/19/20 3:14 PM
 * Last modified 3/19/20 3:06 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameData

import net.geeksempire.primepuzzles.GameLogic.GameLevel

class NumbersListProvider {

    companion object {
        private val listOfNumbers: ArrayList<Int> = ArrayList<Int>()
    }

    fun generateListOfNumbers() : ArrayList<Int> {

        return when (GameLevel().getGameDifficultyLevel()) {
            GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {
                NumbersListProvider.listOfNumbers.clear()
                NumbersListProvider.listOfNumbers.addAll(2..9)

                NumbersListProvider.listOfNumbers
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {
                NumbersListProvider.listOfNumbers.clear()
                NumbersListProvider.listOfNumbers.addAll(10..99)

                NumbersListProvider.listOfNumbers
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_THREE_DIGIT-> {
                NumbersListProvider.listOfNumbers.clear()
                NumbersListProvider.listOfNumbers.addAll(100..999)

                NumbersListProvider.listOfNumbers
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_FOUR_DIGIT-> {
                NumbersListProvider.listOfNumbers.clear()
                NumbersListProvider.listOfNumbers.addAll(1000..9999)

                NumbersListProvider.listOfNumbers
            }
            else -> {
                NumbersListProvider.listOfNumbers.clear()
                NumbersListProvider.listOfNumbers.addAll(2..9)

                NumbersListProvider.listOfNumbers
            }
        }
    }

    fun removeNumberFromList(numberToRemove: Int) {
        NumbersListProvider.listOfNumbers.remove(numberToRemove)
    }
}