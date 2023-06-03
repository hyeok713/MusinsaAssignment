package com.hyeokbeom.domain.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("contents") val contents: Contents,
    @SerializedName("footer") val footer: Footer,
    @SerializedName("header") val header: Header
)