package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import net.geeksempire.primepuzzles.GameInformation.GameInformationVariable

class FunctionsClassGameIO(initContext: Context) {

    val context: Context = initContext

    fun saveTotalPoints(PointValue: Int) {
        val sharedPreferences = context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }

    fun savePositivePoints(PointValue: Int) {
        val sharedPreferences = context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }

    fun saveNegativePoints(PointValue: Int) {
        val sharedPreferences = context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }

    fun readTotalPoints() : Int {
        return context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(GameInformationVariable.POINTS_TOTAL_PREFERENCE, 0)
    }

    fun readTotalPositivePoints() : Int {
        return context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_PREFERENCE, 0)
    }

    fun readTotalNegativePoints() : Int {
        return context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_PREFERENCE, 0)
    }
}