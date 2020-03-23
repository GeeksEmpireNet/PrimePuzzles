/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/22/20 4:29 PM
 * Last modified 3/22/20 3:55 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameLogic

import android.content.Context
import net.geeksempire.primepuzzles.GameData.GameVariablesViewModel
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassMath

class GameOperations(private val context: Context) {

    private var hintType: Int = 0

    companion object {
        const val GENERATE_HINT_PRIME: Int = 1
        const val GENERATE_HINT_DIVISIBLE_TOP: Int = 2
        const val GENERATE_HINT_DIVISIBLE_LEFT: Int = 3
        const val GENERATE_HINT_DIVISIBLE_RIGHT: Int = 4
    }

    fun determinePrimeValue() : Boolean {

        var operationDone: Boolean = false
        if (FunctionsClassMath(context).isNumberPrime(GameVariablesViewModel.CENTER_VALUE.value!!)) {
            GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.postValue(GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1)

            operationDone = true
        }
        return operationDone
    }

    fun determineTopValue() : Boolean {

        var operationDone: Boolean = false
        if (FunctionsClassMath(context).isNumbersDivisible(aA = GameVariablesViewModel.CENTER_VALUE.value!!, bB = GameVariablesViewModel.TOP_VALUE.value!!)) {
            GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.postValue(GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1)

            operationDone = true
        }
        return operationDone
    }

    fun determineLeftValue() : Boolean {
        var operationDone: Boolean = false

        if (FunctionsClassMath(context).isNumbersDivisible(aA = GameVariablesViewModel.CENTER_VALUE.value!!, bB = GameVariablesViewModel.LEFT_VALUE.value!!)) {
            GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.postValue(GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1)

            operationDone = true
        }

        return operationDone
    }

    fun determineRightValue() : Boolean {

        var operationDone: Boolean = false
        if (FunctionsClassMath(context).isNumbersDivisible(aA = GameVariablesViewModel.CENTER_VALUE.value!!, bB = GameVariablesViewModel.RIGHT_VALUE.value!!)) {
            GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.postValue(GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1)

            operationDone = true
        }
        return operationDone
    }

    fun generateHint() : String {
        val functionsClassMath: FunctionsClassMath = FunctionsClassMath(context)
        var hintInformation: String = context.getString(R.string.noHint)

        if (functionsClassMath.isNumbersDivisible(GameVariablesViewModel.CENTER_VALUE.value!!, GameVariablesViewModel.TOP_VALUE.value!!)) {
            hintType = GameOperations.GENERATE_HINT_DIVISIBLE_TOP

            hintInformation = " 🔳 ✖ 🔳 = ${GameVariablesViewModel.TOP_VALUE.value!! * GameVariablesViewModel.TOP_VALUE.value!!}\n\n" +
                    " 🔳 is the Answer ✔ "

        } else if (functionsClassMath.isNumbersDivisible(GameVariablesViewModel.CENTER_VALUE.value!!, GameVariablesViewModel.LEFT_VALUE.value!!)) {
            hintType = GameOperations.GENERATE_HINT_DIVISIBLE_LEFT

            hintInformation = " 🔳 ✖ 🔳 = ${GameVariablesViewModel.LEFT_VALUE.value!! * GameVariablesViewModel.LEFT_VALUE.value!!}\n\n" +
                    " 🔳 is the Answer ✔ "

        } else if (functionsClassMath.isNumbersDivisible(GameVariablesViewModel.CENTER_VALUE.value!!, GameVariablesViewModel.RIGHT_VALUE.value!!)) {
            hintType = GameOperations.GENERATE_HINT_DIVISIBLE_RIGHT

            hintInformation = " 🔳 ✖ 🔳 = ${GameVariablesViewModel.RIGHT_VALUE.value!! * GameVariablesViewModel.RIGHT_VALUE.value!!}\n\n" +
                    " 🔳 is the Answer ✔ "

        } else if (functionsClassMath.isNumberPrime(GameVariablesViewModel.CENTER_VALUE.value!!)) {
            hintType = GameOperations.GENERATE_HINT_PRIME

            hintInformation = context.getString(R.string.primeHint)
        }

        return hintInformation
    }
}