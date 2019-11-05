package net.geeksempire.primepuzzles.Utils.UI

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.text.Html
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.google.android.material.snackbar.Snackbar
import net.geeksempire.physics.animation.Core.SimpleSpringListener
import net.geeksempire.physics.animation.Core.Spring
import net.geeksempire.physics.animation.Core.SpringConfig
import net.geeksempire.physics.animation.SpringSystem
import net.geeksempire.primepuzzles.GameLogic.GameOperations
import net.geeksempire.primepuzzles.GameLogic.GameVariables
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassUI
import net.geeksempire.primepuzzles.Utils.FunctionsClass.isNumbersDivisible
import kotlin.math.abs

class SwipeGestureFilterRandomCenter(private val view: Button, initContext: Context, private val gestureListener: GestureListener) : SimpleOnGestureListener() {

    private val context: Context = initContext

    lateinit var functionsClassUI: FunctionsClassUI

    private var swipeMinDistance: Int  = 10
    private var swipeMaxDistance: Int  = 1000

    private var swipeMinVelocity: Int  = 10

    private var mode: Int = MODE_DYNAMIC

    private var swipeMode: Int = 0

    private var running: Boolean = true


    private var tapIndicator = false
    private val gestureDetector: GestureDetector


    init {
        this.gestureDetector = GestureDetector(initContext, this)

        functionsClassUI = FunctionsClassUI(initContext)
    }

    interface GestureListener {
        fun onSwipe(direction: Int)

        fun onSingleTapUp()
    }

    override fun onFling(downMotionEvent: MotionEvent, moveMotionEvent: MotionEvent, initVelocityX: Float, initVelocityY: Float): Boolean {

        val velocityX: Float = initVelocityX
        val velocityY: Float = initVelocityY

        val xDistance = abs(downMotionEvent.x - moveMotionEvent.x)
        val yDistance = abs(downMotionEvent.y - moveMotionEvent.y)

        if (xDistance > this.swipeMaxDistance || yDistance > this.swipeMaxDistance) {
            return false
        }

        val springForce: SpringForce by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SpringForce(0f).apply {
                stiffness = SpringForce.STIFFNESS_LOW
                dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
            }
        }

