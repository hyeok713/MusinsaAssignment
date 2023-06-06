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

    /**
     * getListState
     * [ListState 확인]
     * @param lastIndex
     * @param currentIndex
     * - 기준 리스트 대비 현재 인덱스의 리스트 상태값 확인
     */

    fun getListState(lastIndex: Int, currentIndex: Int): ListState {
        return when {
            lastIndex == currentIndex -> ListState.Last
            lastIndex < currentIndex -> ListState.Over
            else -> ListState.In
        }
    }
}

/**
 * ListState
 * 특정 아이템 혹은 인덱스 검색 결과 상태값
 *
 */
sealed class ListState {
    object Last: ListState()    /* 검색 값이 배열의 LastIndex */
    object In: ListState()      /* 검색 값이 마지막 이내 값 */
    object Over: ListState()    /* 검색 값이 OutOfBound */
}