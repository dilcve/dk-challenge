package com.rf.dropchallenge.data.repository

import com.rf.dropchallenge.data.api.BeerApiService
import com.rf.dropchallenge.domain.repository.FileRepository
import okhttp3.ResponseBody


class FileRepositoryImpl(private val beerApiService: BeerApiService, private val inputUrl: String) : FileRepository {
    override suspend fun getInputFile(): ResponseBody =
        beerApiService.getInputFile(inputUrl)

}

