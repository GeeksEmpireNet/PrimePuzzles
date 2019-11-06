package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler


class FunctionsClassGame(initContext: Context) {

    val context: Context = initContext

    fun playWrongSound() {
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