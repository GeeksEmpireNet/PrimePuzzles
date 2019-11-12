package net.geeksempire.primepuzzles

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.storage.FirebaseStorage
import net.geeksempire.primepuzzles.GameLogic.GameLevel
import net.geeksempire.primepuzzles.GameLogic.GameSettings
import net.geeksempire.primepuzzles.GamePlay.GamePlay
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGameIO
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassSystem
import java.io.File

class GameConfigurations : Activity() {

    lateinit var functionsClassSystem: FunctionsClassSystem
    lateinit var functionsClassGameIO: FunctionsClassGameIO

    lateinit var firebaseAuth: FirebaseAuth

    val SIGN_IN_REQUEST: Int = 666

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        setContentView(R.layout.configuration_view)
        FirebaseApp.initializeApp(applicationContext)

        functionsClassSystem = FunctionsClassSystem(applicationContext)
        functionsClassGameIO = FunctionsClassGameIO(applicationContext)

        firebaseAuth = FirebaseAuth.getInstance()

        GameLevel.GAME_DIFFICULTY_LEVEL = functionsClassGameIO.readLevelProcess()
        GamePlay.RestoreGameState = if (BuildConfig.DEBUG) { true } else { false }

        if (functionsClassSystem.networkConnection()) {
            if (firebaseAuth.currentUser == null) {
                firebaseAuth.addAuthStateListener { firebaseAuth ->
                    if (firebaseAuth.currentUser == null) {
                        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.webClientId))
                            .requestEmail()
                            .build()

                        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
                        try {
                            googleSignInClient.signOut()
                            googleSignInClient.revokeAccess()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        val signInIntent = googleSignInClient.signInIntent
                        startActivityForResult(signInIntent, SIGN_IN_REQUEST)
                    } else {

                    }
                }
            } else {
                Handler().postDelayed({
                    startActivity(Intent(applicationContext, GamePlay::class.java).apply {
                        putExtra(GameSettings.RESTORE_GAME_STATE, GamePlay.RestoreGameState)
                        putExtra("StatusBarHeight", functionsClassSystem.getStatusBarHeight(applicationContext))
                    }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())
                }, 321)
            }
        } else {

            Handler().postDelayed({
                startActivity(Intent(applicationContext, GamePlay::class.java).apply {
                    putExtra(GameSettings.RESTORE_GAME_STATE, GamePlay.RestoreGameState)
                    putExtra("StatusBarHeight", functionsClassSystem.getStatusBarHeight(applicationContext))
                }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())
            }, 321)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            SIGN_IN_REQUEST -> {
                val taskGoogleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val googleSignInAccount = taskGoogleSignInAccount.getResult(ApiException::class.java)
                    val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount!!.idToken, null)
                    firebaseAuth.signInWithCredential(authCredential).addOnSuccessListener {
                        FunctionsClassDebug.PrintDebug("Sign In Completed ::: ${firebaseAuth.currentUser!!.displayName}")

                        if (firebaseAuth.currentUser != null) {
                            if (functionsClassSystem.networkConnection()) {
                                if (!functionsClassGameIO.primeNumbersJsonExists()) {
                                    val firebaseStorage = FirebaseStorage.getInstance()
                                    val firebaseStorageReference = firebaseStorage.reference

                                    firebaseStorageReference
                                        .child("Assets")
                                        .child("PrimeNumbersResources")
                                        .child("PrimeNumbers.json").getFile(File("/data/data/" + packageName + "/files/" + "PrimeNumbers.json"))
                                        .addOnProgressListener { fileDownloadTaskTaskSnapshot ->
                                            FunctionsClassDebug.PrintDebug("Total Bytes ::: ${fileDownloadTaskTaskSnapshot.totalByteCount} | Transferred Bytes ::: ${fileDownloadTaskTaskSnapshot.bytesTransferred}")

                                        }.addOnFailureListener {

                                        }.addOnCompleteListener {

                                            startActivity(Intent(applicationContext, GamePlay::class.java).apply {
                                                putExtra(GameSettings.RESTORE_GAME_STATE, GamePlay.RestoreGameState)
                                                putExtra("StatusBarHeight", functionsClassSystem.getStatusBarHeight(applicationContext))
                                            }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())
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
