package com.anibalbastias.android.vuro.dualcamerapp.base.navigation

import android.os.Bundle
import android.view.View
import androidx.navigation.NavDirections

/**
 * Created by anibal.bastias on 20, August, 2019
 */
interface BaseNavigationListener {

    fun setAction(actionId: Int, view: View, isLastElement: Boolean, bundle : Bundle? = null)

    fun setDirection(navDirection: NavDirections, view: View, isLastElement: Boolean)

    fun onNextNavigate(nextNav: Int?)

}