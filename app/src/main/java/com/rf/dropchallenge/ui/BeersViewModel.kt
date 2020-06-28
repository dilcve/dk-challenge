package com.rf.dropchallenge.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rf.dropchallenge.R
import com.rf.dropchallenge.domain.model.Beer
import com.rf.dropchallenge.domain.model.InputBeer
import com.rf.dropchallenge.domain.usecase.GetBeersUseCase
import com.rf.dropchallenge.domain.usecase.GetCustomersFromInputFile
import kotlinx.coroutines.launch

class BeersViewModel(
    private val getBeersUseCase: GetBeersUseCase,
    private val getCustomersFromInputFile: GetCustomersFromInputFile
) : ViewModel() {
    val beers = MutableLiveData<List<Beer>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Int>()
    var inputBeers = arrayOf<InputBeer>()

    fun getFileAndGetSolution() {
        viewModelScope.launch {
            loading.value = true
            try {
                inputBeers = getCustomersFromInputFile.getInputFileAndGetSolution()
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
            beers.value = try {
                getBeersUseCase.getBeers(numBeers)
            } catch (e: Exception) {
                error.value = R.string.error_generic
                emptyList()
            }
            loading.value = false
        }
    }
}