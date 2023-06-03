package com.hyeokbeom.domain.repository

import com.hyeokbeom.domain.model.InterviewResponse

interface MusinsaRepository {
    suspend fun interview(): InterviewResponse
}