package com.hyeokbeom.domain.model

import com.google.gson.annotations.SerializedName

data class MainListResponse(
    @SerializedName("data") val list: List<Item>
)