package com.hyeokbeom.musinsa

import MusinsaStyleBanner
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hyeokbeom.domain.model.*
import com.hyeokbeom.musinsa.ui.MusinsaStyleText
import com.hyeokbeom.musinsa.ui.MusinsaTextStyle

/**
 * MainScreen
 * @param list List<Data>
 */
@Composable
fun MainScreen(list: List<Item>?) {
    /* list != null 인 경우 뷰 구성 */
    val scrollState = rememberLazyListState()
    list?.let {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {
            items(list) { Section(it) }
        }
    } ?: EmptyListScreen()
}

/**
 * Section
 * [list data 의 한 '구간' 을 나타 내는 단위]
 * @param item an item of list
 * - header, footer could be 'null'
 */
@Composable
private fun Section(item: Item) = with(item) {
    Column {
        /* HEADER */
        header?.let { HeaderView(it) }

        /* CONTENTS */
        ContentsView(item.contents)

        /* FOOTER */
        footer?.let { FooterView(it) }
    }
}

/**
 * Header
 * [Header 뷰 영역]
 * @param header
 * - iconURL, linkURL could be 'null'
 */
@Composable
private fun HeaderView(header: Header) = with(header) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        /* Text, Icon Block */
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MusinsaStyleText(
                text = title,
                style = MusinsaTextStyle.Header
            )

            Spacer(modifier = Modifier.width(4.dp))

            // In case of iconURL data exist
            iconURL?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "아이콘",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        linkURL?.let { MusinsaStyleText(text = "전체", style = MusinsaTextStyle.HeaderOption) }
    }
}

/**
 * Contents
 * [Contents 뷰 영역]
 * @param contents
 * - contents type 값에 따라 뷰 분류
 */
@Composable
private fun ContentsView(contents: Contents) {
    when (contents.type) {
        ContentType.BANNER.name -> {
            MusinsaStyleBanner(contents.banners)
        }

        ContentType.GRID.name -> {
            GridContents(contents)
        }

        ContentType.STYLE.name -> {
            StylesContents(contents)
        }

        ContentType.SCROLL.name -> {
            ScrollContents(contents)
        }
    }
}

/**
 * GridContents
 * [Grid 컨텐츠 뷰]
 * @param contents
 */
@Composable
private fun GridContents(contents: Contents) = with(contents) {

}

/**
 * StylesContents
 * [Style 컨텐츠 뷰]
 * @param contents
 */
@Composable
private fun StylesContents(contents: Contents) = with(contents) {

}

/**
 * ScrollContents
 * [Scroll 컨텐츠 뷰]
 * @param contents
 */
@Composable
private fun ScrollContents(contents: Contents) = with(contents) {

}


/**
 * Footer
 * [Footer 뷰 영역]
 * @param footer
 */
@Composable
private fun FooterView(footer: Footer) {

}

/**
 * EmptyListScreen
 * [Response Error 대체 화면]
 */
@Preview
@Composable
private fun EmptyListScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "상품 정보를 불러 오는 도중 문제가 생겼습니다 :(")
    }
}