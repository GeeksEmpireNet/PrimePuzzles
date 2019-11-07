package net.geeksempire.primepuzzles.Utils.FunctionsClass

fun isNumberPrime(numberToCheck: Int) : Boolean {
    var isPrime = true
    for (i in (2..9)) {
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