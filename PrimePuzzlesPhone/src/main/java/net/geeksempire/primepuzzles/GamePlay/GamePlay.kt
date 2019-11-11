package net.geeksempire.primepuzzles.GamePlay

import android.animation.Animator
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
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.game_play_control_view.*
import kotlinx.android.synthetic.main.game_play_information_view.*
import kotlinx.android.synthetic.main.game_play_prime_number_detected_view.*
import kotlinx.android.synthetic.main.game_play_view.*
import net.geeksempire.physics.animation.Core.SimpleSpringListener
import net.geeksempire.physics.animation.Core.Spring
import net.geeksempire.physics.animation.Core.SpringConfig
import net.geeksempire.physics.animation.SpringSystem
import net.geeksempire.primepuzzles.BuildConfig
import net.geeksempire.primepuzzles.GameInformation.GameInformationVariable
import net.geeksempire.primepuzzles.GameInformation.GameVariables
import net.geeksempire.primepuzzles.GameLogic.GameLevel
import net.geeksempire.primepuzzles.GameLogic.GameOperations
import net.geeksempire.primepuzzles.GameLogic.GameSettings
import net.geeksempire.primepuzzles.GamePlay.Utils.CountDownTimer
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGame
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGameIO
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassUI
import kotlin.math.hypot

class GamePlay : AppCompatActivity() {

    private lateinit var functionsClassUI: FunctionsClassUI
    private lateinit var functionsClassGame: FunctionsClassGame
    private lateinit var functionsClassGameIO: FunctionsClassGameIO

    private lateinit var gameVariables: GameVariables

    companion object {
        var RestoreGameState = false

        lateinit var countDownTimer: CountDownTimer
        var lastThickTimer: Long = 14000
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
        GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = 0

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        setContentView(R.layout.game_play_view)
        MobileAds.initialize(this) { initializationStatus ->

            if (!BuildConfig.DEBUG) {
                setUpAds()
            }
        }
        countDownTimer()

        gesturedRandomCenter.bringToFront()
        primeNumberDetectedInclude.bringToFront()

        functionsClassUI = FunctionsClassUI(applicationContext)
        functionsClassGame = FunctionsClassGame(applicationContext)
        functionsClassGameIO = FunctionsClassGameIO(applicationContext)

        val rootLayout = this.window.decorView
        rootLayout.visibility = View.INVISIBLE
        val viewTreeObserver = rootLayout.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val finalRadius = hypot(functionsClassUI.displayX().toDouble(), functionsClassUI.displayY().toDouble()).toInt()
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

        val adViewLayoutParams = adViews.layoutParams as RelativeLayout.LayoutParams
        val statusBarHeight = if (intent.getIntExtra("StatusBarHeight", functionsClassUI.DpToInteger(24f).toInt()) == 0) {
            intent.getIntExtra("StatusBarHeight", functionsClassUI.DpToInteger(24f).toInt())
        } else {
            functionsClassUI.DpToInteger(24f).toInt()
        }
        adViewLayoutParams.height = adViewLayoutParams.height + statusBarHeight
        adViews.setPadding(0, statusBarHeight, 0, 0)
        adViews.layoutParams = adViewLayoutParams

        val layoutParams = gameControlInclude.layoutParams
        layoutParams.height = functionsClassUI.displayX()
        gameControlInclude.layoutParams = layoutParams

        gameVariables = ViewModelProviders.of(this).get(GameVariables::class.java)

        val listOfDivisible = ArrayList<Int>()
        listOfDivisible.addAll(2..9)

        val topValueRandom = listOfDivisible.random()
        listOfDivisible.remove(topValueRandom)
        GameVariables.TOP_VALUE.value = topValueRandom
        randomTop.setText("${topValueRandom}")

        val leftValueRandom = listOfDivisible.random()
        listOfDivisible.remove(leftValueRandom)
        GameVariables.LEFT_VALUE.value = leftValueRandom
        randomLeft.setText("${leftValueRandom}")

        val rightValueRandom = listOfDivisible.random()
        listOfDivisible.remove(rightValueRandom)
        GameVariables.RIGHT_VALUE.value = rightValueRandom
        randomRight.setText("${rightValueRandom}")

        Handler().postDelayed({

            GamePlay.countDownTimer.start()
        }, 1999)
    }

