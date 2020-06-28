package com.rf.dropchallenge.domain.usecase

import com.rf.dropchallenge.domain.model.InputBeer
import com.rf.dropchallenge.domain.model.InputCustomer
import com.rf.dropchallenge.domain.repository.FileRepository
import java.io.BufferedReader

class GetCustomersFromInputFile(private val fileRepository: FileRepository) {
    suspend fun getInputFileAndGetSolution(): Array<InputBeer> {

        val content = fileRepository.getInputFile().byteStream().bufferedReader()
            .use(BufferedReader::readText)

        val beersAndCustomers = getBeersAndCustomersFromInputString(content)

        return breweryProblem(beersAndCustomers.numBeers, beersAndCustomers.customers)
    }

    private fun getBeersAndCustomersFromInputString(inputString: String): BeersAndCustomers {
        val splitContent = inputString.split("\n")
        val customers = mutableListOf<InputCustomer>()

        val numBeers = inputString[0].toString().toInt()

        for (i in 1 until splitContent.size) {
            val noSpaceLine = splitContent[i].replace(" ", "")
            val beers = mutableListOf<InputBeer>()
            for (j in 0 until noSpaceLine.length - 1 step 2) {
                println("${noSpaceLine[j]}${noSpaceLine[j + 1]}")
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

    private fun breweryProblem(numBeers: Int, customers: List<InputCustomer>): Array<InputBeer> {
        val result = Array(numBeers) { InputBeer(1, "A") }

        //check if the supported beer type per client and add the beer to the result array
        getFirstBatchDraftBasedOnCustomerPreferences(customers, result)

        //checking if each user is getting at least one beer he likes
        checkIfCustomersHasAtLeastOneHeLikes(customers, result)

        //convert any remaining "A" to "C"
        result.map { if (it.isAny()) it.type = "C" }

        println(result.joinToString(" ", transform = { it.type }))
        return result
    }

    private fun checkIfCustomersHasAtLeastOneHeLikes(
        customers: List<InputCustomer>,
        result: Array<InputBeer>
    ) {
        for (customer in customers) {

            var isSatisfied = false
            var couldSatisfyWithB: InputBeer? = null
            for (beer in customer.beers) {

                isSatisfied = result[beer.id - 1] == beer
                if (isSatisfied) {
                    break
                } else {
                    if (result[beer.id - 1].isAny()) {
                        if (beer.isBarrelAged()) {
                            couldSatisfyWithB = beer
                        } else {
                            result[beer.id - 1].type = beer.type
                            isSatisfied = true
                            break
                        }

                    }
                }
            }
            if (!isSatisfied) {
                couldSatisfyWithB?.run {
                    //only satisfy it with Barrel Aged if wasn't possible with Classic
                    result[this.id - 1].type = this.type
                } ?: throw IllegalStateException("No solution exists")
            }
        }
    }

    private fun getFirstBatchDraftBasedOnCustomerPreferences(
        customers: List<InputCustomer>,
        result: Array<InputBeer>
    ) {
        for (customer in customers) {
            val supportedType = customer.getSupportedBeerType()
            for (beer in customer.beers) {

                result[beer.id - 1].run {

                    if (!this.isAny() && this.type != beer.type) {
                        if (supportedType != "A")
                            throw IllegalStateException("No solution exists")
                    } else {
                        result[beer.id - 1] = beer.copy(type = supportedType)
                    }
                }
            }
        }
    }
}