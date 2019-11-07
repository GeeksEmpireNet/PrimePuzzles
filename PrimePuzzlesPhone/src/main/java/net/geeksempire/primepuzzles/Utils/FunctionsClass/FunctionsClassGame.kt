package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler


class FunctionsClassGame(initContext: Context) {

    val context: Context = initContext

    val functionsClassGameIO: FunctionsClassGameIO = FunctionsClassGameIO(context)

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