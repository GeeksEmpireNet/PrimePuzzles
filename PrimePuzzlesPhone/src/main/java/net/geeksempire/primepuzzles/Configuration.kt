package net.geeksempire.primepuzzles

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import net.geeksempire.primepuzzles.GamePlay.GamePlay

class Configuration : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuration_view)

        startActivity(Intent(applicationContext, GamePlay::class.java))
    }
}
