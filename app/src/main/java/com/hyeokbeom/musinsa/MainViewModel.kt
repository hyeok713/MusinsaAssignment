package com.hyeokbeom.musinsa

import androidx.lifecycle.ViewModel
import com.hyeokbeom.domain.model.InterviewResponse
import com.hyeokbeom.domain.usecase.InterviewUseCase
import com.hyeokbeom.shared.Log
import com.hyeokbeom.shared.executeResult
import com.hyeokbeom.shared.launchOnDefault
import com.hyeokbeom.shared.launchOnIO

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interviewUseCase: InterviewUseCase
) : ViewModel() {
    val data = MutableStateFlow<InterviewResponse?>(null)

    init {
        fetch()
    }

    private fun fetch() = launchOnIO {
        interviewUseCase().executeResult(
            onSuccess = {
                Log.e("성공함")
            },
            onFailure = {
                Log.e("실패함")
            }
        )
    }
}