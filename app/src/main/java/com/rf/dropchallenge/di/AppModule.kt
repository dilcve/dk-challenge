package com.rf.dropchallenge.di

import com.rf.dropchallenge.data.repository.BeersRepositoryImpl
import com.rf.dropchallenge.data.repository.FileRepositoryImpl
import com.rf.dropchallenge.domain.repository.BeersRepository
import com.rf.dropchallenge.domain.repository.FileRepository
import com.rf.dropchallenge.domain.usecase.CheckBreweryProblemUseCase
import com.rf.dropchallenge.domain.usecase.GetBeersUseCase
import com.rf.dropchallenge.domain.usecase.GetCustomersFromInputFileUseCase
import com.rf.dropchallenge.ui.BeerSharedViewModel
import com.rf.dropchallenge.ui.BeersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module


val appModule = module {

    single { BeersRepositoryImpl(get()) } bind BeersRepository::class
    single { FileRepositoryImpl(get(),get(named("inputUrl"))) } bind FileRepository::class
    single { GetBeersUseCase(get()) }
    single { GetCustomersFromInputFileUseCase(get()) }
    single { CheckBreweryProblemUseCase()}

    viewModel { BeersViewModel(get(),get(), get()) }
    viewModel { BeerSharedViewModel() }
}