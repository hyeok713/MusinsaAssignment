package com.hyeokbeom.domain.usecase

import com.hyeokbeom.domain.repository.MusinsaRepository
import com.hyeokbeom.domain.model.MainListResponse
import com.hyeokbeom.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MainListUseCase @Inject constructor(
    private val repository: MusinsaRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : NonParamUseCaseExecutor<MainListResponse>(dispatcher) {
    override suspend fun execute(): MainListResponse = repository.mainList()
}