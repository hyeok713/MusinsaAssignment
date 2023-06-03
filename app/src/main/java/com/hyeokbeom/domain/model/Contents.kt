package com.hyeokbeom.domain.model

import com.google.gson.annotations.SerializedName

data class Contents(
    @SerializedName("banners") val banners: List<Banner>,
    @SerializedName("goods") val goods: List<Good>,
    @SerializedName("styles") val styles: List<Style>,
    @SerializedName("type") val type: String
)