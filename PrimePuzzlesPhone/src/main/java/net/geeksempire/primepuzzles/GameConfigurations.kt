package net.geeksempire.primepuzzles

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import net.geeksempire.primepuzzles.GameLogic.GameLevel
import net.geeksempire.primepuzzles.GameLogic.GameSettings
import net.geeksempire.primepuzzles.GamePlay.GamePlay
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGameIO
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassSystem


class GameConfigurations : Activity() {

    lateinit var functionsClassSystem: FunctionsClassSystem
    lateinit var functionsClassGameIO: FunctionsClassGameIO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.configuration_view)

        functionsClassSystem = FunctionsClassSystem(applicationContext)
        functionsClassGameIO = FunctionsClassGameIO(applicationContext)

        GameLevel.GAME_DIFFICULTY_LEVEL = functionsClassGameIO.readLevelProcess()
        GamePlay.RestoreGameState = if (BuildConfig.DEBUG) { true } else { false }

    }

    override fun onStart() {
        super.onStart()

        Handler().postDelayed({
            startActivity(Intent(applicationContext, GamePlay::class.java).apply {
                putExtra(GameSettings.RESTORE_GAME_STATE, GamePlay.RestoreGameState)
                putExtra("StatusBarHeight", functionsClassSystem.getStatusBarHeight(applicationContext))
            }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())
        }, 555)
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
