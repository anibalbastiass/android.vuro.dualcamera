package com.anibalbastias.android.vuro.dualcamerapp.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.SavedStateViewModelFactory
import com.anibalbastias.android.vuro.R
import com.anibalbastias.android.vuro.databinding.FragmentMainBinding
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.applyFontForToolbarTitle
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.setNoArrowUpToolbar
import com.anibalbastias.android.vuro.dualcamerapp.base.module.getViewModel
import com.anibalbastias.android.vuro.dualcamerapp.base.view.BaseModuleFragment
import com.anibalbastias.android.vuro.dualcamerapp.presentation.appComponent
import com.anibalbastias.android.vuro.dualcamerapp.presentation.getAppContext


open class MainFragment : BaseModuleFragment() {

    override fun tagName(): String = this::class.java.simpleName
    override fun layoutId(): Int = R.layout.fragment_main

    lateinit var binding: FragmentMainBinding
//    lateinit var currenciesViewModel: CurrenciesViewModel

    private val clickListener by lazy {
        View.OnClickListener {
            with ((activity as MainActivity)) {
                when (it.id) {
                    R.id.btn_load_dualcamera -> loadAndLaunchModule(
                        moduleDualCamera
                    )
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        setHasOptionsMenu(true)
    }

    open fun setupInjection() {
        appComponent().inject(this)
        navBaseViewModel = getViewModel(viewModelFactory)
//        currenciesViewModel = getViewModel(viewModelFactory)
        sharedViewModel = activity!!.getViewModel(SavedStateViewModelFactory(getAppContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavController(this@MainFragment.view)

        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentMainBinding
//        binding.currenciesViewModel = currenciesViewModel
        binding.lifecycleOwner = this

        initToolbar()
        initViewModel()
        initializeViews()
    }

    private fun initializeViews() {
        setupClickListener()
    }

    private fun setupClickListener() {
        with(binding) {
            btnLoadDualcamera.setOnClickListener(clickListener)
        }
    }

    private fun initViewModel() {
        // Fetch Latest Currencies
//        with(currenciesViewModel) {
//            observe(getLatestCurrenciesLiveData(), ::observeCurrencies)
//            fetchCurrencies()
//        }
    }

//    private fun observeCurrencies(result: Result<UiCurrencies>?) {
//        when (result) {
//            is Result.OnLoading -> currenciesViewModel.isLoading.set(true)
//            is Result.OnSuccess -> {
//                currenciesViewModel.isLoading.set(false)
//                setLatestCurrenciesData(result.value)
//            }
//            is Result.OnError -> showErrorView()
//            else -> showErrorView()
//        }
//    }
//
//    private fun fetchCurrencies() {
//        currenciesViewModel.apply {
//            getLatestCurrenciesLiveData().value?.let { result ->
//                setLatestCurrenciesData((result as? Result.OnSuccess)?.value)
//            } ?: run {
//                isLoading.set(true)
//                getLatestCurrenciesData()
//            }
//
//            // Set Swipe Refresh Layout
//            binding.currenciesListSwipeRefreshLayout.initSwipe {
//                getLatestCurrenciesData()
//            }
//
//            // Observe changes for currency selected
//            currencySelected.addOnPropertyChanged {
//                getLatestCurrenciesData()
//            }
//        }
//    }
//
//    private fun setLatestCurrenciesData(viewData: UiCurrencies?) {
//
//        binding.currenciesListSwipeRefreshLayout.isRefreshing = false
//
//        currenciesViewModel.apply {
//            setLatestCurrenciesUi(viewData)
//
//            // Keep position for recyclerView
//            binding.currenciesListRecyclerView.paginationForRecyclerScroll(itemPosition)
//        }
//    }

    private fun initToolbar() {
        binding.mainToolbar.run {
            applyFontForToolbarTitle(activity!!)
            setNoArrowUpToolbar(activity!!)
        }
    }

//    private fun showErrorView() {
//        currenciesViewModel.isError.set(true)
//    }

}