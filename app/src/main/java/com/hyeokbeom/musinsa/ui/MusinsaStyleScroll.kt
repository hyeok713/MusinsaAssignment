package com.hyeokbeom.musinsa.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.hyeokbeom.domain.model.Good
import com.hyeokbeom.musinsa.LocalSectionProvider
import com.hyeokbeom.musinsa.SectionProvider

/**
 * MusinsaStyleScroll
 * [스크롤 형태의 상품 뷰]
 */
@Composable
fun MusinsaStyleScroll(goods: List<Good>) {
    val scrollState = rememberScrollState()
    val localSectionProvider = LocalSectionProvider.current

    var goodsToDraw by remember { mutableStateOf(goods) }

    /* goodsToDraw 의 상태 변경시 스크롤 초기화 */
    LaunchedEffect(key1 = goodsToDraw) {
        scrollState.animateScrollTo(0)
    }

    localSectionProvider.footerClickListener = object : SectionProvider.FooterClickListener {
        override fun onClick(type: String) {
            goodsToDraw = goods.shuffled()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(state = scrollState),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        goodsToDraw.forEach {
            GoodView(it)
        }
    }
}