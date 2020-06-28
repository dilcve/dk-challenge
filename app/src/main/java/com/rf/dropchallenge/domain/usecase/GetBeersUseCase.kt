package com.rf.dropchallenge.domain.usecase

import com.rf.dropchallenge.domain.repository.BeersRepository

class GetBeersUseCase(private val beersRepository: BeersRepository) {

    suspend fun getBeers(numBeers: Int) = beersRepository.loadBeers(numBeers)
}