        val springAnimationTranslationX: SpringAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SpringAnimation(view, DynamicAnimation.TRANSLATION_X).setSpring(springForce)
        }
        val springAnimationTranslationY: SpringAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SpringAnimation(view, DynamicAnimation.TRANSLATION_Y).setSpring(springForce)
        }

        val flingAnimationX: FlingAnimation by lazy(LazyThreadSafetyMode.NONE) {
            FlingAnimation(view, DynamicAnimation.X)
                .setFriction(1.3f)
                .setMinValue(0f)
                .setMaxValue(context.resources.displayMetrics.widthPixels.toFloat() - view.width)
        }

        val flingAnimationY: FlingAnimation by lazy(LazyThreadSafetyMode.NONE) {
            FlingAnimation(view, DynamicAnimation.Y)
                .setFriction(1.3f)
                .setMinValue(0f)
                .setMaxValue(context.resources.displayMetrics.heightPixels.toFloat() - view.height)
        }

        flingAnimationX.addEndListener { animation, canceled, value, velocity ->

            when (swipeMode()) {
                SwipeGestureFilterRandomCenter.SWIPE_LEFT -> {
                    GameOperations().determineLeftValue()

                }
                SwipeGestureFilterRandomCenter.SWIPE_RIGHT -> {
                    GameOperations().determineRightValue()

                }
            }

            Handler()
                .postDelayed({
                    springAnimationTranslationX.start()
                    springAnimationTranslationY.start()

                    val listTOfRandom = ArrayList<Int>()
                    listTOfRandom.addAll(2..9)

                    val randomCenterValue: Int = listTOfRandom.random()
                    view.text = "${randomCenterValue}"
                    GameVariables.CENTER_VALUE.value = randomCenterValue
                }, 333)
        }

        flingAnimationY.addEndListener { animation, canceled, value, velocity ->

            when (swipeMode()) {
                SwipeGestureFilterRandomCenter.SWIPE_UP -> {
                    GameOperations().determineTopValue()

                }
                SwipeGestureFilterRandomCenter.SWIPE_DOWN -> {

                }
            }


            Handler()
                .postDelayed({
                    springAnimationTranslationX.start()
                    springAnimationTranslationY.start()

                    val listTOfRandom = ArrayList<Int>()
                    listTOfRandom.addAll(2..9)

                    val randomCenterValue: Int = listTOfRandom.random()
                    view.text = "${randomCenterValue}"
                    GameVariables.CENTER_VALUE.value = randomCenterValue
                }, 333)
        }

        var result = false
        if (abs(velocityY) >= this.swipeMinVelocity && yDistance > this.swipeMinDistance && xDistance < yDistance) {//Vertical
            if (downMotionEvent.y > moveMotionEvent.y) {//Bottom -> Up
                this.gestureListener.onSwipe(SWIPE_UP)
                swipeMode = SwipeGestureFilterRandomCenter.SWIPE_UP


            } else {//Up -> Bottom
                this.gestureListener.onSwipe(SWIPE_DOWN)
                swipeMode = SwipeGestureFilterRandomCenter.SWIPE_DOWN


            }

            flingAnimationY.setStartValue(view.y)
            flingAnimationY.setStartVelocity(velocityY)
            flingAnimationY.start()

            result = true
        }

        if (abs(velocityX) >= this.swipeMinVelocity && xDistance > this.swipeMinDistance && yDistance < xDistance) {//Horizontal
            if (downMotionEvent.x > moveMotionEvent.x) {//Right -> Left
                this.gestureListener.onSwipe(SWIPE_LEFT)
                swipeMode = SwipeGestureFilterRandomCenter.SWIPE_LEFT


            } else {//Left -> Right
                this.gestureListener.onSwipe(SWIPE_RIGHT)
                swipeMode = SwipeGestureFilterRandomCenter.SWIPE_RIGHT


            }

            flingAnimationX.setStartValue(view.x)
            flingAnimationX.setStartVelocity(velocityX)
            flingAnimationX.start()

            result = true
        }
        return result
    }

    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {
        this.gestureListener.onSingleTapUp()

        return false
    }



    fun onTouchEvent(motionEvent: MotionEvent) {
        if (!this.running) {
            return
        }

        val TENSION = 800.0
        val DAMPER = 20.0 //friction

        val springSystem = SpringSystem.create()
        val spring = springSystem.createSpring()

        spring.addListener(object : SimpleSpringListener(/*spring*/) {
            override fun onSpringUpdate(spring: Spring?) {
                val value = spring!!.currentValue.toFloat()
                val scale = 1f - (value * 0.5f)
                view.scaleX = scale
                view.scaleY = scale
            }

            override fun onSpringEndStateChange(spring: Spring?) {

            }

            override fun onSpringAtRest(spring: Spring?) {

            }

            override fun onSpringActivate(spring: Spring?) {
                val listTOfRandom = ArrayList<Int>()
                listTOfRandom.addAll(2..9)

                if (!isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.TOP_VALUE.value!!)
                    && !isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.LEFT_VALUE.value!!)
                    && !isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.RIGHT_VALUE.value!!)) {

                    val randomCenterValue: Int = listTOfRandom.random()
                    view.text = "${randomCenterValue}"
                    GameVariables.CENTER_VALUE.value = randomCenterValue
                } else {
                    val snackbar: Snackbar = Snackbar.make(
                        view,
                        Html.fromHtml(context.getString(R.string.thinkMore), Html.FROM_HTML_MODE_LEGACY),
                        Snackbar.LENGTH_INDEFINITE)
                    if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                        snackbar.setActionTextColor(context.getColor(R.color.default_color_game))
                        snackbar.setTextColor(ColorStateList.valueOf(context.getColor(R.color.light)))
                        snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
                        snackbar.setAction(android.R.string.ok, object : View.OnClickListener {
                            override fun onClick(view: View?) {


                            }
                        })
                        val gradientDrawable = GradientDrawable(
                            GradientDrawable.Orientation.BOTTOM_TOP,
                            intArrayOf(
                                context.getColor(R.color.dark_transparent),
                                context.getColor(R.color.darker),
                                context.getColor(R.color.dark_transparent)
                            )
                        )

                        val view = snackbar.view
                        view.background = gradientDrawable

                        val snackButton: Button = snackbar.getView().findViewById<Button>(R.id.snackbar_action)
                        snackButton.setBackgroundColor(Color.TRANSPARENT)

                        snackbar.show()
                    } else {
                        if (snackbar.isShown) {
                            snackbar.dismiss()
                        }
                    }
                }
            }

        })
        val springConfig = SpringConfig(TENSION, DAMPER)
        spring.springConfig = springConfig

        val result = this.gestureDetector.onTouchEvent(motionEvent)
        if (this.mode == MODE_SOLID) {
            motionEvent.action = MotionEvent.ACTION_CANCEL
        } else if (this.mode == MODE_DYNAMIC) {
            if (motionEvent.action == ACTION_FAKE) {
                motionEvent.action = MotionEvent.ACTION_UP
            } else if (result) {
                motionEvent.action = MotionEvent.ACTION_CANCEL
            } else if (this.tapIndicator) {
                motionEvent.action = MotionEvent.ACTION_DOWN

                this.tapIndicator = false
            }
        }

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                spring.endValue = (0.7)

            }
            MotionEvent.ACTION_UP -> {
                spring.endValue = (0.3)

            }
        }
        //Else -> Just Do Nothing | MODE_TRANSPARENT
    }

    fun setEnabled(status: Boolean) {
        this.running = status
    }

    fun swipeMode(): Int {

        return swipeMode
    }

    companion object {
        const val SWIPE_UP = 1
        const val SWIPE_DOWN = 2
        const val SWIPE_LEFT = 3
        const val SWIPE_RIGHT = 4

        const val MODE_TRANSPARENT = 0
        const val MODE_SOLID = 1
        const val MODE_DYNAMIC = 2

        private val ACTION_FAKE = -666
    }
}
