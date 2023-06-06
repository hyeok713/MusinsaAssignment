package com.hyeokbeom.data.datasource

import com.hyeokbeom.domain.model.MainListResponse
import retrofit2.http.GET

interface MusinsaAPI {
    @GET("interview/list.json")
    suspend fun mainList(): MainListResponse
}