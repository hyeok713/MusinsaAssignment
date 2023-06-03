package com.hyeokbeom.domain.model

import com.google.gson.annotations.SerializedName

data class Header(
    @SerializedName("iconURL") val iconURL: String? = null, /* Optional */
    @SerializedName("linkURL") val linkURL: String? = null, /* Optional */
    @SerializedName("title") val title: String
)