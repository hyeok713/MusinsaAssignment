package com.hyeokbeom.data.datasource

import com.hyeokbeom.domain.model.InterviewResponse
import retrofit2.http.GET

interface MusinsaAPI {
    @GET("interview/list.json")
    suspend fun interview(): InterviewResponse
}