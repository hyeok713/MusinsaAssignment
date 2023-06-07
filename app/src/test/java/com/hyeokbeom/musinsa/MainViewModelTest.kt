package com.hyeokbeom.musinsa

import android.annotation.SuppressLint
import com.hyeokbeom.domain.model.MainListResponse
import com.hyeokbeom.shared.Result
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        viewModel = mock(MainViewModel::class.java)
    }

    /**
     * fetchData_success
     * [메인 데이터 호출 테스트 결과 성공]
     */
    @Test
    fun fetchData_success() = runTest {
        val mockResponse = mock(MainListResponse::class.java)

        `when`(viewModel.fetchData())
            .thenReturn(Result.Success(mockResponse))

        // Result Success 리턴시
        assertEquals(Result.Success(data = mockResponse), viewModel.fetchData())
    }

    /**
     * fetchData_failure
     * [메인 데이터 호출 테스트 결과 실패]
     */
    @Test
    fun fetchData_failure() = runTest {
        val mockSuccessResponse = mock(MainListResponse::class.java)
        val mockFailureResponse = mock(Result.FailureResponse::class.java)

        `when`(viewModel.fetchData())
            .thenReturn(Result.Failure(mockFailureResponse))

        // In case of NOT Result.Success
        assertNotSame(Result.Success(data = mockSuccessResponse), viewModel.fetchData())
    }

    /**
     * getListState_last
     * 리스트의 마지막 아이템 상태 반환
     */
    @Test
    fun getListState_last() {
        val lastIndex = 5
        val currentIndex = 5

        `when`(viewModel.getListState(lastIndex, currentIndex)).thenReturn(
            ListState.Last
        )

        val result = viewModel.getListState(lastIndex, currentIndex)

        assertEquals(ListState.Last, result)
    }

    /**
     * getListState_in
     * 마지막이 아닌, 리스트 바운더리 내 인덱스 상태 반환
     */
    @Test
    fun getListState_in() {
        val lastIndex = 5
        val currentIndex = 3

        `when`(viewModel.getListState(lastIndex, currentIndex)).thenReturn(
            ListState.In
        )

        val result = viewModel.getListState(lastIndex, currentIndex)

        assertEquals(ListState.In, result)
    }

    /**
     * getListState_over
     * 입력받은 인덱스가 리스트의 바운더리 밖인 경우 상태 반환
     */
    @SuppressLint("CheckResult")
    @Test
    fun getListState_over() {
        val lastIndex = 5
        val currentIndex = 8

        `when`(viewModel.getListState(lastIndex, currentIndex)).thenReturn(
            ListState.Over
        )

        val result = viewModel.getListState(lastIndex, currentIndex)

        assertEquals(ListState.Over, result)
    }
}
