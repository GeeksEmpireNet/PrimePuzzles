package net.geeksempire.primepuzzles.Utils.FunctionsClass

import kotlin.math.sqrt

class FunctionsClassMath() {

    fun isNumberPrime(numberToCheck: Int) : Boolean {
        var isPrime = true
        val sqrtNumber: Int = sqrt(numberToCheck.toFloat()).toInt()
        for (i in 2..sqrtNumber) {
            if (numberToCheck % i == 0) {
                isPrime = false
                break
            }
        }
        return isPrime
    }

    fun isNumbersDivisible(aA: Int, bB: Int) : Boolean {

        return ((aA % bB == 0))
    }
}