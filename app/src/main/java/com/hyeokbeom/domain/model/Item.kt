package com.hyeokbeom.domain.model

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("header") val header: Header? = null,   /* Optional */
    @SerializedName("contents") val contents: Contents,
    @SerializedName("footer") val footer: Footer? = null,   /* Optional */
)