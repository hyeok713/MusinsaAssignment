package com.hyeokbeom.data.repository

import com.hyeokbeom.data.datasource.MusinsaAPI
import com.hyeokbeom.domain.repository.MusinsaRepository
import javax.inject.Inject

class MusinsaRepositoryImpl @Inject constructor(
    private val api: MusinsaAPI,
) : MusinsaRepository {
    override suspend fun mainList() = api.mainList()
}