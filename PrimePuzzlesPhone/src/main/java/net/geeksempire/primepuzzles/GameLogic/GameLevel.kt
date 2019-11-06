package net.geeksempire.primepuzzles.GameLogic

class GameLevel {

    companion object {
        const val GAME_DIFFICULTY_LEVEL_ONE_DIGIT = 1
        const val GAME_DIFFICULTY_LEVEL_TWO_DIGIT = 2
        const val GAME_DIFFICULTY_LEVEL_THREE_DIGIT = 3
        const val GAME_DIFFICULTY_LEVEL_FOUR_DIGIT = 4
    }

    fun getGameDifficultyLevel() : Int {
        var gameDifficultyLevel = 0

        /**/
        gameDifficultyLevel = 2
        /**/

        return gameDifficultyLevel
    }
}