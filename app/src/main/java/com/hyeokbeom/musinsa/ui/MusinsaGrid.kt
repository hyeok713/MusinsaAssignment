package com.hyeokbeom.musinsa.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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
fun <T> MusinsaStyleGrid(goods: List<T>) {
    val localSectionProvider = LocalSectionProvider.current
    val rowSize = localSectionProvider.contentType.row
    var currentColumnSize by remember { mutableStateOf(2) }

    val rows: List<List<T>> = goods.chunked(rowSize)    // 3x2 array

    val additionalGoods: MutableState<List<T>> = remember { mutableStateOf(listOf()) }

    localSectionProvider.footerClickListener = object : SectionProvider.FooterClickListener {
        override fun onClick() {
            /**
             * 버튼 클릭 이벤트 발생시 다음 아이템 검색
             */
            localSectionProvider.viewModel?.getAdditionalList(
                rows, currentColumnSize - 1
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
            GridGoodRow(rows[it], rowSize)
        }
    }
}

/**
 * GridGoodRow
 * [3x2 Grid 뷰 Row part]
 */
@Composable
private fun <T> GridGoodRow(list: List<T>, column: Int) {
    val localSectionProvider = LocalSectionProvider.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(column) { x ->
            /**
             * 마지막 열의 데이터가 불충분(3개 미만) 인 경우, Default Item 으로 구성
             */
            if (list.size - 1 > x - 1) {
                when (localSectionProvider.contentType) {
                    ContentType.GRID -> GoodView(
                        good = list[x] as Good,
                        modifier = Modifier.weight(1f)
                    )
                    ContentType.STYLE -> StyleView(
                        style = list[x] as Style,
                        modifier = Modifier.weight(1f)
                    )
                    else -> throw Exception()
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
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
    val rows: List<List<Style>> = styles.chunked(ContentType.STYLE.row)

    Column {
        /* 첫번째 열 별도 구성 */
        Row(Modifier.fillMaxWidth()) {
            val firstRow = rows.first()

            if (firstRow.size >= 2) {
                /* 첫번째 열 첫번째 아이템 */
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .aspectRatio(1f)
                        .padding(4.dp)
                ) {
                    AsyncImage(
                        model = firstRow.first().thumbnailURL,
                        contentDescription = "2x2 Style 이미지",
                    )
                }
            } else {
                Spacer(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                )
            }

            /* 첫번쨰 열 나머지 아이템 구성 */
            Column(modifier = Modifier.weight(1f)) {
                firstRow.subList(1, firstRow.size).forEach { item ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                    ) {
                        AsyncImage(
                            model = firstRow.first().thumbnailURL,
                            contentDescription = "Style 이미지",
                        )
                    }
                }
            }
        }

        /* 나머지 열 */
        rows.subList(1, rows.size).forEach { rowItems ->
            Row(Modifier.fillMaxWidth()) {
                rowItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                    ) {
                        AsyncImage(
                            model = item.thumbnailURL,
                            contentDescription = "Style 이미지",
                        )
                    }
                }
            }
        }
    }
}