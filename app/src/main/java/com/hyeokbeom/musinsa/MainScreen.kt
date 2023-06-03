package com.hyeokbeom.musinsa

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
import com.hyeokbeom.domain.model.Contents
import com.hyeokbeom.domain.model.Footer
import com.hyeokbeom.domain.model.Header
import com.hyeokbeom.domain.model.Item
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
 */
@Composable
private fun HeaderView(header: Header) = with(header) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MusinsaStyleText(
            text = title,
            style = MusinsaTextStyle.Header
        )
    }
}

/**
 * Contents
 * [Contents 뷰 영역]
 */
@Composable
private fun ContentsView(contents: Contents) {

}

/**
 * Footer
 * [Footer 뷰 영역]
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