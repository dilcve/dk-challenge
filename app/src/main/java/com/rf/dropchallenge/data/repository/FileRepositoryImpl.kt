package com.rf.dropchallenge.data.repository

import com.rf.dropchallenge.data.api.ApiService
import com.rf.dropchallenge.domain.repository.FileRepository
import java.io.BufferedReader


class FileRepositoryImpl(private val apiService: ApiService, private val inputUrl: String) :
    FileRepository {
    override suspend fun getInputFile(): String =
        apiService.getInputFile(inputUrl).byteStream().bufferedReader()
            .use(BufferedReader::readText)
}

