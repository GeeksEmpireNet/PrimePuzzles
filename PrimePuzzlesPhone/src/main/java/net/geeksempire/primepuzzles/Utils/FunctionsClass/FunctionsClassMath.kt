/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/17/20 11:06 AM
 * Last modified 3/17/20 11:01 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import org.json.JSONObject
import java.io.FileReader
import kotlin.math.sqrt

class FunctionsClassMath(initContext: Context)  {

    val context: Context = initContext

    fun isNumberPrime(numberToCheck: Int) : Boolean {
        var isPrime = true

        if (context.getFileStreamPath("PrimeNumbers.json").exists()) {
            val fileReader = FileReader("/data/data/" + context.packageName + "/files/" + "PrimeNumbers.json")
            val jsonObject: JSONObject = JSONObject(fileReader.readText()) as JSONObject
            val jsonObjectPrimeNumbers: JSONObject = jsonObject["PrimeNumbers"] as JSONObject
            val jsonObjectPrimeNumber: String? = jsonObjectPrimeNumbers.getString("${numberToCheck}")

            if (jsonObjectPrimeNumber.isNullOrBlank()) {
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