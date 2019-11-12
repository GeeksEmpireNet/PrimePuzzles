/*
 * Copyright (c) 2019.  All Rights Reserved for Geeks Empire.
 * Created by Elias Fazel on 11/11/19 6:09 PM
 * Last modified 11/11/19 6:08 PM
 */

package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import net.geeksempire.primepuzzles.GameInformation.GameInformationVariable

class FunctionsClassGameIO(initContext: Context) {

    val context: Context = initContext


    fun primeNumbersJsonExists() : Boolean {

        return context.getFileStreamPath("PrimeNumbers.json").exists()
    }


    fun saveTotalPoints(PointValue: Int) {
        val sharedPreferences = context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }

    fun readTotalPoints() : Int {
        return context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(GameInformationVariable.POINTS_TOTAL_PREFERENCE, 0)
    }


    fun savePositivePoints(PointValue: Int) {
        val sharedPreferences = context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }

    fun readTotalPositivePoints() : Int {
        return context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_PREFERENCE, 0)
    }


    fun saveNegativePoints(PointValue: Int) {
        val sharedPreferences = context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }

    fun readTotalNegativePoints() : Int {
        return context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_PREFERENCE, 0)
    }


    fun saveLevelProcess(PointValue: Int) {
        val sharedPreferences = context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.LEVELS_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }

    fun readLevelProcess() : Int {
        return context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(GameInformationVariable.LEVELS_PREFERENCE, 1)
    }


    fun savePlaySounds(PointValue: Boolean) {
        val sharedPreferences = context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putBoolean(GameInformationVariable.SOUNDS_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }

    fun readPlaySounds() : Boolean {
        return context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)
            .getBoolean(GameInformationVariable.SOUNDS_PREFERENCE, false)
    }
}