/*
 * Copyright © 2019 By Geeks Empire.
 *
 * Created by Elias Fazel on 11/12/19 5:40 PM
 * Last modified 11/12/19 5:12 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameLogic

import android.content.Context
import net.geeksempire.primepuzzles.GameInformation.GameVariables
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassMath

class GameOperations(initContext: Context) {

    val context: Context = initContext

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


    fun generateHint() : String {




































        return ""
    }
}