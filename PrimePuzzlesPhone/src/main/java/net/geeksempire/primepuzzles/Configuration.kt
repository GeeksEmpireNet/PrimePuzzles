package net.geeksempire.primepuzzles

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import net.geeksempire.primepuzzles.GameLogic.GameLevel
import net.geeksempire.primepuzzles.GameLogic.GameSettings
import net.geeksempire.primepuzzles.GamePlay.GamePlay
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGameIO


class Configuration : Activity() {

    lateinit var functionsClassGameIO: FunctionsClassGameIO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuration_view)

        functionsClassGameIO = FunctionsClassGameIO(applicationContext)

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        GameLevel.GAME_DIFFICULTY_LEVEL = functionsClassGameIO.readLevelProcess()
        GamePlay.RestoreGameState = if (BuildConfig.DEBUG) {true} else {false}
        startActivity(Intent(applicationContext, GamePlay::class.java).apply {
            putExtra(GameSettings.RESTORE_GAME_STATE, GamePlay.RestoreGameState)
            putExtra("StatusBarHeight", statusBarHeight)
        }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())
    }
}
