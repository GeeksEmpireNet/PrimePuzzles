/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/20/20 1:24 PM
 * Last modified 3/20/20 1:23 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GamePlay

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import net.geeksempire.physics.animation.Core.SimpleSpringListener
import net.geeksempire.physics.animation.Core.Spring
import net.geeksempire.physics.animation.Core.SpringConfig
import net.geeksempire.physics.animation.SpringSystem
import net.geeksempire.primepuzzles.GameData.GameInformationVariable
import net.geeksempire.primepuzzles.GameData.GameVariablesViewModel
import net.geeksempire.primepuzzles.GameLogic.GameLevel
import net.geeksempire.primepuzzles.GameLogic.GameOperations
import net.geeksempire.primepuzzles.GameLogic.GameSettings
import net.geeksempire.primepuzzles.GamePlay.Ads.AdsLoading
import net.geeksempire.primepuzzles.GamePlay.Utils.CountDownTimer
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGame
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGameIO
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassUI
import net.geeksempire.primepuzzles.databinding.GamePlayViewBinding
import kotlin.math.hypot

class GamePlay : AppCompatActivity() {

    private val functionsClassUI: FunctionsClassUI by lazy {
        FunctionsClassUI(applicationContext)
    }

    private val functionsClassGame: FunctionsClassGame by lazy {
        FunctionsClassGame(applicationContext)
    }

    private val functionsClassGameIO: FunctionsClassGameIO by lazy {
        FunctionsClassGameIO(applicationContext)
    }


    val gameLevel: GameLevel = GameLevel()

    lateinit var countDownTimer: CountDownTimer
    lateinit var valueAnimatorProgressBar: ValueAnimator


    private lateinit var gameVariablesViewModel: GameVariablesViewModel


