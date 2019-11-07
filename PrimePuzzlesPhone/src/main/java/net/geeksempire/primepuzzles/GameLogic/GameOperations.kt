package net.geeksempire.primepuzzles.GameLogic

import net.geeksempire.primepuzzles.Utils.FunctionsClass.isNumberPrime
import net.geeksempire.primepuzzles.Utils.FunctionsClass.isNumbersDivisible

class GameOperations {

    fun determinePrimeValue() : Boolean {
        GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

        var operationDone: Boolean = false
        if (isNumberPrime(GameVariables.CENTER_VALUE.value!!)) {

            operationDone = true
        }
        return operationDone
    }

    fun determineTopValue() : Boolean {
        GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

        var operationDone: Boolean = false
        if (isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.TOP_VALUE.value!!)) {

            operationDone = true
        }
        return operationDone
    }

    fun determineLeftValue() : Boolean {
        GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

        var operationDone: Boolean = false
        if (isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.LEFT_VALUE.value!!)) {

            operationDone = true
        }
        return operationDone
    }

    fun determineRightValue() : Boolean {
        GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

        var operationDone: Boolean = false
        if (isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.RIGHT_VALUE.value!!)) {

            operationDone = true
        }
        return operationDone
    }


    fun generateHint() : String {

        return ""
    }
}