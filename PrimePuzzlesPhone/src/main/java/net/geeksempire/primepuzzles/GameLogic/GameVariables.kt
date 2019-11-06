package net.geeksempire.primepuzzles.GameLogic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameVariables : ViewModel(){

    companion object {
        val CENTER_VALUE: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>()
        }

        val TOP_VALUE: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>()
        }

        val LEFT_VALUE: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>()
        }

        val RIGHT_VALUE: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>()
        }

        val TOGGLE_SNACKBAR: MutableLiveData<Boolean> by lazy {
            MutableLiveData<Boolean>()
        }

        val PRIME_NUMBER_DETECTED: MutableLiveData<Boolean> by lazy {
            MutableLiveData<Boolean>()
        }
    }
}