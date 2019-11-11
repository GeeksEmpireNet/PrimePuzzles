package net.geeksempire.primepuzzles.GameLogic

import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassMath

class GameOperations {

    fun determinePrimeValue() : Boolean {

        var operationDone: Boolean = false
        if (FunctionsClassMath().isNumberPrime(GameVariables.CENTER_VALUE.value!!)) {
            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

            operationDone = true
        }
        return operationDone
    }

    fun determineTopValue() : Boolean {

        var operationDone: Boolean = false
        if (FunctionsClassMath().isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.TOP_VALUE.value!!)) {
            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

            operationDone = true
        }
        return operationDone
    }

    fun determineLeftValue() : Boolean {

        var operationDone: Boolean = false
        if (FunctionsClassMath().isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.LEFT_VALUE.value!!)) {
            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

            operationDone = true
        }
        return operationDone
    }

    fun determineRightValue() : Boolean {

        var operationDone: Boolean = false
        if (FunctionsClassMath().isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.RIGHT_VALUE.value!!)) {
            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value!! + 1

            operationDone = true
        }
        return operationDone
    }


    fun generateHint() : String {

        return ""
    }
}