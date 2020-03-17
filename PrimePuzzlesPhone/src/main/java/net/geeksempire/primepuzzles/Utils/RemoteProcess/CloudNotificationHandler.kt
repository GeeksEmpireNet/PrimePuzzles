/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/17/20 11:24 AM
 * Last modified 3/17/20 11:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.Utils.RemoteProcess

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CloudNotificationHandler : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }
}
