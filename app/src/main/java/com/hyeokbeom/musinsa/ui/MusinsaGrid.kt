package com.hyeokbeom.musinsa.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hyeokbeom.domain.model.Good
import com.hyeokbeom.domain.model.Style
import com.hyeokbeom.musinsa.ContentType
import com.hyeokbeom.musinsa.LocalSectionProvider
import com.hyeokbeom.musinsa.SectionProvider

/**
 * MusinsaStyleGrid
 * [3x2 Grid 뷰 Column part]
 */
@Composable
fun MusinsaStyleGrid(goods: List<Good>) {
    var currentColumnSize = 2
    val nestedGoods: List<List<Good>> = goods.chunked(ContentType.GRID.column)    // 3x2 array

    val additionalGoods: MutableState<List<Good>> = remember { mutableStateOf(listOf()) }

    val localSectionProvider = LocalSectionProvider.current
    localSectionProvider.footerClickListener = object : SectionProvider.FooterClickListener {
        override fun onClick(type: String) {
            /**
             * 버튼 클릭 이벤트 발생시 다음 아이템 검색
             */
            localSectionProvider.viewModel?.getAdditionalList(
                nestedGoods, currentColumnSize - 1
            )?.let { result ->
                /* set result list */
                additionalGoods.value = result.first
                /* set footer visible state in case of lastIndex */
                takeIf { result.second }?.let {
                    localSectionProvider.footerVisibilityState.value = false
                }
            }

            currentColumnSize++
        }
    }

    Column(
        modifier = Modifier.padding(start = 6.dp, end = 6.dp)
    ) {
        /** Set initial lists **/
        repeat(currentColumnSize) {
            GridGoodRow(nestedGoods[it], ContentType.GRID.column)
        }

        /** Set additional list **/
        if (additionalGoods.value.isNotEmpty()) {
            GridGoodRow(additionalGoods.value, ContentType.GRID.column)
        }
    }
}

/**
 * GridGoodRow
 * [3x2 Grid 뷰 Row part]
 */
@Composable
private fun GridGoodRow(list: List<Good>, column: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(column) { x ->
            /**
             * 마지막 열의 데이터가 불충분(3개 미만) 인 경우, Default Item 으로 구성
             */
            if (list.size - 1 > x - 1) {
                GoodView(good = list[x], modifier = Modifier.weight(1f))
            } else {
                GoodView(modifier = Modifier.weight(1f))
            }
        }
    }
}

/**
 * MusinsaStyleGrid2
 * [Grid style for type "STYLE"]
 */
@Composable
fun MusinsaStyleGrid2(styles: List<Style>) {

}


