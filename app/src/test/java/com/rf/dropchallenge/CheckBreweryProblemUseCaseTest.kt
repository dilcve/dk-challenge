package com.rf.dropchallenge

import com.rf.dropchallenge.domain.repository.FileRepository
import com.rf.dropchallenge.domain.usecase.CheckBreweryProblemUseCase
import com.rf.dropchallenge.domain.usecase.GetCustomersFromInputFileUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader
import java.io.IOException

@ExperimentalCoroutinesApi
class CheckBreweryProblemUseCaseTest {

    private val checkBreweryProblemUseCase = CheckBreweryProblemUseCase()

    @MockK
    private lateinit var fileRepository: FileRepository

    @MockK
    private lateinit var getCustomersFromInputFileUseCase: GetCustomersFromInputFileUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getCustomersFromInputFileUseCase = GetCustomersFromInputFileUseCase(fileRepository)
    }

    @Test
    fun `brewery problem success 1`() = runBlockingTest {

        val inputStream = javaClass.getResourceAsStream("/test_input_1.txt") ?: throw IOException()

        coEvery { fileRepository.getInputFile() }.coAnswers {
            inputStream.bufferedReader()
                .use(BufferedReader::readText)
        }

        val beersAndCustomers = getCustomersFromInputFileUseCase.getInputFileBeerAndCustomers()

        val result = checkBreweryProblemUseCase.checkBreweryProblem(
            beersAndCustomers.numBeers,
            beersAndCustomers.customers
        )
        val resultString = result.joinToString(" ", transform = { it.type })
        Assert.assertEquals("C C C C B", resultString)
    }

    @Test
    fun `brewery problem success 2`() = runBlockingTest {

        val inputStream = javaClass.getResourceAsStream("/test_input_3.txt") ?: throw IOException()

        coEvery { fileRepository.getInputFile() }.coAnswers {
            inputStream.bufferedReader()
                .use(BufferedReader::readText)
        }

        val beersAndCustomers = getCustomersFromInputFileUseCase.getInputFileBeerAndCustomers()

        val result = checkBreweryProblemUseCase.checkBreweryProblem(
            beersAndCustomers.numBeers,
            beersAndCustomers.customers
        )
        val resultString = result.joinToString(" ", transform = { it.type })
        Assert.assertEquals("C B C B C", resultString)


    }

    @Test
    fun `brewery problem success 3`() = runBlockingTest {

        val inputStream = javaClass.getResourceAsStream("/test_input_4.txt") ?: throw IOException()

        coEvery { fileRepository.getInputFile() }.coAnswers {
            inputStream.bufferedReader()
                .use(BufferedReader::readText)
        }

        val beersAndCustomers = getCustomersFromInputFileUseCase.getInputFileBeerAndCustomers()

        val result = checkBreweryProblemUseCase.checkBreweryProblem(
            beersAndCustomers.numBeers,
            beersAndCustomers.customers
        )
        val resultString = result.joinToString(" ", transform = { it.type })
        Assert.assertEquals("B B", resultString)
    }

    @Test
    fun `brewery problem success 4`() = runBlockingTest {

        val inputStream = javaClass.getResourceAsStream("/test_input_5.txt") ?: throw IOException()

        coEvery { fileRepository.getInputFile() }.coAnswers {
            inputStream.bufferedReader()
                .use(BufferedReader::readText)
        }

        val beersAndCustomers = getCustomersFromInputFileUseCase.getInputFileBeerAndCustomers()

        val result = checkBreweryProblemUseCase.checkBreweryProblem(
            beersAndCustomers.numBeers,
            beersAndCustomers.customers
        )
        val resultString = result.joinToString(" ", transform = { it.type })
        Assert.assertEquals("B B B", resultString)
    }

    @Test
    fun `brewery problem success 5`() = runBlockingTest {

        val inputStream = javaClass.getResourceAsStream("/test_input_6.txt") ?: throw IOException()

        coEvery { fileRepository.getInputFile() }.coAnswers {
            inputStream.bufferedReader()
                .use(BufferedReader::readText)
        }

        val beersAndCustomers = getCustomersFromInputFileUseCase.getInputFileBeerAndCustomers()

        val result = checkBreweryProblemUseCase.checkBreweryProblem(
            beersAndCustomers.numBeers,
            beersAndCustomers.customers
        )
        val resultString = result.joinToString(" ", transform = { it.type })
        Assert.assertEquals("B C C C C", resultString)
    }

    @Test
    fun `brewery problem error 1`() = runBlockingTest {

        val inputStream = javaClass.getResourceAsStream("/test_input_2.txt") ?: throw IOException()

        coEvery { fileRepository.getInputFile() }.coAnswers {
            inputStream.bufferedReader()
                .use(BufferedReader::readText)
        }

        val beersAndCustomers = getCustomersFromInputFileUseCase.getInputFileBeerAndCustomers()

        try {
            checkBreweryProblemUseCase.checkBreweryProblem(
                beersAndCustomers.numBeers,
                beersAndCustomers.customers
            )
        } catch (e: Exception) {
            println(e)
            Assert.assertEquals("No solution exists", e.message)
        }
    }
}