package com.rf.dropchallenge.data.repository

import com.rf.dropchallenge.data.api.ApiService
import com.rf.dropchallenge.data.response.mapTo
import com.rf.dropchallenge.domain.model.Beer
import com.rf.dropchallenge.domain.repository.BeersRepository


class BeersRepositoryImpl(private val apiService: ApiService) : BeersRepository {
    override suspend fun loadBeers(numBeers: Int): List<Beer> =
        apiService.getBeers(numBeers).map { it.mapTo() }

}

