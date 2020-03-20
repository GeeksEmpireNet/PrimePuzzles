/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/20/20 3:17 PM
 * Last modified 3/20/20 2:57 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameData

import android.content.Context
import android.widget.TextView
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGame
import net.geeksempire.primepuzzles.databinding.GamePlayViewBinding

class ShuffleProcess(context: Context) {

    private val functionsClassGame: FunctionsClassGame = FunctionsClassGame(context)

    fun shuffleProcessPosition(gamePlayViewBinding: GamePlayViewBinding) {
        functionsClassGame.playShuffleMagicalNumbersPosition()

        val listOfDivisibleShuffle = ArrayList<Int>()
        listOfDivisibleShuffle.add((gamePlayViewBinding.gamePlayControlViewInclude.randomTop.currentView as TextView).text.toString().toInt())
        listOfDivisibleShuffle.add((gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.currentView as TextView).text.toString().toInt())
        listOfDivisibleShuffle.add((gamePlayViewBinding.gamePlayControlViewInclude.randomRight.currentView as TextView).text.toString().toInt())

        val topValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(topValueRandom)
        GameVariablesViewModel.TOP_VALUE.value = topValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setText("${topValueRandom}")

        val leftValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(leftValueRandom)
        GameVariablesViewModel.LEFT_VALUE.value = leftValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setText("${leftValueRandom}")

        val rightValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(rightValueRandom)
        GameVariablesViewModel.RIGHT_VALUE.value = rightValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setText("${rightValueRandom}")

        GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.value = 0
    }

    fun shuffleProcessValue(gamePlayViewBinding: GamePlayViewBinding) {
        functionsClassGame.playShuffleMagicalNumbersValues()

        val listOfDivisibleShuffle = ArrayList<Int>()
        listOfDivisibleShuffle.addAll(2..9)

        val topValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(topValueRandom)
        GameVariablesViewModel.TOP_VALUE.value = topValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setText("${topValueRandom}")

        val leftValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(leftValueRandom)
        GameVariablesViewModel.LEFT_VALUE.value = leftValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setText("${leftValueRandom}")

        val rightValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(rightValueRandom)
        GameVariablesViewModel.RIGHT_VALUE.value = rightValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setText("${rightValueRandom}")

        GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.value = 0
    }
}