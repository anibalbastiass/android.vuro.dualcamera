package com.anibalbastias.android.vuro.dualcamerapp.di.module

import com.anibalbastias.android.vuro.dualcamerapp.data.dataStoreFactory.video.repository.VideoRepositoryImpl
import com.anibalbastias.android.vuro.dualcamerapp.domain.video.repository.IVideoRepository
import dagger.Binds
import dagger.Module

@Module
abstract class VuroRepositoryModule {

    @Binds
    abstract fun bindCurrenciesDataRepository(repository: VideoRepositoryImpl): IVideoRepository

}