package net.geeksempire.primepuzzles.GameLogic

class GameLevel {

    companion object {
        var GAME_DIFFICULTY_LEVEL = 1

        const val GAME_DIFFICULTY_LEVEL_ONE_DIGIT = 1
        const val GAME_DIFFICULTY_LEVEL_TWO_DIGIT = 2
        const val GAME_DIFFICULTY_LEVEL_THREE_DIGIT = 3
        const val GAME_DIFFICULTY_LEVEL_FOUR_DIGIT = 4
    }

    fun getGameDifficultyLevel() : Int {
        var gameDifficultyLevel: Int = GAME_DIFFICULTY_LEVEL


        return gameDifficultyLevel
    }

    fun getPointMultiplier() : Int{
        var pointMultiplier = 1
        when (getGameDifficultyLevel()) {
            GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {
                pointMultiplier = 1
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {
                pointMultiplier = 1
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_THREE_DIGIT-> {
                pointMultiplier = 2
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_FOUR_DIGIT -> {
                pointMultiplier = 3
            }
            else -> {
                pointMultiplier = 1
            }
        }
        return pointMultiplier
    }
}