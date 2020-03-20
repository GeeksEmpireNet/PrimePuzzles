/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/20/20 12:56 PM
 * Last modified 3/20/20 12:53 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameData

import net.geeksempire.primepuzzles.GameLogic.GameLevel

class NumbersListProvider {

    companion object {
        private val ListOfNumbers: ArrayList<Int> = ArrayList<Int>()
    }

    fun generateListOfNumbers() : ArrayList<Int> {

        return  when (GameLevel().getGameDifficultyLevel()) {
            GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {

                if (NumbersListProvider.ListOfNumbers.isEmpty()) {
                    NumbersListProvider.ListOfNumbers.clear()
                    NumbersListProvider.ListOfNumbers.addAll(2..9)
                }

                NumbersListProvider.ListOfNumbers
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {

                if (NumbersListProvider.ListOfNumbers.isEmpty()) {
                    NumbersListProvider.ListOfNumbers.clear()
                    NumbersListProvider.ListOfNumbers.addAll(10..99)
                }

                NumbersListProvider.ListOfNumbers
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_THREE_DIGIT-> {

                if (NumbersListProvider.ListOfNumbers.isEmpty()) {
                    NumbersListProvider.ListOfNumbers.clear()
                    NumbersListProvider.ListOfNumbers.addAll(100..999)
                }

                NumbersListProvider.ListOfNumbers
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_FOUR_DIGIT-> {

                if (NumbersListProvider.ListOfNumbers.isEmpty()) {
                    NumbersListProvider.ListOfNumbers.clear()
                    NumbersListProvider.ListOfNumbers.addAll(1000..9999)
                }

                NumbersListProvider.ListOfNumbers
            }
            else -> {

                if (NumbersListProvider.ListOfNumbers.isEmpty()) {
                    NumbersListProvider.ListOfNumbers.clear()
                    NumbersListProvider.ListOfNumbers.addAll(2..9)
                }

                NumbersListProvider.ListOfNumbers
            }
        }
    }

    fun removeNumberFromList(numberToRemove: Int) {
        NumbersListProvider.ListOfNumbers.remove(numberToRemove)
    }
}