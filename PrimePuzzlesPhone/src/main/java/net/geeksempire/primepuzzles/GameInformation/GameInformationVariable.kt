package net.geeksempire.primepuzzles.GameInformation

class GameInformationVariable {

    companion object {
        var SNACKBAR_HINT_INFORMATION_TEXT: String? = ""
        var SNACKBAR_HINT_BUTTON_TEXT: String? = ""

        const val HINT_ACTION = "HINT_ACTION"
        const val PRIME_NUMBER_ACTION = "PRIME_NUMBER_ACTION"

        var snackBarAction = GameInformationVariable.HINT_ACTION
    }
}