package com.rf.dropchallenge

import com.rf.dropchallenge.util.AppDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherImpl : AppDispatcher {
    override fun getIO() = TestCoroutineDispatcher()
    override fun getMain() = TestCoroutineDispatcher()
    override fun getDefault() = TestCoroutineDispatcher()

    fun cleanupTestCoroutines() {
        getIO().cleanupTestCoroutines()
        getMain().cleanupTestCoroutines()
        getDefault().cleanupTestCoroutines()
    }
}