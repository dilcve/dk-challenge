package com.rf.dropchallenge.util

import kotlinx.coroutines.Dispatchers

class AppDispatcherImpl : AppDispatcher {
    override fun getIO() = Dispatchers.IO
    override fun getMain() = Dispatchers.Main
    override fun getDefault() = Dispatchers.Default
}