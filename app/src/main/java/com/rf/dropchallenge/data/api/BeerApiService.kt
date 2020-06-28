package com.rf.dropchallenge.data.api

import com.rf.dropchallenge.data.response.BeerResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface BeerApiService {

    @GET("/v2/beers/?page=1")
    suspend fun getBeers(@Query("per_page") numBeers: Int): List<BeerResponse>

    @GET
    suspend fun getInputFile(@Url fileUrl: String): ResponseBody


}