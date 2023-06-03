package com.hyeokbeom.domain.model

import com.google.gson.annotations.SerializedName

data class Style(
    @SerializedName("linkURL") val linkURL: String,
    @SerializedName("thumbnailURL") val thumbnailURL: String
)