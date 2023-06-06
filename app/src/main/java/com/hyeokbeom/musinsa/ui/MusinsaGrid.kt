package com.hyeokbeom.musinsa.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hyeokbeom.domain.model.Good
import com.hyeokbeom.domain.model.Style
import com.hyeokbeom.musinsa.ContentType
import com.hyeokbeom.musinsa.ListState
import com.hyeokbeom.musinsa.LocalSectionInfoProvider
import com.hyeokbeom.musinsa.SectionInfoProvider

/**
 * MusinsaStyleGrid
 * [3x2 Grid 뷰 Column part]
 * A ->
 * B ->
 * C ->
 * - 버튼 클릭 이벤트 callback -> 아이템 추가
 * - 리스트 검색 후 버튼 state 변경
 */
@Composable
fun <T> MusinsaStyleGrid(goods: List<T>) {
    val localSectionInfo = LocalSectionInfoProvider.current
    val viewModel = localSectionInfo.viewModel
    val contentType = localSectionInfo.contentType

    val rowSize = contentType.rowSize

    var currentColumnSize by rememberSaveable { mutableStateOf(2) }
    var isFooterVisible by rememberSaveable { mutableStateOf(true) }

    val rows: List<List<T>> = goods.chunked(rowSize)

    LaunchedEffect(key1 = isFooterVisible) {
        localSectionInfo.footerVisibilityState.value = isFooterVisible
    }

    /* FooterClickListener 등록 */
    localSectionInfo.footerClickListener = object : SectionInfoProvider.FooterClickListener {
        override fun onClick() {
            viewModel?.getListState(rows.lastIndex, currentColumnSize)?.let {
                when (it) {
                    ListState.In -> currentColumnSize++
                    ListState.Last -> run { isFooterVisible = false }.also { currentColumnSize++ }
                    ListState.Over -> isFooterVisible = false
                }
            }
        }
    }

    Column(modifier = Modifier.padding(start = 6.dp, end = 6.dp)) {
        repeat(currentColumnSize) {
            val isLast = it == currentColumnSize - 1
            /**
             *  STYLE 컨텐츠, 첫번쨰 아이템 별도 처리
             */
            if (it == 0 && contentType == ContentType.STYLE) {
                SpannedStyleView(styles = rows[it] as List<Style>)
            } else {
                GridGoodRow(rows[it], rowSize, isLast)
            }
        }
    }
}

/**
 * SpannedStyleView
 * [Style 첫번째 열 뷰]
 * @param styles
 * AA  B
 * AA  C
 * - 첫번째 아이템의 영역 설정시 얻어진 height 값을 통해 나머지 아이템의 height 값 설정
 */
@Composable
private fun SpannedStyleView(styles: List<Style>) {
    val density = LocalDensity.current.density
    var positionedHeight by remember { mutableStateOf(0f) }

    Row(modifier = Modifier.fillMaxWidth()) {
        val firstRow = styles.first()
        AsyncImage(
            model = firstRow.thumbnailURL,
            modifier = Modifier
                .padding(2.dp)
                .clip(RoundedCornerShape(topStart = 6.dp))
                .weight(2f)                   // 스크린 대비 영역 비율
                .aspectRatio(1 / 1.5f)  // width / height 비율 설정 (1:1.5)
                .onGloballyPositioned {
                    positionedHeight = it.size.height / density
                },
            contentDescription = "2x2 상품 첫번째 이미지",
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .height(positionedHeight.dp),   // 첫번째 이미지 그려진 후 얻어진 값으로 height 설정
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            styles.subList(1, styles.size).forEach {
                val heightDp = (positionedHeight / 2).dp
                val modifier = if (it == styles.last()) {
                    Modifier
                        .height(heightDp)
                        .padding(top = 2.dp)
                } else {
                    Modifier
                        .height(heightDp)
                        .padding(bottom = 2.dp)
                        .clip(RoundedCornerShape(topEnd = 6.dp))
                }

                AsyncImage(
                    model = it.thumbnailURL,
                    modifier = modifier,
                    contentDescription = "2x2 상품 이미지",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

/**
 * GridGoodRow
 * [3x2 Grid 뷰 Row part]
 * @param list
 * @param count column count from style
 * @param isLastColumn 마지막 열 여부
 * A B C
 */
@Composable
private fun <T> GridGoodRow(list: List<T>, count: Int, isLastColumn: Boolean) {
    val localSectionInfo = LocalSectionInfoProvider.current
    val contentType = localSectionInfo.contentType
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(count) { x -> /* 3번씩 반복 */
            val isStartRow = x == 0
            val isLastRow = x == count - 1

            /**
             * 마지막 열의 데이터가 불충분(3개 미만) 인 경우, Default Item(Spacer) 으로 구성
             */
            if (list.size - 1 > x - 1) {
                when (contentType) {
                    ContentType.GRID -> GoodView(
                        good = list[x] as Good,
                        modifier = Modifier.weight(1f)
                    )
                    ContentType.STYLE -> {
                        StyleView(
                            style = list[x] as Style,
                            modifier = Modifier.weight(1f),
                            isLastColumn = isLastColumn,
                            isStartRow = isStartRow,
                            isLastRow = isLastRow
                        )
                    }
                    else -> throw Exception()
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}