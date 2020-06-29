package com.rf.dropchallenge

import com.rf.dropchallenge.domain.model.InputBeer
import com.rf.dropchallenge.domain.model.InputCustomer
import com.rf.dropchallenge.domain.usecase.CheckBreweryProblemUseCase
import com.rf.dropchallenge.domain.usecase.GetCustomersFromInputFileUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test
import java.io.BufferedReader
import java.io.IOException
import java.lang.Exception

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class CheckBreweryProblemUseCaseTest {
    private val checkBreweryProblemUseCase = CheckBreweryProblemUseCase()

    @Test
    fun `brewery problem success 1`() {

        val inputStream = javaClass.getResourceAsStream("/test_input_1.txt") ?: throw IOException()

        val content = inputStream.bufferedReader()
            .use(BufferedReader::readText)

        val beersAndCustomers = getBeersAndCustomersFromInputString(content)

        try {
            val result = checkBreweryProblemUseCase.checkBreweryProblem(
                beersAndCustomers.numBeers,
                beersAndCustomers.customers
            )
            val resultString = result.joinToString(" ", transform = { it.type })
            Assert.assertEquals("C C C C B", resultString)
        } catch (e: Exception) {
        }

    }

    @Test
    fun `brewery problem error 1`() {

        val inputStream = javaClass.getResourceAsStream("/test_input_2.txt") ?: throw IOException()

        val content = inputStream.bufferedReader()
            .use(BufferedReader::readText)

        val beersAndCustomers = getBeersAndCustomersFromInputString(content)

        try {
            val result = checkBreweryProblemUseCase.checkBreweryProblem(
                beersAndCustomers.numBeers,
                beersAndCustomers.customers
            )
        } catch (e: Exception) {
            println(e)
            Assert.assertEquals("No solution exists", e.message)
        }
    }

    private fun getBeersAndCustomersFromInputString(inputString: String): GetCustomersFromInputFileUseCase.BeersAndCustomers {
        val splitContent = inputString.split("\n")
        val customers = mutableListOf<InputCustomer>()

        val numBeers = inputString[0].toString().toInt()

        for (i in 1 until splitContent.size) {
            val noSpaceLine = splitContent[i].replace(" ", "")
            val beers = mutableListOf<InputBeer>()
            for (j in 0 until noSpaceLine.length - 1 step 2) {
                beers.add(
                    InputBeer(
                        id = noSpaceLine[j].toString().toInt(),
                        type = noSpaceLine[j + 1].toString()
                    )
                )
            }
            customers.add(InputCustomer(beers))
        }
        return GetCustomersFromInputFileUseCase.BeersAndCustomers(numBeers, customers)
    }
}