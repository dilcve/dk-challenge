package com.rf.dropchallenge.domain.repository

import com.rf.dropchallenge.domain.model.Beer

interface BeersRepository {

   suspend fun loadBeers(numBeers: Int): List<Beer>
}