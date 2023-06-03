package com.hyeokbeom.domain.model

import com.google.gson.annotations.SerializedName

data class InterviewResponse(
    @SerializedName("data") val data: List<Data>
)