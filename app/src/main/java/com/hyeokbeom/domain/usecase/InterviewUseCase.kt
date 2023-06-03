package com.hyeokbeom.domain.usecase

import com.hyeokbeom.domain.repository.MusinsaRepository
import com.hyeokbeom.domain.model.InterviewResponse
import com.hyeokbeom.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class InterviewUseCase @Inject constructor(
    private val repository: MusinsaRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : NonParamUseCaseExecutor<InterviewResponse>(dispatcher) {
    override suspend fun execute(): InterviewResponse = repository.interview()
}