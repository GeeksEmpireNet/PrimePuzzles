/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/22/20 2:45 PM
 * Last modified 3/22/20 2:07 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameLogic.LevelsConfiguration

class GameLevel {

    companion object {
        var GAME_DIFFICULTY_LEVEL = 1

        const val GAME_DIFFICULTY_LEVEL_ONE_DIGIT = 1
        const val GAME_DIFFICULTY_LEVEL_TWO_DIGIT = 2
        const val GAME_DIFFICULTY_LEVEL_THREE_DIGIT = 3
        const val GAME_DIFFICULTY_LEVEL_FOUR_DIGIT = 4
    }

    sealed class Levels {
        class Sections(var levelNumber: Int) : Levels()
    }

    fun getGameDifficultyLevel() : Int {

        return GAME_DIFFICULTY_LEVEL
    }

    fun getPointMultiplier() : Levels.Sections {

        return Levels.Sections(getGameDifficultyLevel())
    }
}