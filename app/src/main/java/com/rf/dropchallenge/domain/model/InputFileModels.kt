package com.rf.dropchallenge.domain.model

data class InputCustomer(val beers: List<InputBeer>) {
    fun getSupportedBeerType(): String {
        var supportedType = ""
        beers.find { it.type == "C" }?.run {
            supportedType = "C"
        }
        beers.find { it.type == "B" }?.run {
            supportedType = if (supportedType == "C") {
                "A"
            } else {
                "B"
            }
        }
        return supportedType
    }
}

data class InputBeer(val id: Int, var type: String) {
    fun isClassic() = type == "C"
    fun isBarrelAged() = type == "B"
    fun isAny() = type == "A"
}