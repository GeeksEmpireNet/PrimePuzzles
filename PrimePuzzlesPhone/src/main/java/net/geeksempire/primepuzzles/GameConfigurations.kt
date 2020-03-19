/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/19/20 2:01 PM
 * Last modified 3/19/20 1:26 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */
package net.geeksempire.primepuzzles

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PlayGamesAuthProvider
import com.google.firebase.storage.FirebaseStorage
import net.geeksempire.primepuzzles.GameLogic.GameLevel
import net.geeksempire.primepuzzles.GameLogic.GameSettings
import net.geeksempire.primepuzzles.GamePlay.GamePlay
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGameIO
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassSystem
import net.geeksempire.primepuzzles.databinding.ConfigurationViewBinding

class GameConfigurations : Activity() {

    lateinit var functionsClassSystem: FunctionsClassSystem
    lateinit var functionsClassGameIO: FunctionsClassGameIO

    lateinit var firebaseAuth: FirebaseAuth

    private val SIGN_IN_REQUEST: Int = 666

    private lateinit var configurationViewBinding: ConfigurationViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configurationViewBinding = ConfigurationViewBinding.inflate(layoutInflater)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(configurationViewBinding.root)
        FirebaseApp.initializeApp(applicationContext)

        functionsClassSystem = FunctionsClassSystem(applicationContext)
        functionsClassGameIO = FunctionsClassGameIO(applicationContext)

        firebaseAuth = FirebaseAuth.getInstance()

        GameLevel.GAME_DIFFICULTY_LEVEL = functionsClassGameIO.readLevelProcess()
        GamePlay.RestoreGameState = BuildConfig.DEBUG

        if (functionsClassSystem.networkConnection()) {
            if (firebaseAuth.currentUser == null) {
                configurationViewBinding.signInWaiting.visibility = View.VISIBLE

                firebaseAuth.addAuthStateListener { firebaseAuth ->

                    if (firebaseAuth.currentUser == null) {
                        val googleSignInOptions =
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                                .requestServerAuthCode(getString(R.string.default_web_client_id))
                                .requestIdToken(getString(R.string.webClientId))
                                .requestProfile()
                                .requestEmail()
                                .build()

                        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
                        googleSignInClient.signOut().addOnCompleteListener {
                            googleSignInClient.revokeAccess().addOnCompleteListener {

                                val signInIntent = googleSignInClient.signInIntent
                                startActivityForResult(signInIntent, SIGN_IN_REQUEST)
                            }
                        }
                    } else {

                    }
                }
            } else {
                configurationViewBinding.signInWaiting.visibility = View.GONE

                startActivity(
                    Intent(applicationContext, GamePlay::class.java).apply {
                        putExtra(GameSettings.RESTORE_GAME_STATE, GamePlay.RestoreGameState)
                        putExtra(
                            "StatusBarHeight",
                            functionsClassSystem.getStatusBarHeight(applicationContext)
                        )
                    },
                    ActivityOptions.makeCustomAnimation(
                        applicationContext,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    ).toBundle()
                )

                this@GameConfigurations.finish()
            }
        } else {
            configurationViewBinding.signInWaiting.visibility = View.GONE

            startActivity(
                Intent(applicationContext, GamePlay::class.java).apply {
                    putExtra(GameSettings.RESTORE_GAME_STATE, GamePlay.RestoreGameState)
                    putExtra(
                        "StatusBarHeight",
                        functionsClassSystem.getStatusBarHeight(applicationContext)
                    )
                },
                ActivityOptions.makeCustomAnimation(
                    applicationContext,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                ).toBundle()
            )

            this@GameConfigurations.finish()
        }
    }

    override fun onBackPressed() {

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            SIGN_IN_REQUEST -> {
                val taskGoogleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val googleSignInAccount =
                        taskGoogleSignInAccount.getResult(ApiException::class.java)
                    val authCredential =
                        PlayGamesAuthProvider.getCredential(googleSignInAccount!!.serverAuthCode!!)
                    firebaseAuth.signInWithCredential(authCredential).addOnSuccessListener {
                        FunctionsClassDebug.PrintDebug("Play Game Sign In Completed ::: ${firebaseAuth.currentUser!!.displayName}")

                        if (firebaseAuth.currentUser != null) {
                            if (functionsClassSystem.networkConnection()) {
                                val authCredentialGoogle = GoogleAuthProvider.getCredential(
                                    googleSignInAccount.idToken!!,
                                    null
                                )
                                firebaseAuth.currentUser!!.linkWithCredential(authCredentialGoogle)
                                    .addOnSuccessListener {

                                    }.addOnFailureListener {

                                }

                                if (!functionsClassGameIO.primeNumbersJsonExists()) {
                                    val firebaseStorage = FirebaseStorage.getInstance()
                                    val firebaseStorageReference = firebaseStorage.reference

                                    firebaseStorageReference
                                        .child("Assets")
                                        .child("PrimeNumbersResources")
                                        .child("PrimeNumbers.json")
                                        .getFile(getFileStreamPath("PrimeNumbers.json"))
                                        .addOnFailureListener {

                                        }.addOnCompleteListener {

                                        }.addOnSuccessListener {

                                            startActivity(
                                                Intent(
                                                    applicationContext,
                                                    GamePlay::class.java
                                                ).apply {
                                                    putExtra(
                                                        GameSettings.RESTORE_GAME_STATE,
                                                        GamePlay.RestoreGameState
                                                    )
                                                    putExtra(
                                                        "StatusBarHeight",
                                                        functionsClassSystem.getStatusBarHeight(
                                                            applicationContext
                                                        )
                                                    )
                                                },
                                                ActivityOptions.makeCustomAnimation(
                                                    applicationContext,
                                                    android.R.anim.fade_in,
                                                    android.R.anim.fade_out
                                                ).toBundle()
                                            )

                                            this@GameConfigurations.finish()

                                        }.addOnProgressListener { fileDownloadTaskTaskSnapshot ->
                                            FunctionsClassDebug.PrintDebug("Total Bytes ::: ${fileDownloadTaskTaskSnapshot.totalByteCount} | Transferred Bytes ::: ${fileDownloadTaskTaskSnapshot.bytesTransferred}")
                                        }
                                }
                            }
                        }
                    }.addOnFailureListener {

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
