package com.rf.dropchallenge.data.repository

import com.rf.dropchallenge.data.api.BeerApiService
import com.rf.dropchallenge.domain.repository.FileRepository
import java.io.BufferedReader


class FileRepositoryImpl(private val beerApiService: BeerApiService, private val inputUrl: String) :
    FileRepository {
    override suspend fun getInputFile(): String =
        beerApiService.getInputFile(inputUrl).byteStream().bufferedReader()
            .use(BufferedReader::readText)
}

