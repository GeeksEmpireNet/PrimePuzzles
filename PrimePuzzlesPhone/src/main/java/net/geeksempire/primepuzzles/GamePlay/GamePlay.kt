package net.geeksempire.primepuzzles.GamePlay

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.game_play_control_view.*
import kotlinx.android.synthetic.main.game_play_view.*
import kotlinx.android.synthetic.main.prime_number_detected_view.*
import net.geeksempire.primepuzzles.GameInformation.GameInformationVariable
import net.geeksempire.primepuzzles.GameLogic.GameVariables
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassUI
import net.geeksempire.primepuzzles.Utils.FunctionsClass.generateHint


class GamePlay : AppCompatActivity() {

    private lateinit var functionsClassUI: FunctionsClassUI

    private lateinit var gameVariables: GameVariables

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
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
                    detectedPrimeNumber.text = "${GameVariables.CENTER_VALUE.value!!}"

                    Handler().postDelayed({
                        functionsClassUI.circularRevealAnimation(
                            primeNumberDetectedInclude,
                            primeNumbers.y + (primeNumbers.height/2),
                            primeNumbers.x + (primeNumbers.width/2),
                            1f
                        )
                    }, 321)
                }
            }
        })
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
                getColor(R.color.dark_transparent),
                getColor(R.color.darker),
                getColor(R.color.dark_transparent)
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

                                    Handler().postDelayed({
                                        functionsClassUI.circularHideAnimation(
                                            primeNumberDetectedInclude,
                                            primeNumbers.y + (primeNumbers.height/2),
                                            primeNumbers.x + (primeNumbers.width/2),
                                            1f
                                        )
                                    }, 321)
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
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {

    }
}
