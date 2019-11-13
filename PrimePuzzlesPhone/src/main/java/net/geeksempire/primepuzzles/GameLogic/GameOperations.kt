/*
 * Copyright © 2019 By Geeks Empire.
 *
 * Created by Elias Fazel on 11/13/19 2:52 PM
 * Last modified 11/13/19 1:29 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameLogic

import android.content.Context
import net.geeksempire.primepuzzles.GameInformation.GameVariables
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassMath

class GameOperations(initContext: Context) {

    val context: Context = initContext

    var hintType: Int = 0

    companion object {
        const val GENERATE_HINT_PRIME: Int = 1
        const val GENERATE_HINT_DIVISIBLE_TOP: Int = 2
        const val GENERATE_HINT_DIVISIBLE_LEFT: Int = 3
        const val GENERATE_HINT_DIVISIBLE_RIGHT: Int = 4
    }

    fun determinePrimeValue() : Boolean {

        var operationDone: Boolean = false
        if (FunctionsClassMath(context).isNumberPrime(GameVariables.CENTER_VALUE.value!!)) {
            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

            operationDone = true
        }
        return operationDone
    }

    fun determineTopValue() : Boolean {

        var operationDone: Boolean = false
        if (FunctionsClassMath(context).isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.TOP_VALUE.value!!)) {
            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

            operationDone = true
        }
        return operationDone
    }

    fun determineLeftValue() : Boolean {

        var operationDone: Boolean = false
        if (FunctionsClassMath(context).isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.LEFT_VALUE.value!!)) {
            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

            operationDone = true
        }
        return operationDone
    }

    fun determineRightValue() : Boolean {

        var operationDone: Boolean = false
        if (FunctionsClassMath(context).isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.RIGHT_VALUE.value!!)) {
            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

            operationDone = true
        }
        return operationDone
    }


    fun generateHint() : String{
        val functionsClassMath: FunctionsClassMath = FunctionsClassMath(context)
        var hintInformation: String = context.getString(R.string.noHint)

        if (functionsClassMath.isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.TOP_VALUE.value!!)) {
            hintType = GameOperations.GENERATE_HINT_DIVISIBLE_TOP

        } else if (functionsClassMath.isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.LEFT_VALUE.value!!)) {
            hintType = GameOperations.GENERATE_HINT_DIVISIBLE_LEFT

        } else if (functionsClassMath.isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.RIGHT_VALUE.value!!)) {
            hintType = GameOperations.GENERATE_HINT_DIVISIBLE_RIGHT

        } else if (functionsClassMath.isNumberPrime(GameVariables.CENTER_VALUE.value!!)) {
            hintType = GameOperations.GENERATE_HINT_PRIME

            hintInformation = context.getString(R.string.primeHint)
        }

        return hintInformation
    }
}