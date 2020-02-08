package com.anibalbastias.android.vuro.dualcamerapp.di.module

import androidx.lifecycle.ViewModel
import com.anibalbastias.android.vuro.dualcamerapp.base.module.ViewModelKey
import com.anibalbastias.android.vuro.dualcamerapp.base.module.module.BaseViewModelModule
import com.anibalbastias.android.vuro.dualcamerapp.base.view.NavBaseViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(NavBaseViewModel::class)
    internal abstract fun navBaseViewModel(viewModel: NavBaseViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(CurrenciesViewModel::class)
//    internal abstract fun curenciesViewModel(viewModel: CurrenciesViewModel): ViewModel

}