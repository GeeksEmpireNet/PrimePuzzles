/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/18/20 5:23 PM
 * Last modified 3/18/20 3:49 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GamePlay.Ads

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import net.geeksempire.primepuzzles.BuildConfig
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug

class AdsLoading (private val context: Context) {

    fun initializeAds (adViewBannerGamePlay: AdView) {
        MobileAds.initialize(context) { initializationStatus ->

            if (!BuildConfig.DEBUG) {
                setUpAds(adViewBannerGamePlay)
            }
        }
    }

    /*
       *
       * Ads Functions
       *
       */
    private fun setUpAds (adViewBannerGamePlay: AdView) {

        val adRequest = AdRequest.Builder()
            .addKeyword("Game")
            .build()

        /*Banner Ads*/
        adViewBannerGamePlay.loadAd(adRequest)
        adViewBannerGamePlay.adListener = object : AdListener() {
            override fun onAdLoaded() {
                FunctionsClassDebug.PrintDebug("Banner Ads Loaded")

            }

            override fun onAdFailedToLoad(errorCode: Int) {
                FunctionsClassDebug.PrintDebug("Banner Ads Failed")

            }

            override fun onAdOpened() {
                FunctionsClassDebug.PrintDebug("Banner Ads Opened")

            }

            override fun onAdClicked() {
                FunctionsClassDebug.PrintDebug("Banner Ads Clicked")

            }

            override fun onAdLeftApplication() {

            }

            override fun onAdClosed() {

            }
        }


        /*Interstitial Ads*/


        /*Rewarded Ads*/
    }
}