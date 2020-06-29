package com.rf.dropchallenge.util

import kotlinx.coroutines.CoroutineDispatcher

interface AppDispatcher {

    fun getIO(): CoroutineDispatcher
    fun getMain(): CoroutineDispatcher
    fun getDefault(): CoroutineDispatcher
}