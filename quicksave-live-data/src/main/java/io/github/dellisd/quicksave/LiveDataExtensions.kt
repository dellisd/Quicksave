package io.github.dellisd.quicksave

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Converts this setting into a [LiveData] which can be observed
 */
fun <T : Any> Setting<T>.asLiveData(): LiveData<T> = asMutableLiveData()

fun <T : Any> Setting<T>.asMutableLiveData(): MutableLiveData<T> {
    val liveData = MutableLiveData<T>()
    addListener(liveData::postValue)

    liveData.observeForever {
        value = it
    }

    return liveData
}