package com.rf.dropchallenge.data.api

import com.rf.dropchallenge.data.response.BeerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DocumentApiService {

    @GET("/v2/beers/?page=1")
    suspend fun getBeers(@Query("per_page") numBeers: Int): List<BeerResponse>


}