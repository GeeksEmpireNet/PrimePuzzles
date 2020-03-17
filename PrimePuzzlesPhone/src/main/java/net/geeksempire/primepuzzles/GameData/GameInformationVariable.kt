/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/17/20 2:03 PM
 * Last modified 3/17/20 1:47 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameData

class GameInformationVariable {

    companion object {
        var SNACKBAR_HINT_INFORMATION_TEXT: String = ""
        var SNACKBAR_HINT_BUTTON_TEXT: String = ""

        const val HINT_ACTION: String = "HINT_ACTION"
        const val PRIME_NUMBER_ACTION: String = "PRIME_NUMBER_ACTION"

        const val LEVELS_PREFERENCE: String = "Levels"
        const val SOUNDS_PREFERENCE: String = "Sounds"
        const val POINTS_PREFERENCE: String = "Points"

        const val POINTS_TOTAL_PREFERENCE: String = "TotalPoints"
        const val POINTS_TOTAL_POSITIVE_PREFERENCE: String = "TotalPositivePoints"
        const val POINTS_TOTAL_NEGATIVE_PREFERENCE: String = "TotalNegativePoints"

        const val POINTS_TOTAL_POSITIVE_DIVISIBLE_PREFERENCE: String = "DivisiblePositivePoints"
        const val POINTS_TOTAL_POSITIVE_PRIME_PREFERENCE: String = "PrimePositivePoints"
        const val POINTS_TOTAL_POSITIVE_CHANGE_CENTER_PREFERENCE: String = "ChangeCenterPositivePoints"

        const val POINTS_TOTAL_NEGATIVE_DIVISIBLE_PREFERENCE: String = "DivisibleNegativePoints"
        const val POINTS_TOTAL_NEGATIVE_PRIME_PREFERENCE: String = "PrimeNegativePoints"
        const val POINTS_TOTAL_NEGATIVE_CHANGE_CENTER_PREFERENCE: String = "ChangeCenterNegativePoints"

        var snackBarAction: String = GameInformationVariable.HINT_ACTION
    }
}