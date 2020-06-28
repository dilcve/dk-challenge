package com.rf.dropchallenge.data.repository

import com.rf.dropchallenge.data.api.BeerApiService
import com.rf.dropchallenge.data.response.mapTo
import com.rf.dropchallenge.domain.model.Beer
import com.rf.dropchallenge.domain.repository.BeersRepository



class BeersRepositoryImpl(private val beerApiService: BeerApiService) : BeersRepository {
    override suspend fun loadBeers(numBeers: Int): List<Beer> =
        beerApiService.getBeers(numBeers).map { it.mapTo() }

}

