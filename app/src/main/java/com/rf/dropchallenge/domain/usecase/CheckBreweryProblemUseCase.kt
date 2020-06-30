package com.rf.dropchallenge.domain.usecase

import com.rf.dropchallenge.domain.model.InputBeer
import com.rf.dropchallenge.domain.model.InputCustomer

class CheckBreweryProblemUseCase {

    fun checkBreweryProblem(
        numBeers: Int,
        customers: List<InputCustomer>
    ): Array<InputBeer> {
        val result = Array(numBeers) { InputBeer(1, "A") }

        //check if the supported beer type per client and add the beer to the result array
        getFirstBatchDraftBasedOnCustomerPreferences(customers, result)

        //checking if each user is getting at least one beer he likes
        checkIfCustomersHasAtLeastOneHeLikes(customers, result)

        //convert any remaining "A" to "C"
        result.map { if (it.isAny()) it.type = "C" }

        return result
    }

    private fun getFirstBatchDraftBasedOnCustomerPreferences(
        customers: List<InputCustomer>,
        result: Array<InputBeer>
    ) {
        for (customer in customers) {
            val supportedType = if (customer.beers.size > 1) "A" else customer.beers.first().type
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

    //new way
    fun checkBreweryProblemNew(
        numBeers: Int,
        customers: List<InputCustomer>
    ): Array<InputBeer> {
        val result = Array(numBeers) { InputBeer(1, "A") }

        val (oneBeerCustomers, moreThanOneBeerCustomers) = customers.partition {
            it.beers.size == 1
        }

        if (oneBeerCustomers.isNotEmpty()) {
            //set one beer client beers into result array and check if there is any conflict between them
            getFirstBatchDraftBasedOnOneBeerCustomersPreferences(oneBeerCustomers, result)

            //checking if each user is getting at least one beer he likes
            checkIfCustomersHasAtLeastOneHeLikes(moreThanOneBeerCustomers, result)
        }

        //convert any remaining "A" to "C"
        result.map { if (it.isAny()) it.type = "C" }

        return result
    }

    private fun getFirstBatchDraftBasedOnOneBeerCustomersPreferences(
        customers: List<InputCustomer>,
        result: Array<InputBeer>
    ) {

        for (customer in customers) {
            val beer = customer.beers.first()

            result[beer.id - 1].run {

                if (!this.isAny() && this.type != beer.type) {
                    throw IllegalStateException("No solution exists")
                } else {
                    result[beer.id - 1] = beer
                }
            }
        }
    }
}