    override fun onStart() {
        super.onStart()

        GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.observe(this,
            Observer<Int> { newDifficultyLevel ->
                when (GameLevel().getGameDifficultyLevel()) {
                    GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {//2..9
                        if (newDifficultyLevel!! >= 7) {
                            GameLevel.GAME_DIFFICULTY_LEVEL++
                            if (GameLevel.GAME_DIFFICULTY_LEVEL == 5) {
                                //The End
                            }
                            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = 0
                        }
                    }
                    GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {//10..99
                        if (newDifficultyLevel!! >= 77) {
                            GameLevel.GAME_DIFFICULTY_LEVEL++
                            if (GameLevel.GAME_DIFFICULTY_LEVEL == 5) {
                                //The End
                            }
                            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = 0
                        }
                    }
                    GameLevel.GAME_DIFFICULTY_LEVEL_THREE_DIGIT-> {//100..999
                        if (newDifficultyLevel!! >= 777) {
                            GameLevel.GAME_DIFFICULTY_LEVEL++
                            if (GameLevel.GAME_DIFFICULTY_LEVEL == 5) {
                                //The End
                            }
                            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = 0
                        }
                    }
                    GameLevel.GAME_DIFFICULTY_LEVEL_FOUR_DIGIT-> {//1000..9999
                        if (newDifficultyLevel!! >= 7777) {
                            GameLevel.GAME_DIFFICULTY_LEVEL++
                            if (GameLevel.GAME_DIFFICULTY_LEVEL == 5) {
                                //The End
                            }
                            GameVariables.GAME_LEVEL_DIFFICULTY_COUNTER.value = 0
                        }
                    }
                }
                functionsClassGameIO.saveLevelProcess(GameLevel().getGameDifficultyLevel())
            })


        GameVariables.PRIME_NUMBER_DETECTED.observe(this,
            Observer<Boolean> { primeDetected ->
                if (primeDetected!!) {
                    functionsClassGame.playLongPrimeDetectionSound()

                    detectedPrimeNumber.text = "${GameVariables.CENTER_VALUE.value!!}"

                    Handler().postDelayed({
                        functionsClassUI.circularRevealAnimationPrimeNumber(
                            primeNumberDetectedInclude,
                            primeNumbers.y + (primeNumbers.height/2),
                            primeNumbers.x + (primeNumbers.width/2),
                            1f
                        )
                    }, 99)
                }
            })


        GameVariables.SHUFFLE_PROCESS_POSITION.value = 0
        GameVariables.SHUFFLE_PROCESS_POSITION.observe(this,
            Observer<Int> { newShufflePosition ->
                if (newShufflePosition!! >= 7) {
                    shuffleProcessPosition()
                }
            })

        GameVariables.SHUFFLE_PROCESS_VALUE.value = 0
        GameVariables.SHUFFLE_PROCESS_VALUE.observe(this,
            Observer<Int> { newShuffleValue ->
                if (newShuffleValue!! >= 21) {
                    shuffleProcessValue()
                }
            })


        GameVariables.CENTER_VALUE.observe(this,
            Observer<Int> { newCenterValue ->
                gesturedRandomCenter.setText("${newCenterValue}")
            })

        GameVariables.TOP_VALUE.observe(this,
            Observer<Int> {
                    newTopValue -> randomTop.setText("${newTopValue}")
            })
        GameVariables.LEFT_VALUE.observe(this,
            Observer<Int> { newLeftValue ->
                randomLeft.setText("${newLeftValue}")
            })
        GameVariables.RIGHT_VALUE.observe(this,
            Observer<Int> { newRightValue ->
                randomRight.setText("${newRightValue}")
            })


        val snackbarHint = Snackbar.make(
            fullGamePlay,
            Html.fromHtml(GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT, Html.FROM_HTML_MODE_LEGACY),
            Snackbar.LENGTH_INDEFINITE)
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

        GameVariables.TOGGLE_SNACKBAR.observe(this, object : Observer<Boolean> {
            override fun onChanged(newToggleSnackBar: Boolean?) {
                if (newToggleSnackBar!!) {
                    snackbarHint.setAction(GameInformationVariable.SNACKBAR_HINT_BUTTON_TEXT, object : View.OnClickListener {
                        override fun onClick(view: View?) {
                            when (GameInformationVariable.snackBarAction) {
                                GameInformationVariable.HINT_ACTION -> {
                                    val hintData: String = GameOperations().generateHint()

                                }
                                GameInformationVariable.PRIME_NUMBER_ACTION -> {
                                    functionsClassUI.circularHideAnimationPrimeNumber(
                                        primeNumberDetectedInclude,
                                        primeNumbers.y + (primeNumbers.height/2),
                                        primeNumbers.x + (primeNumbers.width/2),
                                        1f
                                    )
                                }
                            }
                        }
                    })
                    snackbarHint.setText(Html.fromHtml(GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT, Html.FROM_HTML_MODE_LEGACY))
                    snackbarHint.show()
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

        GamePlay.countDownTimer.resume()
    }

    override fun onPause() {
        super.onPause()

        GamePlay.countDownTimer.pause()
    }

    override fun onBackPressed() {

    }

    /*
     *
     * Ads Functions
     *
     */
    private fun setUpAds() {
        val adRequest = AdRequest.Builder()
            .addTestDevice("F54D998BCE077711A17272B899B44798")
            .addTestDevice("DD428143B4772EC7AA87D1E2F9DA787C")
            .addKeyword("Game")
            .build()

        /*Banner Ads*/
        adViewBannerGamePlay.loadAd(adRequest)
        adViewBannerGamePlay.adListener = object: AdListener() {
            override fun onAdLoaded() {
                FunctionsClassDebug.PrintDebug("Banner Ads Loaded")

            }

            override fun onAdFailedToLoad(errorCode : Int) {
                FunctionsClassDebug.PrintDebug("Banner Ads Failed")

            }

            override fun onAdOpened() {
                FunctionsClassDebug.PrintDebug("Banner Ads Opened")

            }

            override fun onAdClicked() {
                FunctionsClassDebug.PrintDebug("Banner Ads Clicked")

            }

            override fun onAdLeftApplication() {

            }

            override fun onAdClosed() {

            }
        }
        /*Interstitial Ads*/


        /*Rewarded Ads*/
    }

    /*
     *
     * Sides Random Values Functions
     *
     */
    private fun setupViews() {

        functionsClassUI.shadowValueAnimatorLoop(primeNumbers,
            19, 3,
            1333, 777,
            getColor(R.color.lighter), 0f, 0f)

        val springSystem = SpringSystem.create()

        val springRandomTop = springSystem.createSpring()
        val springRandomLeft = springSystem.createSpring()
        val springRandomRight = springSystem.createSpring()

        springRandomTop.addListener(object : SimpleSpringListener(/*spring*/) {
            override fun onSpringUpdate(spring: Spring?) {
                val value = spring!!.currentValue.toFloat()
                val scale = 1f - (value * 0.5f)

                randomTop.scaleX = scale
                randomTop.scaleY = scale
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

                randomLeft.scaleX = scale
                randomLeft.scaleY = scale
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

                randomRight.scaleX = scale
                randomRight.scaleY = scale
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


        randomTop.setOnTouchListener { view, motionEvent ->
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
        randomTop.setOnClickListener {}

        randomLeft.setOnClickListener {  }
        randomLeft.setOnTouchListener { view, motionEvent ->
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

        randomRight.setOnClickListener {  }
        randomRight.setOnTouchListener { view, motionEvent ->
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
        listOfDivisibleShuffle.add((randomTop.currentView as TextView).text.toString().toInt())
        listOfDivisibleShuffle.add((randomLeft.currentView as TextView).text.toString().toInt())
        listOfDivisibleShuffle.add((randomRight.currentView as TextView).text.toString().toInt())

        val topValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(topValueRandom)
        GameVariables.TOP_VALUE.value = topValueRandom
        randomTop.setText("${topValueRandom}")

        val leftValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(leftValueRandom)
        GameVariables.LEFT_VALUE.value = leftValueRandom
        randomLeft.setText("${leftValueRandom}")

        val rightValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(rightValueRandom)
        GameVariables.RIGHT_VALUE.value = rightValueRandom
        randomRight.setText("${rightValueRandom}")

        GameVariables.SHUFFLE_PROCESS_POSITION.value = 0
    }

    private fun shuffleProcessValue() {
        functionsClassGame.playShuffleMagicalNumbersValues()

        val listOfDivisibleShuffle = ArrayList<Int>()
        listOfDivisibleShuffle.addAll(2..9)

        val topValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(topValueRandom)
        GameVariables.TOP_VALUE.value = topValueRandom
        randomTop.setText("${topValueRandom}")

        val leftValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(leftValueRandom)
        GameVariables.LEFT_VALUE.value = leftValueRandom
        randomLeft.setText("${leftValueRandom}")

        val rightValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(rightValueRandom)
        GameVariables.RIGHT_VALUE.value = rightValueRandom
        randomRight.setText("${rightValueRandom}")

        GameVariables.SHUFFLE_PROCESS_VALUE.value = 0
    }

    /*
     *
     * Points Functions
     *
     */
    private fun scanPointsChange() {

        if (GamePlay.RestoreGameState) {

            pointsTotalView.setText("${functionsClassGameIO.readTotalPoints()}")
            GameLevel.GAME_DIFFICULTY_LEVEL = functionsClassGameIO.readLevelProcess()
        } else {

            functionsClassGameIO.saveTotalPoints(0)
            functionsClassGameIO.saveLevelProcess(1)
        }

        GameVariables.DIVISIBLE_POSITIVE_POINT.value = 0
        GameVariables.PRIME_POSITIVE_POINT.value = 0
        GameVariables.CHANGE_CENTER_RANDOM_POSITIVE_POINT.value = 0
        GameVariables.DIVISIBLE_NEGATIVE_POINT.value = 0
        GameVariables.PRIME_NEGATIVE_POINT.value = 0
        GameVariables.CHANGE_CENTER_RANDOM_NEGATIVE_POINT.value = 0

        /*
         * Positive Points
         */
        GameVariables.DIVISIBLE_POSITIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newPositivePoint: Int?) {

                if (newPositivePoint != 0) {
                    val fadeAnimationEarningPoints = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_animation_earning_points)
                    fadeAnimationEarningPoints.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            Handler().postDelayed({

                                timerTotalViewOne.setTextColor(getColor(R.color.light))
                                timerTotalViewTwo.setTextColor(getColor(R.color.light))

                                GamePlay.countDownTimer.cancel()
                                GamePlay.countDownTimer.start()
                            }, 777)
                        }

                        override fun onAnimationStart(animation: Animation?) {

                        }
                    })

                    pointsTotalViewOne.setTextColor(getColor(R.color.green))
                    pointsTotalViewTwo.setTextColor(getColor(R.color.green))

                    pointsEarning.setTextColor(getColor(R.color.green))
                    pointsEarning.text = "+${newPositivePoint}"
                    pointsEarning.append(if(GameLevel().getGameDifficultyLevel() == 1){ "" } else { " x ${GameLevel().getGameDifficultyLevel()}" })

                    pointsEarning.startAnimation(fadeAnimationEarningPoints)
                }

                val totalSavePoint: Int = functionsClassGameIO.readTotalPoints()
                val totalNewPoint: Int = totalSavePoint + (newPositivePoint!! * GameLevel().getPointMultiplier())

                functionsClassGameIO.saveTotalPoints(totalNewPoint)
                pointsTotalView.setText("${totalNewPoint}")

                FunctionsClassDebug.PrintDebug("DIVISIBLE_POSITIVE_POINT | ${totalNewPoint} ::: ${newPositivePoint}")
            }
        })

        GameVariables.PRIME_POSITIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newPositivePoint: Int?) {
                FunctionsClassDebug.PrintDebug("DIVISIBLE_POSITIVE_POINT ::: ${newPositivePoint}")

                val totalSavePoint: Int = functionsClassGameIO.readTotalPoints()
                val totalNewPoint: Int = totalSavePoint + (newPositivePoint!! * GameLevel().getPointMultiplier())

                functionsClassGameIO.saveTotalPoints(totalNewPoint)
                pointsTotalView.setText("${totalNewPoint}")
            }
        })

        GameVariables.CHANGE_CENTER_RANDOM_POSITIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newPositivePoint: Int?) {
                FunctionsClassDebug.PrintDebug("DIVISIBLE_POSITIVE_POINT ::: ${newPositivePoint}")

                val totalSavePoint: Int = functionsClassGameIO.readTotalPoints()
                val totalNewPoint: Int = totalSavePoint + (newPositivePoint!! * GameLevel().getPointMultiplier())

                functionsClassGameIO.saveTotalPoints(totalNewPoint)
                pointsTotalView.setText("${totalNewPoint}")
            }
        })
        /*
         * Negative Points
         */
        GameVariables.DIVISIBLE_NEGATIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newNegativePoint: Int?) {

                if (newNegativePoint != 0) {
                    val fadeAnimationEarningPoints = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_animation_earning_points)
                    fadeAnimationEarningPoints.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            Handler().postDelayed({

                                timerTotalViewOne.setTextColor(getColor(R.color.light))
                                timerTotalViewTwo.setTextColor(getColor(R.color.light))

                                GamePlay.countDownTimer.cancel()
                                GamePlay.countDownTimer.start()
                            }, 777)
                        }

                        override fun onAnimationStart(animation: Animation?) {

                        }
                    })

                    pointsTotalViewOne.setTextColor(getColor(R.color.red))
                    pointsTotalViewTwo.setTextColor(getColor(R.color.red))

                    pointsEarning.setTextColor(getColor(R.color.red))
                    pointsEarning.text = "-${newNegativePoint}"
                    pointsEarning.append(if(GameLevel().getGameDifficultyLevel() == 1){ "" } else { " x ${GameLevel().getGameDifficultyLevel()}" })

                    pointsEarning.startAnimation(fadeAnimationEarningPoints)
                }

                val totalSavePoint: Int = functionsClassGameIO.readTotalPoints()
                val totalNewPoint: Int = totalSavePoint - (newNegativePoint!! * GameLevel().getPointMultiplier())

                functionsClassGameIO.saveTotalPoints(totalNewPoint)
                pointsTotalView.setText("${totalNewPoint}")

                FunctionsClassDebug.PrintDebug("DIVISIBLE_NEGATIVE_POINT | ${totalNewPoint} ::: ${newNegativePoint}")
            }
        })

        GameVariables.PRIME_NEGATIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newNegativePoint: Int?) {
                FunctionsClassDebug.PrintDebug("DIVISIBLE_NEGATIVE_POINT ::: ${newNegativePoint}")

                val totalSavePoint: Int = functionsClassGameIO.readTotalPoints()
                val totalNewPoint: Int = totalSavePoint - (newNegativePoint!! * GameLevel().getPointMultiplier())

                functionsClassGameIO.saveTotalPoints(totalNewPoint)
                pointsTotalView.setText("${totalNewPoint}")
            }
        })

        GameVariables.CHANGE_CENTER_RANDOM_NEGATIVE_POINT.observe(this, object : Observer<Int> {
            override fun onChanged(newNegativePoint: Int?) {
                FunctionsClassDebug.PrintDebug("DIVISIBLE_NEGATIVE_POINT ::: ${newNegativePoint}")

                val totalSavePoint: Int = functionsClassGameIO.readTotalPoints()
                val totalNewPoint: Int = totalSavePoint - (newNegativePoint!! * GameLevel().getPointMultiplier())

                functionsClassGameIO.saveTotalPoints(totalNewPoint)
                pointsTotalView.setText("${totalNewPoint}")
            }
        })
    }

    /*
     *
     * Timer Functions
     *
     */
    private fun countDownTimer() : CountDownTimer {

        GamePlay.countDownTimer = object : CountDownTimer(GamePlay.lastThickTimer, 1) {

            override fun onTick(millisUntilFinished: Long) {
                GamePlay.lastThickTimer = millisUntilFinished

                val newSecond: Long = (millisUntilFinished / 1000)
                if (newSecond <= 5) {
                    timerTotalViewOne.setTextColor(getColor(R.color.default_color_game_light))
                    timerTotalViewTwo.setTextColor(getColor(R.color.default_color_game_light))
                }
                timerTotalView.setText("${newSecond}")
            }

            override fun onFinish() {
                //GamePlay.lastThickTimer = 14000

                //WRONG ANSWER
                FunctionsClassDebug.PrintDebug("WRONG ANSWER")

                functionsClassGame.playWrongSound()

                GameVariables.DIVISIBLE_NEGATIVE_POINT.value = 3
            }
        }

        return GamePlay.countDownTimer
    }
}
