package com.hyeokbeom.musinsa

import androidx.lifecycle.ViewModel
import com.hyeokbeom.domain.model.Item
import com.hyeokbeom.domain.usecase.InterviewUseCase
import com.hyeokbeom.shared.executeResult
import com.hyeokbeom.shared.launchOnIO

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interviewUseCase: InterviewUseCase
) : ViewModel() {
    val item = MutableStateFlow<List<Item>?>(emptyList())

    init {
        fetchData()
    }

    /**
     * fetchData
     * [메인 화면 데이터 요청]
     * - Response Success 인 경우 list data 전달
     * - Response Failure 인 경우 null 전달
     */
    private fun fetchData() = launchOnIO {
        interviewUseCase().executeResult(
            onSuccess = { item.value = it.list },
            onFailure = { item.value = null }
        )
    }
}