package com.hyeokbeom.domain.model

import com.google.gson.annotations.SerializedName

data class Footer(
    @SerializedName("iconURL") val iconURL: String,
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: String
)