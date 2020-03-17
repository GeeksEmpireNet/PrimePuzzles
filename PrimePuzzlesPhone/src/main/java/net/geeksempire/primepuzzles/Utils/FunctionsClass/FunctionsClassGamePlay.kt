/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/17/20 2:03 PM
 * Last modified 3/17/20 12:52 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.games.Games
import net.geeksempire.primepuzzles.R

class FunctionsClassGamePlay(private val activity: AppCompatActivity, private val context: Context) {

    fun setNewHighScore(newHighScore: Long) {
        Games.getLeaderboardsClient(activity, GoogleSignIn.getLastSignedInAccount(context)!!)
            .submitScore(context.getString(R.string.leaderBoardBrainiacs), newHighScore)
    }
}