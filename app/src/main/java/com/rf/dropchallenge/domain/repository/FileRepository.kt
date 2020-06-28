package com.rf.dropchallenge.domain.repository

import okhttp3.ResponseBody

interface FileRepository {

   suspend fun getInputFile(url: String): ResponseBody
}