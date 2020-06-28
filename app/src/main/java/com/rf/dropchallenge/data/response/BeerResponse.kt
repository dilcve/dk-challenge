package com.rf.dropchallenge.data.response

import com.google.gson.annotations.SerializedName
import com.rf.dropchallenge.domain.model.Beer

data class BeerResponse(
    var id: Int,
    var name: String,
    var abv: String,
    var description: String,
    var ingredients: Ingredients,
    @SerializedName("method")
    var methods: Methods,
    @SerializedName("image_url")
    var imageUrl: String
)

data class Methods(@SerializedName("mash_temp") val mashTemp: List<MashTemp>, val fermentation: Fermentation, val twist: String? = null)
data class MashTemp(val temp: Temp, val duration: Int? = null)
data class Fermentation(val temp: Temp)
data class Temp(val value: Int, val unit: String)
data class Ingredient(val name: String = "", val amount: Amount)
data class Amount(val value: Double = 0.0, val unit: String = "")
data class Ingredients(
    val hops: List<Ingredient>,
    val malt: List<Ingredient>
)

fun BeerResponse.mapTo() = Beer(
    id = this.id,
    name = this.name,
    abv = this.abv,
    description = this.description,
    imageUrl = imageUrl,
    hops = this.ingredients.hops.map { it.mapTo() },
    malts = this.ingredients.malt.map { it.mapTo() },
    methods = this.methods
)

fun Ingredient.mapTo() = com.rf.dropchallenge.domain.model.Ingredient(
    name = this.name,
    amount = "${this.amount.value} ${this.amount.unit}"
)
