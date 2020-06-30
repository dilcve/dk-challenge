package com.rf.dropchallenge.data.response

import com.google.gson.annotations.SerializedName
import com.rf.dropchallenge.domain.model.*

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
    var ingredients: IngredientsResponse,
    @SerializedName("method")
    var methods: MethodsResponse,
    @SerializedName("image_url")
    var imageUrl: String
)

data class MethodsResponse(
    @SerializedName("mash_temp") val mashTemp: List<MashTempResponse>,
    @SerializedName("fermentation") val fermentation: FermentationResponse,
    @SerializedName("twist") val twist: String? = null
)

data class MashTempResponse(
    @SerializedName("temp") val temp: TempResponse,
    @SerializedName("duration") val duration: Int? = null
)

data class FermentationResponse(@SerializedName("temp") val temp: TempResponse)

data class TempResponse(
    @SerializedName("value") val value: Int,
    @SerializedName("unit") val unit: String
)

data class IngredientResponse(
    @SerializedName("name") val name: String = "",
    @SerializedName("amount") val amount: AmountResponse
)

data class AmountResponse(
    @SerializedName("value") val value: Double = 0.0,
    @SerializedName("unit") val unit: String = ""
)

data class IngredientsResponse(
    @SerializedName("hops")
    val hops: List<IngredientResponse>,
    @SerializedName("malt")
    val malt: List<IngredientResponse>
)

fun BeerResponse.mapTo() = Beer(
    id = this.id,
    name = this.name,
    abv = this.abv,
    description = this.description,
    imageUrl = imageUrl,
    hops = this.ingredients.hops.map { it.mapTo() },
    malts = this.ingredients.malt.map { it.mapTo() },
    methods = this.methods.mapTo()
)

fun IngredientResponse.mapTo() = Ingredient(
    name = this.name,
    amount = "${this.amount.value} ${this.amount.unit}"
)

fun MethodsResponse.mapTo() =
    Methods(this.mashTemp.map { it.mapTo() }, this.fermentation.mapTo(), this.twist)

fun FermentationResponse.mapTo() = Fermentation(Temp(this.temp.value, this.temp.unit))

fun MashTempResponse.mapTo() = MashTemp(Temp(this.temp.value, this.temp.unit), this.duration)
