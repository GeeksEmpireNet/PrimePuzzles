package net.geeksempire.primepuzzles.GamePlay

import android.app.Activity
import android.os.Bundle
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassUI

class GamePlay : Activity() {

    lateinit var functionsClassUI: FunctionsClassUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_play_view)

        functionsClassUI = FunctionsClassUI(applicationContext)
   
        val listOfDivisible = ArrayList<Int>()
        listOfDivisible.addAll(1..9)


        val i = listOfDivisible.random()
        listOfDivisible.remove(i)


        val j = listOfDivisible.random()
        listOfDivisible.remove(j)


        val h = listOfDivisible.random()
        listOfDivisible.remove(h)
    }
}
