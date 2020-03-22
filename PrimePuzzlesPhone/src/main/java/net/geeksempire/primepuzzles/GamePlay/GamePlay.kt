/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/22/20 2:45 PM
 * Last modified 3/22/20 2:45 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GamePlay

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.geeksempire.primepuzzles.GameData.GameVariablesViewModel
import net.geeksempire.primepuzzles.GameLogic.GameSettings
import net.geeksempire.primepuzzles.GameLogic.LevelsConfiguration.GameLevel
import net.geeksempire.primepuzzles.GamePlay.Extensions.*
import net.geeksempire.primepuzzles.GamePlay.Utils.CountDownTimer
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGame
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGameIO
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassUI
import net.geeksempire.primepuzzles.databinding.GamePlayViewBinding
import kotlin.math.hypot

class GamePlay : AppCompatActivity() {

    val functionsClassUI: FunctionsClassUI by lazy {
        FunctionsClassUI(applicationContext)
    }

    val functionsClassGame: FunctionsClassGame by lazy {
        FunctionsClassGame(applicationContext)
    }

    val functionsClassGameIO: FunctionsClassGameIO by lazy {
        FunctionsClassGameIO(applicationContext)
    }


    val gameLevel: GameLevel = GameLevel()

    lateinit var countDownTimer: CountDownTimer
    lateinit var valueAnimatorProgressBar: ValueAnimator


    private lateinit var gameVariablesViewModel: GameVariablesViewModel


    lateinit var gamePlayViewBinding: GamePlayViewBinding


    companion object {
        var RestoreGameState: Boolean = false

        var lastThickTimer: Long = 13000
        var countDownTimePaused: Boolean = false
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.postValue(0)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)

        gamePlayViewBinding = GamePlayViewBinding.inflate(layoutInflater)
        setContentView(gamePlayViewBinding.root)

        countDownTimer = functionsClassGame.countDownTimer(this@GamePlay, gamePlayViewBinding.gamePlayInformationViewInclude.timerProgressBar)

        gamePlayViewBinding.gamePlayControlViewInclude.gesturedRandomCenterView.bringToFront()
        gamePlayViewBinding.gamePlayPrimeNumberDetectedViewInclude.root.bringToFront()

        gameVariablesViewModel = ViewModelProvider(this@GamePlay).get(GameVariablesViewModel::class.java)

        val rootLayout = gamePlayViewBinding.root
        rootLayout.visibility = View.INVISIBLE
        val viewTreeObserver = rootLayout.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

                override fun onGlobalLayout() {
                    val finalRadius = hypot(
                        functionsClassUI.displayX().toDouble(),
                        functionsClassUI.displayY().toDouble()
                    ).toInt()
                    val circularReveal = ViewAnimationUtils.createCircularReveal(
                        rootLayout,
                        functionsClassUI.displayX() / 2,
                        functionsClassUI.displayY() / 2,
                        functionsClassUI.DpToInteger(50f),
                        finalRadius.toFloat()
                    )
                    circularReveal.duration = 1300
                    circularReveal.interpolator = AccelerateInterpolator()

                    rootLayout.visibility = View.VISIBLE
                    circularReveal.start()
                    rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    circularReveal.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {

                        }

                        override fun onAnimationEnd(animation: Animator) {
                            rootLayout.visibility = View.VISIBLE
                        }

                        override fun onAnimationCancel(animation: Animator) {

                        }

                        override fun onAnimationRepeat(animation: Animator) {

                        }
                    })
                }
            })
        } else {
            rootLayout.visibility = View.VISIBLE
        }

        /*
         * Restore Game State
         */
        GamePlay.RestoreGameState = intent.getBooleanExtra(GameSettings.RESTORE_GAME_STATE, false)
        FunctionsClassDebug.PrintDebug("Restore Game State ::: ${GamePlay.RestoreGameState}")

        /*
         *
         * Sides Random Values Functions
         *
         */
        setupViews()

        /*
         *
         * Points Functions
         *
         */
        scanPointsChange()

        /*
         *
         * Re-position AdsView
         *
         */
        setupAdsView()

        /*
         *
         * Re-position Game Controller Views
         *
         */
        setupGameControllerViews()

        /*
         *
         * Set Numbers for Top-Left-Right
         *
         */
        setupInitialNumber()

        /*
         *
         * LiveData Game Logic
         *
         */
        observeGameLogicVariable()

        /*
         *
         * Show/Dismiss Snackbar
         *
         */
        observeSnackbarToggle()

        Handler().postDelayed({
            valueAnimatorProgressBar.start()
            countDownTimer.start()
        }, 1999)
    }

    override fun onResume() {
        super.onResume()

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)

        if (GamePlay.countDownTimePaused) {
            valueAnimatorProgressBar.resume()
            countDownTimer.resume()

            GamePlay.countDownTimePaused = false
        }
    }

    override fun onPause() {
        super.onPause()

        valueAnimatorProgressBar.pause()
        countDownTimer.pause()

        GamePlay.countDownTimePaused = true
    }

    override fun onBackPressed() {
        if (gamePlayViewBinding.gamePlayHintViewInclude.root.isShown) {
            val hintAnimationHide =
                AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_out)
            gamePlayViewBinding.gamePlayHintViewInclude.root.startAnimation(hintAnimationHide)
            hintAnimationHide.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                    gamePlayViewBinding.gamePlayHintViewInclude.root.visibility = View.INVISIBLE
                    gamePlayViewBinding.gamePlayHintViewInclude.root.setBackgroundColor(Color.TRANSPARENT)
                }

                override fun onAnimationStart(animation: Animation?) {

                }
            })

            valueAnimatorProgressBar.resume()
            countDownTimer.resume()
        } else {
            super.onBackPressed()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}
