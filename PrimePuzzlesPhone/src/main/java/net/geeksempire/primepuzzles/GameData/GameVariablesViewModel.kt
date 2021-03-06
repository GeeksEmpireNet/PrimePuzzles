/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/23/20 2:35 PM
 * Last modified 3/23/20 2:28 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject

class GameVariablesViewModel : ViewModel() {

    companion object {
        //
        var GAME_PRIME_NUMBER_DATA: JSONObject? = null

        //
        val GAME_LEVEL_DIFFICULTY: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>()
        }

        val GAME_LEVEL_DIFFICULTY_COUNTER: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>()
        }


        //
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


        //
        val PRIME_NUMBER_DETECTED: MutableLiveData<Boolean> by lazy {
            MutableLiveData<Boolean>()
        }


        //
        val TOGGLE_SNACKBAR: MutableLiveData<Boolean> by lazy {
            MutableLiveData<Boolean>()
        }


        //
        val SHUFFLE_PROCESS_POSITION: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>().also {
                it.postValue(0)
            }
        }

        val SHUFFLE_PROCESS_VALUE: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>().also {
                it.postValue(0)
            }
        }


        //
        val POSITIVE_POINT: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>().also {
                it.postValue(0)
            }
        }

        val DIVISIBLE_POSITIVE_POINT: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>().also {
                it.postValue(0)
            }
        }

        val PRIME_POSITIVE_POINT: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>().also {
                it.postValue(0)
            }
        }

        val CHANGE_CENTER_RANDOM_POSITIVE_POINT: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>().also {
                it.postValue(0)
            }
        }


        //
        val NEGATIVE_POINT: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>().also {
                it.postValue(0)
            }
        }

        val DIVISIBLE_NEGATIVE_POINT: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>().also {
                it.postValue(0)
            }
        }

        val PRIME_NEGATIVE_POINT: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>().also {
                it.postValue(0)
            }
        }

        val CHANGE_CENTER_RANDOM_NEGATIVE_POINT: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>().also {
                it.postValue(0)
            }
        }
    }
}