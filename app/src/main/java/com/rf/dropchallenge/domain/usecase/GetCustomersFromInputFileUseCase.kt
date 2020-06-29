package com.rf.dropchallenge.domain.usecase

import com.rf.dropchallenge.domain.model.InputBeer
import com.rf.dropchallenge.domain.model.InputCustomer
import com.rf.dropchallenge.domain.repository.FileRepository

class GetCustomersFromInputFileUseCase(private val fileRepository: FileRepository) {
    suspend fun getInputFileBeerAndCustomers(): BeersAndCustomers {

        val content = fileRepository.getInputFile()

        return getBeersAndCustomersFromInputString(content)
    }

    private fun getBeersAndCustomersFromInputString(inputString: String): BeersAndCustomers {
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
        return BeersAndCustomers(numBeers, customers)
    }

    data class BeersAndCustomers(val numBeers: Int, val customers: List<InputCustomer>)
}