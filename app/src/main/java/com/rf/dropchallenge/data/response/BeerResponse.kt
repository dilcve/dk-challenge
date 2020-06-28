package com.rf.dropchallenge.data.response

import com.google.gson.annotations.SerializedName
import com.rf.dropchallenge.domain.model.Beer

data class BeerResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("abv")
    var abv: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("ingredients")
    var ingredients: Ingredients,
    @SerializedName("method")
    var methods: Methods,
    @SerializedName("image_url")
    var imageUrl: String
)

data class Methods(
    @SerializedName("mash_temp") val mashTemp: List<MashTemp>,
    @SerializedName("fermentation") val fermentation: Fermentation,
    @SerializedName("twist") val twist: String? = null
)

data class MashTemp(
    @SerializedName("temp") val temp: Temp,
    @SerializedName("duration") val duration: Int? = null
)

data class Fermentation(@SerializedName("temp") val temp: Temp)
data class Temp(@SerializedName("value") val value: Int, @SerializedName("unit") val unit: String)
data class Ingredient(
    @SerializedName("name") val name: String = "",
    @SerializedName("amount") val amount: Amount
)

data class Amount(
    @SerializedName("value") val value: Double = 0.0,
    @SerializedName("unit") val unit: String = ""
)

data class Ingredients(
    @SerializedName("hops")
    val hops: List<Ingredient>,
    @SerializedName("malt")
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
