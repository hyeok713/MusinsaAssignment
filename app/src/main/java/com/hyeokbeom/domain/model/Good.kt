package com.hyeokbeom.domain.model

import com.google.gson.annotations.SerializedName

data class Good(
    @SerializedName("brandName") val brandName: String,
    @SerializedName("hasCoupon") val hasCoupon: Boolean,
    @SerializedName("linkURL") val linkURL: String,
    @SerializedName("price") val price: Int,
    @SerializedName("saleRate") val saleRate: Int,
    @SerializedName("thumbnailURL") val thumbnailURL: String
)