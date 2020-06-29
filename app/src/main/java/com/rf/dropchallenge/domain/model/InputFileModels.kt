package com.rf.dropchallenge.domain.model

data class InputCustomer(val beers: List<InputBeer>)
data class InputBeer(val id: Int, var type: String) {
    fun isClassic() = type == "C"
    fun isBarrelAged() = type == "B"
    fun isAny() = type == "A"
}