    private lateinit var gamePlayViewBinding: GamePlayViewBinding


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
        GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.value = 0

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)

        gamePlayViewBinding = GamePlayViewBinding.inflate(layoutInflater)
        setContentView(gamePlayViewBinding.root)

        functionsClassGame.countDownTimer(this@GamePlay, gamePlayViewBinding.gamePlayInformationViewInclude.timerProgressBar)

        gamePlayViewBinding.gamePlayControlViewInclude.gesturedRandomCenterView.bringToFront()
        gamePlayViewBinding.gamePlayPrimeNumberDetectedViewInclude.root.bringToFront()

        val rootLayout = this.window.decorView
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

        val adViewLayoutParams = gamePlayViewBinding.adViews.layoutParams as RelativeLayout.LayoutParams
        val statusBarHeight = if (intent.getIntExtra(
                "StatusBarHeight",
                functionsClassUI.DpToInteger(24f).toInt()
            ) == 0
        ) {
            intent.getIntExtra("StatusBarHeight", functionsClassUI.DpToInteger(24f).toInt())
        } else {
            functionsClassUI.DpToInteger(24f).toInt()
        }
        adViewLayoutParams.height = adViewLayoutParams.height + statusBarHeight
        gamePlayViewBinding.adViews.setPadding(0, statusBarHeight, 0, 0)
        gamePlayViewBinding.adViews.layoutParams = adViewLayoutParams

        val layoutParams = gamePlayViewBinding.gamePlayControlViewInclude.root.layoutParams
        layoutParams.height = functionsClassUI.displayX()
        gamePlayViewBinding.gamePlayControlViewInclude.root.layoutParams = layoutParams

        gameVariablesViewModel = ViewModelProvider(this@GamePlay).get(GameVariablesViewModel::class.java)

        val listOfDivisible = ArrayList<Int>()
        listOfDivisible.addAll(2..9)

        val topValueRandom = listOfDivisible.random()
        listOfDivisible.remove(topValueRandom)
        GameVariablesViewModel.TOP_VALUE.value = topValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setText("${topValueRandom}")

        val leftValueRandom = listOfDivisible.random()
        listOfDivisible.remove(leftValueRandom)
        GameVariablesViewModel.LEFT_VALUE.value = leftValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setText("${leftValueRandom}")

        val rightValueRandom = listOfDivisible.random()
        listOfDivisible.remove(rightValueRandom)
        GameVariablesViewModel.RIGHT_VALUE.value = rightValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setText("${rightValueRandom}")

        Handler().postDelayed({
            valueAnimatorProgressBar.start()
            countDownTimer.start()
        }, 1999)

        AdsLoading(applicationContext)
            .initializeAds(gamePlayViewBinding.adViewBannerGamePlay)
    }

    override fun onStart() {
        super.onStart()

        GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.observe(this,
            Observer<Int> { newDifficultyLevel ->
                when (gameLevel.getGameDifficultyLevel()) {
                    GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {//2..9
                        if (newDifficultyLevel!! >= 7) {
                            GameLevel.GAME_DIFFICULTY_LEVEL++
                            if (GameLevel.GAME_DIFFICULTY_LEVEL == 5) {
                                //The End
                            }
                            GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.value = 0
                        }
                    }
                    GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {//10..99
                        if (newDifficultyLevel!! >= 77) {
                            GameLevel.GAME_DIFFICULTY_LEVEL++
                            if (GameLevel.GAME_DIFFICULTY_LEVEL == 5) {
                                //The End
                            }
                            GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.value = 0
                        }
                    }
                    GameLevel.GAME_DIFFICULTY_LEVEL_THREE_DIGIT -> {//100..999
                        if (newDifficultyLevel!! >= 777) {
                            GameLevel.GAME_DIFFICULTY_LEVEL++
                            if (GameLevel.GAME_DIFFICULTY_LEVEL == 5) {
                                //The End
                            }
                            GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.value = 0
                        }
                    }
                    GameLevel.GAME_DIFFICULTY_LEVEL_FOUR_DIGIT -> {//1000..9999
                        if (newDifficultyLevel!! >= 7777) {
                            GameLevel.GAME_DIFFICULTY_LEVEL++
                            if (GameLevel.GAME_DIFFICULTY_LEVEL == 5) {
                                //The End
                            }
                            GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.value = 0
                        }
                    }
                }
                functionsClassGameIO.saveLevelProcess(gameLevel.getGameDifficultyLevel())
            })


        GameVariablesViewModel.PRIME_NUMBER_DETECTED.observe(this,
            Observer<Boolean> { primeDetected ->
                if (primeDetected!!) {
                    functionsClassGame.playLongPrimeDetectionSound()

                    gamePlayViewBinding.gamePlayPrimeNumberDetectedViewInclude.detectedPrimeNumber.text = "${GameVariablesViewModel.CENTER_VALUE.value!!}"

                    Handler().postDelayed({
                        functionsClassUI.circularRevealAnimationPrimeNumber(
                            countDownTimer,
                            gamePlayViewBinding.gamePlayPrimeNumberDetectedViewInclude.root,
                            gamePlayViewBinding.primeNumbers.y + (gamePlayViewBinding.primeNumbers.height / 2),
                            gamePlayViewBinding.primeNumbers.x + (gamePlayViewBinding.primeNumbers.width / 2),
                            1f
                        )
                    }, 99)

                    GameVariablesViewModel.PRIME_NUMBER_DETECTED.value = false
                }
            })


        GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.value = 0
        GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.observe(this,
            Observer<Int> { newShufflePosition ->
                if (newShufflePosition!! >= 7) {
                    shuffleProcessPosition()
                }
            })

        GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.value = 0
        GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.observe(this,
            Observer<Int> { newShuffleValue ->
                if (newShuffleValue!! >= 21) {
                    shuffleProcessValue()
                }
            })


        GameVariablesViewModel.CENTER_VALUE.observe(this,
            Observer<Int> { newCenterValue ->
                gamePlayViewBinding.gamePlayControlViewInclude.gesturedRandomCenterView.text = "${newCenterValue}"
            })

        GameVariablesViewModel.TOP_VALUE.observe(this,
            Observer<Int> { newTopValue ->
                gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setText("${newTopValue}")
            })
        GameVariablesViewModel.LEFT_VALUE.observe(this,
            Observer<Int> { newLeftValue ->
                gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setText("${newLeftValue}")
            })
        GameVariablesViewModel.RIGHT_VALUE.observe(this,
            Observer<Int> { newRightValue ->
                gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setText("${newRightValue}")
            })


        val snackbarHint = Snackbar.make(
            gamePlayViewBinding.fullGamePlay,
            Html.fromHtml(
                GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT,
                Html.FROM_HTML_MODE_LEGACY
            ),
            Snackbar.LENGTH_INDEFINITE
        )
        snackbarHint.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        snackbarHint.setTextColor(ColorStateList.valueOf(getColor(R.color.light)))
        snackbarHint.setActionTextColor(getColor(R.color.yellow))

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,
            intArrayOf(
                getColor(R.color.dark),
                getColor(R.color.darker),
                getColor(R.color.dark)
            )
        )

        val viewSnackbar = snackbarHint.view
        viewSnackbar.background = gradientDrawable

        val snackButton: Button = snackbarHint.getView().findViewById<Button>(R.id.snackbar_action)
        snackButton.setBackgroundColor(Color.TRANSPARENT)

        GameVariablesViewModel.TOGGLE_SNACKBAR.observe(this, object : Observer<Boolean> {
            override fun onChanged(newToggleSnackBar: Boolean?) {
                if (newToggleSnackBar!!) {
                    snackbarHint.setText(
                        Html.fromHtml(
                            GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT,
                            Html.FROM_HTML_MODE_LEGACY
                        )
                    )
                    snackbarHint.setAction(
                        GameInformationVariable.SNACKBAR_HINT_BUTTON_TEXT,
                        object : View.OnClickListener {
                            override fun onClick(view: View?) {
                                when (GameInformationVariable.snackBarAction) {
                                    GameInformationVariable.HINT_ACTION -> {
                                        valueAnimatorProgressBar.pause()
                                        countDownTimer.pause()

                                        val hintData: String =
                                            GameOperations(applicationContext).generateHint()
                                        gamePlayViewBinding.gamePlayHintViewInclude.hintEquation.text = hintData

                                        val hintAnimation = AnimationUtils.loadAnimation(
                                            applicationContext,
                                            android.R.anim.fade_in
                                        )
                                        gamePlayViewBinding.gamePlayHintViewInclude.root.startAnimation(hintAnimation)
                                        hintAnimation.setAnimationListener(object :
                                            Animation.AnimationListener {
                                            override fun onAnimationRepeat(animation: Animation?) {

                                            }

                                            override fun onAnimationEnd(animation: Animation?) {
                                                gamePlayViewBinding.gamePlayHintViewInclude.root.visibility = View.VISIBLE

                                                val hintBackgroundDim = ValueAnimator.ofArgb(
                                                    Color.TRANSPARENT,
                                                    getColor(R.color.dark_transparent)
                                                )
                                                hintBackgroundDim.duration = 321
                                                hintBackgroundDim.addUpdateListener { animator ->
                                                    //(animator.animatedValue as Int)
                                                    gamePlayViewBinding.gamePlayHintViewInclude.root.setBackgroundColor((animator.animatedValue as Int))
                                                }
                                                hintBackgroundDim.start()
                                            }

                                            override fun onAnimationStart(animation: Animation?) {

                                            }
                                        })

                                        gamePlayViewBinding.gamePlayHintViewInclude.root.setOnClickListener {
                                            if (gamePlayViewBinding.gamePlayHintViewInclude.root.isShown) {
                                                val hintAnimationHide =
                                                    AnimationUtils.loadAnimation(
                                                        applicationContext,
                                                        android.R.anim.fade_out
                                                    )
                                                gamePlayViewBinding.gamePlayHintViewInclude.root.startAnimation(hintAnimationHide)
                                                hintAnimationHide.setAnimationListener(object :
                                                    Animation.AnimationListener {
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
                                            }
                                        }

                                        gamePlayViewBinding.gamePlayHintViewInclude.hintGotIt.setOnClickListener {
                                            if (gamePlayViewBinding.gamePlayHintViewInclude.root.isShown) {
                                                val hintAnimationHide =
                                                    AnimationUtils.loadAnimation(
                                                        applicationContext,
                                                        android.R.anim.fade_out
                                                    )
                                                gamePlayViewBinding.gamePlayHintViewInclude.root.startAnimation(hintAnimationHide)
                                                hintAnimationHide.setAnimationListener(object :
                                                    Animation.AnimationListener {
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
                                            }
                                        }
                                    }
                                    GameInformationVariable.PRIME_NUMBER_ACTION -> {
                                        functionsClassUI.circularHideAnimationPrimeNumber(
                                            countDownTimer,
                                            gamePlayViewBinding.gamePlayPrimeNumberDetectedViewInclude.root,
                                            gamePlayViewBinding.primeNumbers.y + (gamePlayViewBinding.primeNumbers.height / 2),
                                            gamePlayViewBinding.primeNumbers.x + (gamePlayViewBinding.primeNumbers.width / 2),
                                            1f
                                        )
                                    }
                                }
                            }
                        })
                    Handler().postDelayed({
                        snackbarHint.show()
                    }, 321)
                } else {
                    snackbarHint.dismiss()
                }
            }
        })
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

    /*
     *
     * Sides Random Values Functions
     *
     */
    private fun setupViews() {

        functionsClassUI.shadowValueAnimatorLoop(
            gamePlayViewBinding.primeNumbers,
            19, 3,
            1333, 777,
            getColor(R.color.lighter), 0f, 0f
        )

        val springSystem = SpringSystem.create()

        val springRandomTop = springSystem.createSpring()
        val springRandomLeft = springSystem.createSpring()
        val springRandomRight = springSystem.createSpring()

        springRandomTop.addListener(object : SimpleSpringListener(/*spring*/) {
            override fun onSpringUpdate(spring: Spring?) {
                val value = spring!!.currentValue.toFloat()
                val scale = 1f - (value * 0.5f)

                gamePlayViewBinding.gamePlayControlViewInclude.randomTop.scaleX = scale
                gamePlayViewBinding.gamePlayControlViewInclude.randomTop.scaleY = scale
            }

            override fun onSpringEndStateChange(spring: Spring?) {

            }

            override fun onSpringAtRest(spring: Spring?) {

            }

            override fun onSpringActivate(spring: Spring?) {

            }

        })

        springRandomLeft.addListener(object : SimpleSpringListener(/*spring*/) {
            override fun onSpringUpdate(spring: Spring?) {
                val value = spring!!.currentValue.toFloat()
                val scale = 1f - (value * 0.5f)

                gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.scaleX = scale
                gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.scaleY = scale
            }

            override fun onSpringEndStateChange(spring: Spring?) {

            }

            override fun onSpringAtRest(spring: Spring?) {

            }

            override fun onSpringActivate(spring: Spring?) {

            }

        })

        springRandomRight.addListener(object : SimpleSpringListener(/*spring*/) {
            override fun onSpringUpdate(spring: Spring?) {
                val value = spring!!.currentValue.toFloat()
                val scale = 1f - (value * 0.5f)

                gamePlayViewBinding.gamePlayControlViewInclude.randomRight.scaleX = scale
                gamePlayViewBinding.gamePlayControlViewInclude.randomRight.scaleY = scale
            }

            override fun onSpringEndStateChange(spring: Spring?) {

            }

            override fun onSpringAtRest(spring: Spring?) {

            }

            override fun onSpringActivate(spring: Spring?) {

            }

        })

        val springConfig = SpringConfig(800.0, 20.0 /*friction*/)

        springRandomTop.springConfig = springConfig
        springRandomLeft.springConfig = springConfig
        springRandomRight.springConfig = springConfig


        gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    springRandomTop.endValue = (0.7)

                }
                MotionEvent.ACTION_UP -> {
                    springRandomTop.endValue = (0.0)

                }
            }
            return@setOnTouchListener false
        }
        gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setOnClickListener {}

        gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setOnClickListener { }
        gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    springRandomLeft.endValue = (0.7)

                }
                MotionEvent.ACTION_UP -> {
                    springRandomLeft.endValue = (0.0)

                }
            }
            return@setOnTouchListener false
        }

        gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setOnClickListener { }
        gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    springRandomRight.endValue = (0.7)

                }
                MotionEvent.ACTION_UP -> {
                    springRandomRight.endValue = (0.0)

                }
            }
            return@setOnTouchListener false
        }
    }

    /*
     *
     * Shuffle Functions
     *
     */
    private fun shuffleProcessPosition() {
        functionsClassGame.playShuffleMagicalNumbersPosition()

        val listOfDivisibleShuffle = ArrayList<Int>()
        listOfDivisibleShuffle.add((gamePlayViewBinding.gamePlayControlViewInclude.randomTop.currentView as TextView).text.toString().toInt())
        listOfDivisibleShuffle.add((gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.currentView as TextView).text.toString().toInt())
        listOfDivisibleShuffle.add((gamePlayViewBinding.gamePlayControlViewInclude.randomRight.currentView as TextView).text.toString().toInt())

        val topValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(topValueRandom)
        GameVariablesViewModel.TOP_VALUE.value = topValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setText("${topValueRandom}")

        val leftValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(leftValueRandom)
        GameVariablesViewModel.LEFT_VALUE.value = leftValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setText("${leftValueRandom}")

        val rightValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(rightValueRandom)
        GameVariablesViewModel.RIGHT_VALUE.value = rightValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setText("${rightValueRandom}")

        GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.value = 0
    }

    private fun shuffleProcessValue() {
        functionsClassGame.playShuffleMagicalNumbersValues()

        val listOfDivisibleShuffle = ArrayList<Int>()
        listOfDivisibleShuffle.addAll(2..9)

        val topValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(topValueRandom)
        GameVariablesViewModel.TOP_VALUE.value = topValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setText("${topValueRandom}")

        val leftValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(leftValueRandom)
        GameVariablesViewModel.LEFT_VALUE.value = leftValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setText("${leftValueRandom}")

        val rightValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(rightValueRandom)
        GameVariablesViewModel.RIGHT_VALUE.value = rightValueRandom
        gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setText("${rightValueRandom}")

        GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.value = 0
    }

    /*
     *
     * Points Functions
     *
     */
    private fun scanPointsChange() {
        if (GamePlay.RestoreGameState) {

            gamePlayViewBinding.gamePlayInformationViewInclude.pointsTotalView.setText("${functionsClassGameIO.readTotalPoints()}")
            GameLevel.GAME_DIFFICULTY_LEVEL = functionsClassGameIO.readLevelProcess()

        } else {

            functionsClassGameIO.saveLevelProcess(1)

            functionsClassGameIO.saveTotalPoints(0)

            functionsClassGameIO.saveDivisiblePositivePoints(0)
            functionsClassGameIO.savePrimePositivePoints(0)
            functionsClassGameIO.saveCenterChangePositivePoints(0)


            functionsClassGameIO.saveDivisibleNegativePoints(0)
            functionsClassGameIO.savePrimeNegativePoints(0)
            functionsClassGameIO.saveCenterChangeNegativePoints(0)
        }

        GameVariablesViewModel.POSITIVE_POINT.value = 0
        GameVariablesViewModel.DIVISIBLE_POSITIVE_POINT.value = 0
        GameVariablesViewModel.PRIME_POSITIVE_POINT.value = 0
        GameVariablesViewModel.CHANGE_CENTER_RANDOM_POSITIVE_POINT.value = 0

        GameVariablesViewModel.NEGATIVE_POINT.value = 0
        GameVariablesViewModel.DIVISIBLE_NEGATIVE_POINT.value = 0
        GameVariablesViewModel.PRIME_NEGATIVE_POINT.value = 0
        GameVariablesViewModel.CHANGE_CENTER_RANDOM_NEGATIVE_POINT.value = 0

        /*
         * Positive Points
         */
        GameVariablesViewModel.POSITIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newPositivePoint: Int?) {

                if (newPositivePoint != 0) {
                    val fadeAnimationEarningPoints = AnimationUtils.loadAnimation(
                        applicationContext,
                        R.anim.fade_animation_earning_points
                    )
                    fadeAnimationEarningPoints.setAnimationListener(object :
                        Animation.AnimationListener {
                        override fun onAnimationRepeat(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            Handler().postDelayed({
                                countDownTimer.cancel()

                                gamePlayViewBinding.gamePlayInformationViewInclude.timerProgressBar.setTrackEnabled(true)
                                gamePlayViewBinding.gamePlayInformationViewInclude.timerProgressBar.setTrackColor(getColor(R.color.default_color_darker))

                                valueAnimatorProgressBar.start()
                                countDownTimer.start()
                            }, 777)
                        }

                        override fun onAnimationStart(animation: Animation?) {

                        }
                    })

                    gamePlayViewBinding.gamePlayInformationViewInclude.pointsTotalViewOne.setTextColor(getColor(R.color.green))
                    gamePlayViewBinding.gamePlayInformationViewInclude.pointsTotalViewTwo.setTextColor(getColor(R.color.green))

                    gamePlayViewBinding.pointsEarning.setTextColor(getColor(R.color.green))
                    gamePlayViewBinding.pointsEarning.text = "+${newPositivePoint}"
                    gamePlayViewBinding.pointsEarning.append(
                        if (gameLevel.getGameDifficultyLevel() == 1) {
                            ""
                        } else {
                            " x ${gameLevel.getGameDifficultyLevel()}"
                        }
                    )

                    gamePlayViewBinding.pointsEarning.startAnimation(fadeAnimationEarningPoints)
                }

                val totalSavePoint: Int = functionsClassGameIO.readTotalPoints()
                val totalNewPoint: Int =
                    totalSavePoint + (newPositivePoint!! * gameLevel.getPointMultiplier())

                functionsClassGameIO.saveTotalPoints(totalNewPoint)
                gamePlayViewBinding.gamePlayInformationViewInclude.pointsTotalView.setText("${totalNewPoint}")

                FunctionsClassDebug.PrintDebug("POSITIVE_POINT | ${totalNewPoint} ::: ${newPositivePoint}")
            }
        })

        GameVariablesViewModel.DIVISIBLE_POSITIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newPositivePoint: Int?) {
                FunctionsClassDebug.PrintDebug("DIVISIBLE_POSITIVE_POINT ::: ${newPositivePoint}")

                val point = functionsClassGameIO.readDivisiblePositivePoints()
                val newPoint = point + newPositivePoint!!
                functionsClassGameIO.saveDivisiblePositivePoints(newPoint)
            }
        })

        GameVariablesViewModel.PRIME_POSITIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newPositivePoint: Int?) {
                FunctionsClassDebug.PrintDebug("PRIME_POSITIVE_POINT ::: ${newPositivePoint}")

                val point = functionsClassGameIO.readPrimePositivePoints()
                val newPoint = point + newPositivePoint!!
                functionsClassGameIO.savePrimePositivePoints(newPoint)
            }
        })

        GameVariablesViewModel.CHANGE_CENTER_RANDOM_POSITIVE_POINT.observe(
            this,
            object : Observer<Int> {
                override fun onChanged(newPositivePoint: Int?) {
                    FunctionsClassDebug.PrintDebug("CHANGE_CENTER_RANDOM_POSITIVE_POINT ::: ${newPositivePoint}")

                    val point = functionsClassGameIO.readCenterChangePositivePoints()
                    val newPoint = point + newPositivePoint!!
                    functionsClassGameIO.saveCenterChangePositivePoints(newPoint)
                }
            })

        /*
         * Negative Points
         */
        GameVariablesViewModel.NEGATIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newNegativePoint: Int?) {

                if (newNegativePoint != 0) {
                    val fadeAnimationEarningPoints = AnimationUtils.loadAnimation(
                        applicationContext,
                        R.anim.fade_animation_earning_points
                    )
                    fadeAnimationEarningPoints.setAnimationListener(object :
                        Animation.AnimationListener {
                        override fun onAnimationRepeat(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            countDownTimer.cancel()

                            gamePlayViewBinding.gamePlayInformationViewInclude.timerProgressBar.setTrackEnabled(true)
                            gamePlayViewBinding.gamePlayInformationViewInclude.timerProgressBar.setTrackColor(getColor(R.color.default_color_darker))

                            valueAnimatorProgressBar.start()
                            countDownTimer.start()
                        }

                        override fun onAnimationStart(animation: Animation?) {

                        }
                    })

                    gamePlayViewBinding.gamePlayInformationViewInclude.pointsTotalViewOne.setTextColor(getColor(R.color.red))
                    gamePlayViewBinding.gamePlayInformationViewInclude.pointsTotalViewTwo.setTextColor(getColor(R.color.red))

                    gamePlayViewBinding.pointsEarning.setTextColor(getColor(R.color.red))
                    gamePlayViewBinding.pointsEarning.text = "-${newNegativePoint}"
                    gamePlayViewBinding.pointsEarning.append(
                        if (gameLevel.getGameDifficultyLevel() == 1) {
                            ""
                        } else {
                            " x ${gameLevel.getGameDifficultyLevel()}"
                        }
                    )

                    gamePlayViewBinding.pointsEarning.startAnimation(fadeAnimationEarningPoints)
                }

                val totalSavePoint: Int = functionsClassGameIO.readTotalPoints()
                val totalNewPoint: Int =
                    totalSavePoint - (newNegativePoint!! * gameLevel.getPointMultiplier())

                functionsClassGameIO.saveTotalPoints(totalNewPoint)
                gamePlayViewBinding.gamePlayInformationViewInclude.pointsTotalView.setText("${totalNewPoint}")

                FunctionsClassDebug.PrintDebug("NEGATIVE_POINT | ${totalNewPoint} ::: ${newNegativePoint}")
            }
        })

        GameVariablesViewModel.DIVISIBLE_NEGATIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newNegativePoint: Int?) {
                FunctionsClassDebug.PrintDebug("DIVISIBLE_NEGATIVE_POINT ::: ${newNegativePoint}")

                val point = functionsClassGameIO.readDivisibleNegativePoints()
                val newPoint = point - newNegativePoint!!
                functionsClassGameIO.saveDivisibleNegativePoints(newPoint)
            }
        })

        GameVariablesViewModel.PRIME_NEGATIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newNegativePoint: Int?) {
                FunctionsClassDebug.PrintDebug("PRIME_NEGATIVE_POINT ::: ${newNegativePoint}")

                val point = functionsClassGameIO.readPrimeNegativePoints()
                val newPoint = point - newNegativePoint!!
                functionsClassGameIO.savePrimeNegativePoints(newPoint)
            }
        })

        GameVariablesViewModel.CHANGE_CENTER_RANDOM_NEGATIVE_POINT.observe(
            this,
            object : Observer<Int> {
                override fun onChanged(newNegativePoint: Int?) {
                    FunctionsClassDebug.PrintDebug("CHANGE_CENTER_RANDOM_NEGATIVE_POINT ::: ${newNegativePoint}")

                    val point = functionsClassGameIO.readCenterChangeNegativePoints()
                    val newPoint = point - newNegativePoint!!
                    functionsClassGameIO.saveCenterChangeNegativePoints(newPoint)
                }
            })
    }
}
