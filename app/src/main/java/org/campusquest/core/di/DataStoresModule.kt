package org.campusquest.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.campusquest.core.data.repository.DataStoreOperationsImpl
import org.campusquest.core.data.repository.Repository
import org.campusquest.core.domain.repository.DataStoreOperations
import org.campusquest.core.domain.usecase.UseCase
import org.campusquest.core.domain.usecase.read_onboarding.ReadOnBoardingUseCase
import org.campusquest.core.domain.usecase.save_onboarding.SaveOnBoardingUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoresModule {

    @Provides
    @Singleton
    fun provideDataStoresPreference(@ApplicationContext context: Context): DataStoreOperations =
        DataStoreOperationsImpl(context)

    @Provides
    @Singleton
    fun provideUseCase(repository: Repository): UseCase = UseCase(
        readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
        saveOnBoardingUseCase = SaveOnBoardingUseCase(repository)
    )
}