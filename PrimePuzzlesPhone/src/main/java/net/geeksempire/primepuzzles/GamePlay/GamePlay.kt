package net.geeksempire.primepuzzles.GamePlay

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.game_play_control_view.*
import kotlinx.android.synthetic.main.game_play_prime_number_detected_view.*
import kotlinx.android.synthetic.main.game_play_view.*
import net.geeksempire.physics.animation.Core.SimpleSpringListener
import net.geeksempire.physics.animation.Core.Spring
import net.geeksempire.physics.animation.Core.SpringConfig
import net.geeksempire.physics.animation.SpringSystem
import net.geeksempire.primepuzzles.GameInformation.GameInformationVariable
import net.geeksempire.primepuzzles.GameLogic.GameVariables
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGame
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassUI
import net.geeksempire.primepuzzles.Utils.FunctionsClass.generateHint

class GamePlay : AppCompatActivity() {

    private lateinit var functionsClassUI: FunctionsClassUI
    private lateinit var functionsClassGame: FunctionsClassGame

    private lateinit var gameVariables: GameVariables

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

        functionsClassUI = FunctionsClassUI(applicationContext)
        functionsClassGame = FunctionsClassGame(applicationContext)

        val layoutParams = gameControlInclude.layoutParams
        layoutParams.height = functionsClassUI.displayX()
        gameControlInclude.layoutParams = layoutParams

        gameVariables = ViewModelProviders.of(this).get(GameVariables::class.java)

        gesturedRandomCenter.bringToFront()
        primeNumberDetectedInclude.bringToFront()

        val listOfDivisible = ArrayList<Int>()
        listOfDivisible.addAll(2..9)

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

        GameVariables.PRIME_NUMBER_DETECTED.observe(this, object : Observer<Boolean> {
            override fun onChanged(primeDetected: Boolean?) {
                if (primeDetected!!) {
                    functionsClassGame.playLongPrimeDetectionSound()

                    detectedPrimeNumber.text = "${GameVariables.CENTER_VALUE.value!!}"

                    Handler().postDelayed({
                        functionsClassUI.circularRevealAnimation(
                            primeNumberDetectedInclude,
                            primeNumbers.y + (primeNumbers.height/2),
                            primeNumbers.x + (primeNumbers.width/2),
                            1f
                        )
                    }, 99)
                }
            }
        })

        GameVariables.SHUFFLE_PROCESS_POSITION.value = 0
        GameVariables.SHUFFLE_PROCESS_POSITION.observe(this, object : Observer<Int> {
            override fun onChanged(newShufflePosition: Int?) {

                if (newShufflePosition!! >= 7) {
                    shuffleProcessPosition()
                }
            }
        })

        GameVariables.SHUFFLE_PROCESS_VALUE.value = 0
        GameVariables.SHUFFLE_PROCESS_VALUE.observe(this, object : Observer<Int> {
            override fun onChanged(newShuffleValue: Int?) {

                if (newShuffleValue!! >= 21) {
                    shuffleProcessValue()
                }
            }
        })

        setupThreeRandomViews()
    }

    override fun onStart() {
        super.onStart()

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
                                    val hintData: String = generateHint()

                                }
                                GameInformationVariable.PRIME_NUMBER_ACTION -> {
                                    functionsClassUI.circularHideAnimation(
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
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
        super.onResume()


    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {

    }

    private fun setupThreeRandomViews() {

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

    private fun shuffleProcessPosition() {
        functionsClassGame.playShuffleMagicalNumbersPosition()

        val listOfDivisibleShuffle = ArrayList<Int>()
        listOfDivisibleShuffle.add(randomTop.text.toString().toInt())
        listOfDivisibleShuffle.add(randomLeft.text.toString().toInt())
        listOfDivisibleShuffle.add(randomRight.text.toString().toInt())

        val topValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(topValueRandom)
        GameVariables.TOP_VALUE.value = topValueRandom
        randomTop.text = "${topValueRandom}"

        val leftValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(leftValueRandom)
        GameVariables.LEFT_VALUE.value = leftValueRandom
        randomLeft.text = "${leftValueRandom}"

        val rightValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(rightValueRandom)
        GameVariables.RIGHT_VALUE.value = rightValueRandom
        randomRight.text = "${rightValueRandom}"

        GameVariables.SHUFFLE_PROCESS_POSITION.value = 0
    }

    private fun shuffleProcessValue() {
        functionsClassGame.playShuffleMagicalNumbersValues()

        val listOfDivisibleShuffle = ArrayList<Int>()
        listOfDivisibleShuffle.addAll(2..9)

        val topValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(topValueRandom)
        GameVariables.TOP_VALUE.value = topValueRandom
        randomTop.text = "${topValueRandom}"

        val leftValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(leftValueRandom)
        GameVariables.LEFT_VALUE.value = leftValueRandom
        randomLeft.text = "${leftValueRandom}"

        val rightValueRandom = listOfDivisibleShuffle.random()
        listOfDivisibleShuffle.remove(rightValueRandom)
        GameVariables.RIGHT_VALUE.value = rightValueRandom
        randomRight.text = "${rightValueRandom}"

        GameVariables.SHUFFLE_PROCESS_VALUE.value = 0
    }
}
