package com.rf.dropchallenge.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rf.dropchallenge.R
import com.rf.dropchallenge.domain.model.Beer
import com.rf.dropchallenge.domain.usecase.CheckBreweryProblemUseCase
import com.rf.dropchallenge.domain.usecase.GetBeersUseCase
import com.rf.dropchallenge.domain.usecase.GetCustomersFromInputFileUseCase
import com.rf.dropchallenge.util.AppDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BeersViewModel(
    private val getBeersUseCase: GetBeersUseCase,
    private val getCustomersFromInputFileUseCase: GetCustomersFromInputFileUseCase,
    private val checkBreweryProblemUseCase: CheckBreweryProblemUseCase,
    private val appDispatcher: AppDispatcher
) : ViewModel() {
    val beers = MutableLiveData<List<Beer>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Int>()

    fun getFileAndGetSolution() {
        viewModelScope.launch(appDispatcher.getIO()) {
            loading.postValue(true)
            try {
                val beersAndCustomers =
                    getCustomersFromInputFileUseCase.getInputFileBeersAndCustomers()

                withContext(appDispatcher.getDefault()) {
                    val inputBeers = checkBreweryProblemUseCase.checkBreweryProblemNew(
                        beersAndCustomers.numBeers,
                        beersAndCustomers.customers
                    )

                    withContext(appDispatcher.getIO()) {
//                        getBeers(inputBeers)
                        beers.postValue(getBeersUseCase.getBeers(inputBeers.size).map {
                            it.type = inputBeers[it.id - 1].type
                            return@map it
                        })
                    }
                }
            } catch (e: Exception) {
                Log.e(this@BeersViewModel.javaClass.simpleName, "error: $e")
                if (e is IllegalStateException) {
                    error.postValue(R.string.error_no_solution)
                } else {
                    error.postValue(R.string.error_generic)
                }
            }
            loading.postValue(false)
        }
    }
}