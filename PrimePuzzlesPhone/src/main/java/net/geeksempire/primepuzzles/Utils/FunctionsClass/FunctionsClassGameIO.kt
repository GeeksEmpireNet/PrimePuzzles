/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/17/20 11:24 AM
 * Last modified 3/17/20 11:22 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import net.geeksempire.primepuzzles.GameInformation.GameInformationVariable

class FunctionsClassGameIO(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(GameInformationVariable.POINTS_PREFERENCE, Context.MODE_PRIVATE)

    fun primeNumbersJsonExists() : Boolean {

        return context.getFileStreamPath("PrimeNumbers.json").exists()
    }

    fun saveTotalPoints(PointValue: Int) {
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }
    fun readTotalPoints() : Int {
        return sharedPreferences
            .getInt(GameInformationVariable.POINTS_TOTAL_PREFERENCE, 0)
    }


    fun saveDivisiblePositivePoints(PointValue: Int) {
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_DIVISIBLE_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }
    fun readDivisiblePositivePoints() : Int {
        return sharedPreferences
            .getInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_DIVISIBLE_PREFERENCE, 0)
    }


    fun savePrimePositivePoints(PointValue: Int) {
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_PRIME_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }
    fun readPrimePositivePoints() : Int {
        return sharedPreferences
            .getInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_PRIME_PREFERENCE, 0)
    }


    fun saveCenterChangePositivePoints(PointValue: Int) {
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_CHANGE_CENTER_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }
    fun readCenterChangePositivePoints() : Int {
        return sharedPreferences
            .getInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_CHANGE_CENTER_PREFERENCE, 0)
    }


    fun saveDivisibleNegativePoints(PointValue: Int) {
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_DIVISIBLE_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }
    fun readDivisibleNegativePoints() : Int {
        return sharedPreferences
            .getInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_DIVISIBLE_PREFERENCE, 0)
    }


    fun savePrimeNegativePoints(PointValue: Int) {
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_PRIME_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }
    fun readPrimeNegativePoints() : Int {
        return sharedPreferences
            .getInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_PRIME_PREFERENCE, 0)
    }


    fun saveCenterChangeNegativePoints(PointValue: Int) {
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_CHANGE_CENTER_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }
    fun readCenterChangeNegativePoints() : Int {
        return sharedPreferences
            .getInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_CHANGE_CENTER_PREFERENCE, 0)
    }


    fun savePositivePoints(PointValue: Int) {
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }
    fun readTotalPositivePoints() : Int {
        return sharedPreferences
            .getInt(GameInformationVariable.POINTS_TOTAL_POSITIVE_PREFERENCE, 0)
    }


    fun saveNegativePoints(PointValue: Int) {
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }
    fun readTotalNegativePoints() : Int {
        return sharedPreferences
            .getInt(GameInformationVariable.POINTS_TOTAL_NEGATIVE_PREFERENCE, 0)
    }


    fun saveLevelProcess(PointValue: Int) {
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putInt(GameInformationVariable.LEVELS_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }
    fun readLevelProcess() : Int {
        return sharedPreferences
            .getInt(GameInformationVariable.LEVELS_PREFERENCE, 1)
    }


    fun savePlaySounds(PointValue: Boolean) {
        val editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.putBoolean(GameInformationVariable.SOUNDS_PREFERENCE, PointValue)
        editorSharedPreferences.apply()
    }
    fun readPlaySounds() : Boolean {
        return sharedPreferences
            .getBoolean(GameInformationVariable.SOUNDS_PREFERENCE, false)
    }
}