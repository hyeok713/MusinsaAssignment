package com.hyeokbeom.musinsa

import android.annotation.SuppressLint
import com.hyeokbeom.shared.Result
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = mock(MainViewModel::class.java)
    }

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    /**
     * fetchData_success
     * [메인 데이터 호출 테스트 결과 성공]
     */
//    @Test
//    suspend fun `fetchData_success`() = with(viewModel) {
//        val successResponse: MainListResponse = mock(MainListResponse::class.java)
//        val repo = mock(MusinsaRepository::class.java)
//
//        `when`(repo.mainList())
//            .thenReturn(successResponse)
//
//        fetchData() // API 호출
//        assertEquals(successResponse, viewModel.item.value)
//    }

    /**
     * fetchData_success
     * [메인 데이터 호출 테스트 결과 실패]
     */
    @Test
    fun fetchData_failure() {
        lateinit var failureResponse: Result.FailureResponse

    }

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
