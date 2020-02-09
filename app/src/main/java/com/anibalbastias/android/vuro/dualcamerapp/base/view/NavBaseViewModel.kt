package com.anibalbastias.android.vuro.dualcamerapp.base.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anibalbastias.android.vuro.dualcamerapp.base.navigation.BaseNavigationListener
import com.anibalbastias.android.vuro.dualcamerapp.base.navigation.event.Event
import javax.inject.Inject


class NavBaseViewModel @Inject constructor() : BaseViewModel() {

    //region Navigation
    private val newDestination = MutableLiveData<Pair<Event<Int?>, BaseNavigationListener?>>()

    fun getNewDestination(): LiveData<Pair<Event<Int?>, BaseNavigationListener?>> {
        return newDestination
    }

    fun setNewDestination(destinationId: Int?) {
        newDestination.value = Pair(Event(destinationId), null)
    }

    fun setNewDestination(destinationId: Int?, callback: BaseNavigationListener? = null) {
        newDestination.value = Pair(Event(destinationId), callback)
    }

    fun setNavigateUp() {
        newDestination.value = Pair(Event(-1), null)
    }
    //endregion

}