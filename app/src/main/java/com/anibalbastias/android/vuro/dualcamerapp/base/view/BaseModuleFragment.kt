package com.anibalbastias.android.vuro.dualcamerapp.base.view

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.inflate
import com.anibalbastias.android.vuro.dualcamerapp.base.module.ViewModelFactory

import javax.inject.Inject

abstract class BaseModuleFragment : Fragment() {

    abstract fun tagName(): String
    abstract fun layoutId(): Int

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var navBaseViewModel: NavBaseViewModel
    lateinit var sharedViewModel: SharedViewModel

    var mResources: Resources? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retainInstance = true
        mResources = resources

        try {
            return container?.inflate(layoutId())
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return null
    }

    var navController: NavController? = null

    fun setNavController(view: View?) {
        view?.let {
            navController = Navigation.findNavController(it)
            navBaseViewModel.getNewDestination().observe(this@BaseModuleFragment, Observer { dest ->
                if (dest.first.status == ResourceState.LOADING) {
                    dest.second?.let { listener ->
                        listener.onNextNavigate(dest.first.consume())
                    } ?: run {
                        nextNavigate(dest.first.consume())
                    }
                    dest.first.status = ResourceState.DEFAULT
                }
            })
        }
    }

    private fun navigateToUp() {
        navController?.navigateUp()
    }

    fun nextNavigate(nav: Int?, bundle: Bundle? = null, extras: Navigator.Extras? = null) {
        when (nav) {
            -1 -> navigateToUp()
            else -> navigate(nav ?: 0, bundle, extras)
        }
    }

    private fun navigate(
        destination: Int,
        bundle: Bundle? = null,
        extras: Navigator.Extras?
    ) {
        if (destination == 0)
            navController?.navigateUp()
        else {
            try {
                navController?.navigate(destination, bundle, null, extras)
            } catch (e: IllegalArgumentException) {
                // Actions for finish flow
                activity?.finish()
            }
        }
    }
}