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
    }


}