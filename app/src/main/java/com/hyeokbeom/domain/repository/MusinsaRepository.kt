package com.hyeokbeom.domain.repository

import com.hyeokbeom.domain.model.MainListResponse

interface MusinsaRepository {
    suspend fun mainList(): MainListResponse
}