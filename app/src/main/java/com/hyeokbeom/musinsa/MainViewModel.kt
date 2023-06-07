package com.hyeokbeom.musinsa

import androidx.lifecycle.ViewModel
import com.hyeokbeom.domain.model.Item
import com.hyeokbeom.domain.model.MainListResponse
import com.hyeokbeom.domain.usecase.MainListUseCase
import com.hyeokbeom.shared.Result
import com.hyeokbeom.shared.executeResult
import com.hyeokbeom.shared.launchOnIO

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    internal val mockMainListUseCase: MainListUseCase
) : ViewModel() {
    val mainListResult = MutableStateFlow<List<Item>?>(emptyList())

    init {
        launchOnIO { fetchData() }
    }

    /**
     * fetchData
     * [메인 화면 데이터 요청]
     * - Response Success 인 경우 list data 전달
     * - Response Failure 인 경우 null 전달
     */
    internal suspend fun fetchData(): Result<MainListResponse> = mockMainListUseCase().executeResult(
        onSuccess = { mainListResult.value = it.list },
        onFailure = { mainListResult.value = null }
    )


    /**
     * getListState
     * [ListState 확인]
     * @param lastIndex
     * @param currentIndex
     * - 기준 리스트 대비 현재 인덱스의 리스트 상태값 확인
     */
    internal fun getListState(lastIndex: Int, currentIndex: Int): ListState {
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
    object Last : ListState()    /* 검색 값이 배열의 LastIndex */
    object In : ListState()      /* 검색 값이 마지막 이내 값 */
    object Over : ListState()    /* 검색 값이 OutOfBound */
}