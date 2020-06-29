package com.rf.dropchallenge.domain.model

data class Beer(
    val id: Int,
    val name: String,
    var type: String = "C",
    val abv: String,
    val description: String,
    val imageUrl: String = "https://images.punkapi.com/v2/keg.png",
    val hops: List<Ingredient>,
    val malts: List<Ingredient>,
    val methods: Methods
) {
    fun getDescType() = if (type == "B") {
        "Barrel Aged"
    } else {
        "Classic"
    }
}

data class Ingredient(val name: String, val amount: String)
data class Methods(
    val mashTemp: List<MashTemp>,
    val fermentation: Fermentation,
    val twist: String? = null
)

data class MashTemp(
    val temp: Temp,
    val duration: Int? = null
)

data class Fermentation(val temp: Temp)
data class Temp(
    val value: Int,
    val unit: String
)