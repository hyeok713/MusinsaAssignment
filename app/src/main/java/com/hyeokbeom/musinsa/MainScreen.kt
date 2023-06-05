package com.hyeokbeom.musinsa

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.hyeokbeom.domain.model.Contents
import com.hyeokbeom.domain.model.Footer
import com.hyeokbeom.domain.model.Header
import com.hyeokbeom.domain.model.Item
import com.hyeokbeom.musinsa.ui.*
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * LocalComposition for Section
 */
internal val LocalSectionProvider = staticCompositionLocalOf { SectionProvider() }

internal class SectionProvider(val viewModel: MainViewModel? = null) {
    var footerVisibilityState = MutableStateFlow(true)

    interface FooterClickListener {
        fun onClick(type: String)
    }

    lateinit var footerClickListener: FooterClickListener
}

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
            items(list) {
                val section = SectionProvider(hiltViewModel())

                CompositionLocalProvider(LocalSectionProvider provides section) {
                    Section(it)
                }
            }
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
            val uiInfo = BannerUiInfo.create(
                screenWidthDp = LocalConfiguration.current.screenWidthDp.dp.value,
                itemWidthDp = LocalConfiguration.current.screenWidthDp.dp.value,
                parallaxOffsetFactor = .33f,
            )

            CompositionLocalProvider(LocalBannerUiInfo provides uiInfo) {
                MusinsaStyleBanner(contents.banners)
            }
        }

        ContentType.GRID.name -> {
            MusinsaStyleGrid(contents.goods)
        }

        ContentType.STYLE.name -> {
//            StylesContents(contents)
        }

        ContentType.SCROLL.name -> {
//            ScrollContents(contents)
        }
    }
}

/**
 * Footer
 * [Footer 뷰 영역]
 * @param footer
 */
@Composable
private fun FooterView(footer: Footer) {
    val footerType = FooterType.values().find { it.name == footer.type }
    val localSectionPreview = LocalSectionProvider.current
    val isFooterVisible = localSectionPreview.footerVisibilityState.collectAsState()

    require(footerType != null)

    if (isFooterVisible.value) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            OutlinedButton(
                onClick = { localSectionPreview.footerClickListener.onClick(footerType.name) },
                modifier = Modifier.fillMaxWidth(0.94f),
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.LightGray)
            ) {
                Row {
                    footer.iconURL?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = "Footer 아이콘",
                            modifier = Modifier.size(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    MusinsaStyleText(
                        text = footerType.label,
                        style = footerType.fontStyle
                    )
                }
            }
        }
    }
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