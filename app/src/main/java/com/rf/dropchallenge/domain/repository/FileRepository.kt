package com.rf.dropchallenge.domain.repository

interface FileRepository {

   suspend fun getInputFile(): String
}