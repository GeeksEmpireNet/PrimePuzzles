/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/17/20 2:03 PM
 * Last modified 3/17/20 1:54 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import org.json.JSONObject
import kotlin.math.sqrt

class FunctionsClassMath(private val context: Context)  {

    private val functionsClassGameIO: FunctionsClassGameIO = FunctionsClassGameIO(context)
    private val primeJsonObject = functionsClassGameIO.primeNumbersJsonFile()

    fun isNumberPrime(numberToCheck: Int) : Boolean {
        var isPrime = true

        if (primeJsonObject != null) {
            val jsonObjectPrimeNumbers: JSONObject = primeJsonObject.get("PrimeNumbers") as JSONObject

            if (jsonObjectPrimeNumbers.isNull("${numberToCheck}")) {
                isPrime = false
            }
        } else {
            val sqrtNumber: Int = sqrt(numberToCheck.toFloat()).toInt()

            for (i in 2..sqrtNumber) {
                if (numberToCheck % i == 0) {
                    isPrime = false

                    break
                }
            }
        }

        return isPrime
    }

    fun isNumbersDivisible(aA: Int, bB: Int) : Boolean {

        return ((aA % bB == 0))
    }
}