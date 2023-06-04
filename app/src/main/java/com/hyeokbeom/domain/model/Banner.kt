package com.hyeokbeom.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Banner
 * [배너 정보]
 * for BANNER type contents
 */
data class Banner(
    @SerializedName("description") val description: String,
    @SerializedName("keyword") val keyword: String,
    @SerializedName("linkURL") val linkURL: String,
    @SerializedName("thumbnailURL") val thumbnailURL: String,
    @SerializedName("title") val title: String
)