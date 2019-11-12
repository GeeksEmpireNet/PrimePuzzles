package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import net.geeksempire.primepuzzles.Utils.JsonParser.JSONObject
import net.geeksempire.primepuzzles.Utils.JsonParser.JSONParser
import java.io.FileReader
import kotlin.math.sqrt

class FunctionsClassMath(initContext: Context)  {

    val context: Context = initContext

    fun isNumberPrime(numberToCheck: Int) : Boolean {
        var isPrime = true

        if (context.getFileStreamPath("PrimeNumbers.json").exists()) {
            val jsonParser: JSONParser = JSONParser()
            val fileReader = FileReader("/data/data/" + context.packageName + "/files/" + "PrimeNumbers.json")
            val jsonObject: JSONObject = jsonParser.parse(fileReader) as JSONObject
            val jsonObjectPrimeNumbers: JSONObject = jsonObject["PrimeNumbers"] as JSONObject
            if (jsonObjectPrimeNumbers["${numberToCheck}"] == null) {
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