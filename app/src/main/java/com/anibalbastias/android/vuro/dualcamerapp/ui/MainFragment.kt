package com.anibalbastias.android.vuro.dualcamerapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.SavedStateViewModelFactory
import com.anibalbastias.android.vuro.R
import com.anibalbastias.android.vuro.databinding.FragmentMainBinding
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.applyFontForToolbarTitle
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.setNoArrowUpToolbar
import com.anibalbastias.android.vuro.dualcamerapp.base.module.coroutines.observe
import com.anibalbastias.android.vuro.dualcamerapp.base.module.getViewModel
import com.anibalbastias.android.vuro.dualcamerapp.base.view.BaseModuleFragment
import com.anibalbastias.android.vuro.dualcamerapp.presentation.model.UiVideo
import com.anibalbastias.android.vuro.dualcamerapp.presentation.viewmodel.VideoViewModel
import com.anibalbastias.android.vuro.dualcamerapp.base.module.coroutines.Result

open class MainFragment : BaseModuleFragment() {

    override fun tagName(): String = this::class.java.simpleName
    override fun layoutId(): Int = R.layout.fragment_main

    lateinit var binding: FragmentMainBinding
    lateinit var videoViewModel: VideoViewModel

    private val clickListener by lazy {
        View.OnClickListener {
            with((activity as MainActivity)) {
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
        videoViewModel = getViewModel(viewModelFactory)
        sharedViewModel = activity!!.getViewModel(SavedStateViewModelFactory(getAppContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavController(this@MainFragment.view)

        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentMainBinding
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
        with(videoViewModel) {
            observe(getUploadVideoLiveData(), ::render)
            fetchCurrencies()
        }
    }

    private fun render(result: Result<UiVideo>?) {
        when (result) {
            is Result.OnLoading -> videoViewModel.isLoading.set(true)
            is Result.OnSuccess -> {
                videoViewModel.isLoading.set(false)
                setVideoSuccess(result.value)
            }
            is Result.OnError -> showErrorView()
            else -> showErrorView()
        }
    }

    private fun setVideoSuccess(value: UiVideo) {
        Log.v("Video", "Video Success: ${value.success}")
    }

    private fun fetchCurrencies() {
        videoViewModel.apply {
            getUploadVideoLiveData().value?.let { result ->
                setVideoSuccess((result as? Result.OnSuccess)?.value!!)
            } ?: run {
                isLoading.set(true)
                uploadVideoData("")
            }
        }
    }

    private fun initToolbar() {
        binding.mainToolbar.run {
            applyFontForToolbarTitle(activity!!)
            setNoArrowUpToolbar(activity!!)
        }
    }

    private fun showErrorView() {
        videoViewModel.isError.set(true)
    }

}