package net.geeksempire.primepuzzles.GamePlay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.game_play_control_view.*
import net.geeksempire.primepuzzles.GameLogic.GameVariables
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassUI


class GamePlay : AppCompatActivity() {

    private lateinit var functionsClassUI: FunctionsClassUI

    private lateinit var gameVariables: GameVariables

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_play_view)

        functionsClassUI = FunctionsClassUI(applicationContext)

        gameVariables = ViewModelProviders.of(this).get(GameVariables::class.java)

        gesturedRandomCenter.bringToFront()

        val listOfDivisible = ArrayList<Int>()
        listOfDivisible.addAll(1..99)

        val topValueRandom = listOfDivisible.random()
        listOfDivisible.remove(topValueRandom)
        GameVariables.TOP_VALUE.value = topValueRandom
        randomTop.text = "${topValueRandom}"

        val leftValueRandom = listOfDivisible.random()
        listOfDivisible.remove(leftValueRandom)
        GameVariables.LEFT_VALUE.value = leftValueRandom
        randomLeft.text = "${leftValueRandom}"

        val rightValueRandom = listOfDivisible.random()
        listOfDivisible.remove(rightValueRandom)
        GameVariables.RIGHT_VALUE.value = rightValueRandom
        randomRight.text = "${rightValueRandom}"

        GameVariables.CENTER_VALUE.observe(this, object : Observer<Int> {
            override fun onChanged(newCenterValue: Int?) {
                gesturedRandomCenter.text = "${newCenterValue}"
            }
        })

        GameVariables.TOP_VALUE.observe(this, object : Observer<Int> {
            override fun onChanged(newTopValue: Int?) {
                randomTop.text = "${newTopValue}"
            }
        })

        GameVariables.LEFT_VALUE.observe(this, object : Observer<Int> {
            override fun onChanged(newLeftValue: Int?) {
                randomLeft.text = "${newLeftValue}"
            }
        })

        GameVariables.RIGHT_VALUE.observe(this, object : Observer<Int> {
            override fun onChanged(newRightValue: Int?) {
                randomRight.text = "${newRightValue}"
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {

    }
}
