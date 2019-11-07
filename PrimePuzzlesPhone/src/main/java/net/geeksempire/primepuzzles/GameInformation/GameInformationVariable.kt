package net.geeksempire.primepuzzles.GameInformation

class GameInformationVariable {

    companion object {
        var SNACKBAR_HINT_INFORMATION_TEXT: String = ""
        var SNACKBAR_HINT_BUTTON_TEXT: String = ""

        const val HINT_ACTION: String = "HINT_ACTION"
        const val PRIME_NUMBER_ACTION: String = "PRIME_NUMBER_ACTION"

        const val POINTS_PREFERENCE: String = "Points"

        const val POINTS_TOTAL_PREFERENCE: String = "TotalPoints"
        const val POINTS_TOTAL_POSITIVE_PREFERENCE: String = "TotalPositivePoints"
        const val POINTS_TOTAL_NEGATIVE_PREFERENCE: String = "TotalNegativePoints"

        var snackBarAction: String = GameInformationVariable.HINT_ACTION
    }
}