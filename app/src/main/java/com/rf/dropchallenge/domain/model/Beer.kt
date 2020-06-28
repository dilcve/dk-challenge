package com.rf.dropchallenge.domain.model

import com.rf.dropchallenge.data.response.Methods

data class Beer(
    val id: Int,
    val name: String,
    var type: String = "Classic",
    val abv: String,
    val description: String,
    val imageUrl: String = "https://images.punkapi.com/v2/keg.png",
    val hops: List<Ingredient>,
    val malts: List<Ingredient>,
    val methods: Methods
)

data class Ingredient(val name: String, val amount: String)