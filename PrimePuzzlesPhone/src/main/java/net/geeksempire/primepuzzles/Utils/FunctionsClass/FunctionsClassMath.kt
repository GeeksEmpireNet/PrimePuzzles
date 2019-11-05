package net.geeksempire.primepuzzles.Utils.FunctionsClass

fun isNumbersDivisible(aA: Int, bB: Int) : Boolean {

    return ((aA % bB == 0) || (bB % aA == 0))
}