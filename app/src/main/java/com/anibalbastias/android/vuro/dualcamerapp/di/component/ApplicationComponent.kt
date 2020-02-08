package com.anibalbastias.android.vuro.dualcamerapp.di.component

import com.anibalbastias.android.vuro.dualcamerapp.base.module.component.BaseApplicationComponent
import com.anibalbastias.android.vuro.dualcamerapp.di.module.ApplicationModule
import com.anibalbastias.android.vuro.dualcamerapp.di.module.VuroAPIModule
import com.anibalbastias.android.vuro.dualcamerapp.di.module.VuroRepositoryModule
import com.anibalbastias.android.vuro.dualcamerapp.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        VuroRepositoryModule::class,
        ViewModelModule::class,
        VuroAPIModule::class]
)

interface ApplicationComponent : BaseApplicationComponent, FragmentInjector {
//    fun inject(mainActivity: MainActivity)
}

interface FragmentInjector {
//    fun inject(currenciesFragment: CurrenciesFragment)
}