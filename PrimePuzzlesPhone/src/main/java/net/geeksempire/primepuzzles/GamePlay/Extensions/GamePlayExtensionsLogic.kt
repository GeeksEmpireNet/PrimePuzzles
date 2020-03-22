/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/22/20 3:41 PM
 * Last modified 3/22/20 3:09 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GamePlay.Extensions

import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import net.geeksempire.primepuzzles.GameData.GameVariablesViewModel
import net.geeksempire.primepuzzles.GameData.ShuffleProcess
import net.geeksempire.primepuzzles.GameLogic.LevelsConfiguration.GameLevel
import net.geeksempire.primepuzzles.GamePlay.GamePlay
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug

fun GamePlay.observeGameLogicVariable() {

    val shuffleProcess: ShuffleProcess = ShuffleProcess(applicationContext)

    GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.observe(this@observeGameLogicVariable,
        Observer<Int> { newDifficultyLevel ->

            when (gameLevel.getGameDifficultyLevel()) {
                GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {//2..9

                    if (newDifficultyLevel!! >= 7) {
                        GameLevel.GAME_DIFFICULTY_LEVEL++

                        if (GameLevel.GAME_DIFFICULTY_LEVEL == functionsClassGameIO.readEndLevel()) {
                            //The End
                        }

                        GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.postValue(0)
                    }
                }
                GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {//10..99

                    if (newDifficultyLevel!! >= 77) {
                        GameLevel.GAME_DIFFICULTY_LEVEL++

                        if (GameLevel.GAME_DIFFICULTY_LEVEL == functionsClassGameIO.readEndLevel()) {
                            //The End
                        }

                        GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.postValue(0)
                    }
                }
                GameLevel.GAME_DIFFICULTY_LEVEL_THREE_DIGIT -> {//100..999

                    if (newDifficultyLevel!! >= 777) {
                        GameLevel.GAME_DIFFICULTY_LEVEL++

                        if (GameLevel.GAME_DIFFICULTY_LEVEL == functionsClassGameIO.readEndLevel()) {
                            //The End
                        }

                        GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.postValue(0)
                    }
                }
                GameLevel.GAME_DIFFICULTY_LEVEL_FOUR_DIGIT -> {//1000..9999

                    if (newDifficultyLevel!! >= 7777) {
                        GameLevel.GAME_DIFFICULTY_LEVEL++

                        if (GameLevel.GAME_DIFFICULTY_LEVEL == functionsClassGameIO.readEndLevel()) {
                            //The End
                        }

                        GameVariablesViewModel.GAME_LEVEL_DIFFICULTY_COUNTER.postValue(0)
                    }
                }
            }

            functionsClassGameIO.saveLevelProcess(gameLevel.getGameDifficultyLevel())
        })


    GameVariablesViewModel.PRIME_NUMBER_DETECTED.observe(this@observeGameLogicVariable,
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
    GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.observe(this@observeGameLogicVariable,
        Observer<Int> { newShufflePosition ->
            if (newShufflePosition!! >= 7) {
                shuffleProcess.shuffleProcessPosition(gamePlayViewBinding)
            }
        })

    GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.value = 0
    GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.observe(this@observeGameLogicVariable,
        Observer<Int> { newShuffleValue ->
            if (newShuffleValue!! >= 21) {
                shuffleProcess.shuffleProcessValue(gamePlayViewBinding)
            }
        })


    GameVariablesViewModel.CENTER_VALUE.observe(this@observeGameLogicVariable,
        Observer<Int> { newCenterValue ->
            gamePlayViewBinding.gamePlayControlViewInclude.gesturedRandomCenterView.text = "${newCenterValue}"
        })

    GameVariablesViewModel.TOP_VALUE.observe(this@observeGameLogicVariable,
        Observer<Int> { newTopValue ->
            gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setText("${newTopValue}")
        })
    GameVariablesViewModel.LEFT_VALUE.observe(this@observeGameLogicVariable,
        Observer<Int> { newLeftValue ->
            gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setText("${newLeftValue}")
        })
    GameVariablesViewModel.RIGHT_VALUE.observe(this@observeGameLogicVariable,
        Observer<Int> { newRightValue ->
            gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setText("${newRightValue}")
        })
}

/**
 *
 * Points Functions
 *
 */
fun GamePlay.scanPointsChange() {
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

//    GameVariablesViewModel.POSITIVE_POINT.value = 0
//    GameVariablesViewModel.DIVISIBLE_POSITIVE_POINT.value = 0
//    GameVariablesViewModel.PRIME_POSITIVE_POINT.value = 0
//    GameVariablesViewModel.CHANGE_CENTER_RANDOM_POSITIVE_POINT.value = 0
//
//    GameVariablesViewModel.NEGATIVE_POINT.value = 0
//    GameVariablesViewModel.DIVISIBLE_NEGATIVE_POINT.value = 0
//    GameVariablesViewModel.PRIME_NEGATIVE_POINT.value = 0
//    GameVariablesViewModel.CHANGE_CENTER_RANDOM_NEGATIVE_POINT.value = 0

    /*
     * Positive Points
     */
    GameVariablesViewModel.POSITIVE_POINT.observe(this@scanPointsChange,
        Observer<Int> { newPositivePoint ->
            if (newPositivePoint > 0) {

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

                val totalSavePoint: Int = functionsClassGameIO.readTotalPoints()
                val totalNewPoint: Int =
                    totalSavePoint + (newPositivePoint!! * gameLevel.getPointMultiplier().levelNumber)

                functionsClassGameIO.saveTotalPoints(totalNewPoint)
                gamePlayViewBinding.gamePlayInformationViewInclude.pointsTotalView.setText("${totalNewPoint}")

                FunctionsClassDebug.PrintDebug("POSITIVE_POINT | ${totalNewPoint} ::: ${newPositivePoint}")
            }
        })

    GameVariablesViewModel.DIVISIBLE_POSITIVE_POINT.observe(this@scanPointsChange,
        Observer<Int> { newPositivePoint ->
            FunctionsClassDebug.PrintDebug("DIVISIBLE_POSITIVE_POINT ::: ${newPositivePoint}")

            val point = functionsClassGameIO.readDivisiblePositivePoints()
            val newPoint = point + newPositivePoint!!
            functionsClassGameIO.saveDivisiblePositivePoints(newPoint)
        })

    GameVariablesViewModel.PRIME_POSITIVE_POINT.observe(this@scanPointsChange,
        Observer<Int> { newPositivePoint ->
            FunctionsClassDebug.PrintDebug("PRIME_POSITIVE_POINT ::: ${newPositivePoint}")

            val point = functionsClassGameIO.readPrimePositivePoints()
            val newPoint = point + newPositivePoint!!
            functionsClassGameIO.savePrimePositivePoints(newPoint)
        })

    GameVariablesViewModel.CHANGE_CENTER_RANDOM_POSITIVE_POINT.observe(this@scanPointsChange,
        Observer<Int> { newPositivePoint ->
            FunctionsClassDebug.PrintDebug("CHANGE_CENTER_RANDOM_POSITIVE_POINT ::: ${newPositivePoint}")

            val point = functionsClassGameIO.readCenterChangePositivePoints()
            val newPoint = point + newPositivePoint!!
            functionsClassGameIO.saveCenterChangePositivePoints(newPoint)
        })

    /*
     * Negative Points
     */
    GameVariablesViewModel.NEGATIVE_POINT.observe(this@scanPointsChange,
        Observer<Int> { newNegativePoint ->
            if (newNegativePoint > 0) {

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

                val totalSavePoint: Int = functionsClassGameIO.readTotalPoints()
                val totalNewPoint: Int =
                    totalSavePoint - (newNegativePoint!! * gameLevel.getPointMultiplier().levelNumber)

                functionsClassGameIO.saveTotalPoints(totalNewPoint)
                gamePlayViewBinding.gamePlayInformationViewInclude.pointsTotalView.setText("${totalNewPoint}")

                FunctionsClassDebug.PrintDebug("NEGATIVE_POINT | ${totalNewPoint} ::: ${newNegativePoint}")
            }
        })

    GameVariablesViewModel.DIVISIBLE_NEGATIVE_POINT.observe(this@scanPointsChange,
        Observer<Int> { newNegativePoint ->
            FunctionsClassDebug.PrintDebug("DIVISIBLE_NEGATIVE_POINT ::: ${newNegativePoint}")

            val point = functionsClassGameIO.readDivisibleNegativePoints()
            val newPoint = point - newNegativePoint!!
            functionsClassGameIO.saveDivisibleNegativePoints(newPoint)
        })

    GameVariablesViewModel.PRIME_NEGATIVE_POINT.observe(this@scanPointsChange,
        Observer<Int> { newNegativePoint ->
            FunctionsClassDebug.PrintDebug("PRIME_NEGATIVE_POINT ::: ${newNegativePoint}")

            val point = functionsClassGameIO.readPrimeNegativePoints()
            val newPoint = point - newNegativePoint!!
            functionsClassGameIO.savePrimeNegativePoints(newPoint)
        })

    GameVariablesViewModel.CHANGE_CENTER_RANDOM_NEGATIVE_POINT.observe(this@scanPointsChange,
        Observer<Int> { newNegativePoint ->
            FunctionsClassDebug.PrintDebug("CHANGE_CENTER_RANDOM_NEGATIVE_POINT ::: ${newNegativePoint}")

            val point = functionsClassGameIO.readCenterChangeNegativePoints()
            val newPoint = point - newNegativePoint!!
            functionsClassGameIO.saveCenterChangeNegativePoints(newPoint)
        })
}

fun GamePlay.setupInitialNumber() {
    val listOfDivisible = ArrayList<Int>()
    listOfDivisible.addAll(2..9)

    val topValueRandom = listOfDivisible.random()
    GameVariablesViewModel.TOP_VALUE.value = topValueRandom
    gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setText("${topValueRandom}")
    listOfDivisible.remove(topValueRandom)

    val leftValueRandom = listOfDivisible.random()
    GameVariablesViewModel.LEFT_VALUE.value = leftValueRandom
    gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setText("${leftValueRandom}")
    listOfDivisible.remove(leftValueRandom)

    val rightValueRandom = listOfDivisible.random()
    GameVariablesViewModel.RIGHT_VALUE.value = rightValueRandom
    gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setText("${rightValueRandom}")
    listOfDivisible.remove(rightValueRandom)
}