package net.geeksempire.primepuzzles.GamePlay

import android.app.Activity
import android.os.Bundle
import net.geeksempire.primepuzzles.R
import kotlin.random.Random

class GamePlay : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_play_view)

        val random: Int = Random.nextInt(1, 10)
        val randomValues = List(3) { Random.nextInt(1, 10) }


        val listToDivisible = ArrayList<Int>()
        listToDivisible.addAll(1..9)


        val i = listToDivisible.random()
        listToDivisible.remove(i)


        val j = listToDivisible.random()
        listToDivisible.remove(j)


        val h = listToDivisible.random()
        listToDivisible.remove(h)
    }
}
