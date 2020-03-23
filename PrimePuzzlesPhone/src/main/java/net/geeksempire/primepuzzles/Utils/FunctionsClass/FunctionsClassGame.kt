/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/23/20 3:11 PM
 * Last modified 3/23/20 2:50 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.animation.ValueAnimator
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import net.geeksempire.ProgressBar.HorizontalProgressView
import net.geeksempire.primepuzzles.GameData.GameVariablesViewModel
import net.geeksempire.primepuzzles.GamePlay.GamePlay
import net.geeksempire.primepuzzles.GamePlay.Utils.CountDownTimer

class FunctionsClassGame(private val context: Context) {

    private val functionsClassGameIO: FunctionsClassGameIO = FunctionsClassGameIO(context)

    /**
     * Timer Functions
     */
    fun countDownTimer(gamePlay: GamePlay, timerProgressBar: HorizontalProgressView)
            : CountDownTimer {

        gamePlay.valueAnimatorProgressBar = ValueAnimator.ofFloat(0F, 100F)
        gamePlay.valueAnimatorProgressBar.duration = GamePlay.countDownTimerDuration
        gamePlay.valueAnimatorProgressBar.addUpdateListener { animator ->

            timerProgressBar.progress = (animator.animatedValue as Float)
        }

        return object : CountDownTimer(GamePlay.lastThickTimer, 1) {

            override fun onTick(millisUntilFinished: Long) {

                GamePlay.lastThickTimer = millisUntilFinished
            }

            override fun onFinish() {
                //WRONG ANSWER
                FunctionsClassDebug.PrintDebug("WRONG ANSWER")

                playWrongSound()

                GameVariablesViewModel.NEGATIVE_POINT.postValue(3)

                val valueAnimatorProgressBarBack = ValueAnimator.ofFloat(100F, 0F)
                valueAnimatorProgressBarBack.duration = 333
                valueAnimatorProgressBarBack.addUpdateListener { animator ->
                    timerProgressBar.progress = (animator.animatedValue as Float)
                }
                valueAnimatorProgressBarBack.start()
            }
        }
    }

    fun playLongPrimeDetectionSound() {
        if (functionsClassGameIO.readPlaySounds()) {

            Handler().postDelayed({
                var mediaPlayer = MediaPlayer()
                try {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.stop()
                        mediaPlayer.release()

                        mediaPlayer = MediaPlayer()
                    }

                    val assetFileDescriptor = context.assets.openFd("sounds/prime_number_detected_short.mp3")
                    mediaPlayer.setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                    )
                    assetFileDescriptor.close()

                    mediaPlayer.prepare()
                    mediaPlayer.setVolume(1f, 1f)
                    mediaPlayer.isLooping = false
                    mediaPlayer.start()
                    mediaPlayer.setOnCompletionListener {
                        mediaPlayer.stop()
                        mediaPlayer.release()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {

                }
            }, 7)
        }
    }

    fun playShortPrimeDetectionSound() {
        if (functionsClassGameIO.readPlaySounds()) {

            Handler().postDelayed({
                var mediaPlayer = MediaPlayer()
                try {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.stop()
                        mediaPlayer.release()

                        mediaPlayer = MediaPlayer()
                    }

                    val assetFileDescriptor = context.assets.openFd("sounds/prime_number_detected_short.mp3")
                    mediaPlayer.setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                    )
                    assetFileDescriptor.close()

                    mediaPlayer.prepare()
                    mediaPlayer.setVolume(1f, 1f)
                    mediaPlayer.isLooping = false
                    mediaPlayer.start()
                    mediaPlayer.setOnCompletionListener {
                        mediaPlayer.stop()
                        mediaPlayer.release()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {

                }
            }, 77)
        }
    }

    fun playChangedCenterRandomSound() {
        if (functionsClassGameIO.readPlaySounds()) {

            Handler().postDelayed({
                var mediaPlayer = MediaPlayer()
                try {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.stop()
                        mediaPlayer.release()

                        mediaPlayer = MediaPlayer()
                    }

                    val assetFileDescriptor = context.assets.openFd("sounds/center_random_changed_sound.mp3")
                    mediaPlayer.setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                    )
                    assetFileDescriptor.close()

                    mediaPlayer.prepare()
                    mediaPlayer.setVolume(1f, 1f)
                    mediaPlayer.isLooping = false
                    mediaPlayer.start()
                    mediaPlayer.setOnCompletionListener {
                        mediaPlayer.stop()
                        mediaPlayer.release()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {

                }
            }, 77)
        }
    }

    fun playShuffleMagicalNumbersPosition() {
        if (functionsClassGameIO.readPlaySounds()) {

            Handler().postDelayed({
                var mediaPlayer = MediaPlayer()
                try {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.stop()
                        mediaPlayer.release()

                        mediaPlayer = MediaPlayer()
                    }

                    val assetFileDescriptor = context.assets.openFd("sounds/shuffle_numbers_positions.mp3")
                    mediaPlayer.setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                    )
                    assetFileDescriptor.close()

                    mediaPlayer.prepare()
                    mediaPlayer.setVolume(1f, 1f)
                    mediaPlayer.isLooping = false
                    mediaPlayer.start()
                    mediaPlayer.setOnCompletionListener {
                        mediaPlayer.stop()
                        mediaPlayer.release()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {

                }
            }, 77)
        }
    }

    fun playShuffleMagicalNumbersValues() {
        if (functionsClassGameIO.readPlaySounds()) {

            Handler().postDelayed({
                var mediaPlayer = MediaPlayer()
                try {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.stop()
                        mediaPlayer.release()

                        mediaPlayer = MediaPlayer()
                    }

                    val assetFileDescriptor = context.assets.openFd("sounds/shuffle_magical_numbers_values.mp3")
                    mediaPlayer.setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                    )
                    assetFileDescriptor.close()

                    mediaPlayer.prepare()
                    mediaPlayer.setVolume(1f, 1f)
                    mediaPlayer.isLooping = false
                    mediaPlayer.start()
                    mediaPlayer.setOnCompletionListener {
                        mediaPlayer.stop()
                        mediaPlayer.release()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {

                }
            }, 77)
        }
    }

    fun playWrongSound() {
        if (functionsClassGameIO.readPlaySounds()) {

            Handler().postDelayed({
                var mediaPlayer = MediaPlayer()
                try {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.stop()
                        mediaPlayer.release()

                        mediaPlayer = MediaPlayer()
                    }

                    val assetFileDescriptor = context.assets.openFd("sounds/wrong_answer_sound.mp3")
                    mediaPlayer.setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                    )
                    assetFileDescriptor.close()

                    mediaPlayer.prepare()
                    mediaPlayer.setVolume(1f, 1f)
                    mediaPlayer.isLooping = false
                    mediaPlayer.start()
                    mediaPlayer.setOnCompletionListener {
                        mediaPlayer.stop()
                        mediaPlayer.release()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {

                }
            }, 77)
        }
    }
}