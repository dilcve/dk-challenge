package com.rf.dropchallenge.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rf.dropchallenge.domain.model.Beer

class BeerSharedViewModel : ViewModel() {
    val selectedBeer = MutableLiveData<Beer>()
}

