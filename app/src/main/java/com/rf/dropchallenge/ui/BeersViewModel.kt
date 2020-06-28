package com.rf.dropchallenge.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rf.dropchallenge.R
import com.rf.dropchallenge.domain.model.Beer
import com.rf.dropchallenge.domain.model.InputBeer
import com.rf.dropchallenge.domain.usecase.CheckBreweryProblemUseCase
import com.rf.dropchallenge.domain.usecase.GetBeersUseCase
import com.rf.dropchallenge.domain.usecase.GetCustomersFromInputFileUseCase
import kotlinx.coroutines.launch

class BeersViewModel(
    private val getBeersUseCase: GetBeersUseCase,
    private val getCustomersFromInputFileUseCase: GetCustomersFromInputFileUseCase,
    private val checkBreweryProblemUseCase: CheckBreweryProblemUseCase
) : ViewModel() {
    val beers = MutableLiveData<List<Beer>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Int>()
    var inputBeers = arrayOf<InputBeer>()

    fun getFileAndGetSolution() {
        viewModelScope.launch {
            loading.value = true
            try {
                val beersAndCustomers =
                    getCustomersFromInputFileUseCase.getInputFileBeerAndCustomers()
                inputBeers = checkBreweryProblemUseCase.checkBreweryProblem(
                    beersAndCustomers.numBeers,
                    beersAndCustomers.customers
                )
                getBeers(inputBeers.size)
            } catch (e: Exception) {
                error.value = R.string.error_no_solution
            }
            loading.value = false
        }
    }

    private fun getBeers(numBeers: Int) {

        viewModelScope.launch {
            loading.value = true
            try {
                beers.value = getBeersUseCase.getBeers(numBeers).map {
                    it.type = inputBeers[it.id - 1].type
                    return@map it
                }
            } catch (e: Exception) {
                error.value = R.string.error_generic
            }
            loading.value = false
        }
    }
}