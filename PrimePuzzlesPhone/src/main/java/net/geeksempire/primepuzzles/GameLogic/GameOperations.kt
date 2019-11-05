package net.geeksempire.primepuzzles.GameLogic

import net.geeksempire.primepuzzles.Utils.FunctionsClass.isNumbersDivisible

class GameOperations {

    fun determineTopValue() : Boolean {
        var operationDone: Boolean = false
        if (isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.TOP_VALUE.value!!)) {
            GameVariables.TOP_VALUE.value = GameVariables.CENTER_VALUE.value!! + GameVariables.TOP_VALUE.value!!

            operationDone = true
        }
        return operationDone
    }

    fun determineLeftValue() : Boolean {
        var operationDone: Boolean = false
        if (isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.LEFT_VALUE.value!!)) {
            GameVariables.LEFT_VALUE.value = GameVariables.CENTER_VALUE.value!! + GameVariables.LEFT_VALUE.value!!

            operationDone = true
        }
        return operationDone
    }

    fun determineRightValue() : Boolean {
        var operationDone: Boolean = false
        if (isNumbersDivisible(aA = GameVariables.CENTER_VALUE.value!!, bB = GameVariables.RIGHT_VALUE.value!!)) {
            GameVariables.RIGHT_VALUE.value = GameVariables.CENTER_VALUE.value!! + GameVariables.RIGHT_VALUE.value!!

            operationDone = true
        }
        return operationDone
    }
}