package com.hyeokbeom.musinsa

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * staticLocalComposition for Section
 */
internal val LocalSectionInfoProvider = staticCompositionLocalOf { SectionInfoProvider() }

/**
 * SectionInfoProvider
 * [Section 정보 LocalCompositionProvider]
 * @property viewModel
 * @property lazyListState
 */
internal class SectionInfoProvider(
    val viewModel: MainViewModel? = null,
    val lazyListState: LazyListState? = null
) {
    lateinit var contentType: ContentType
    lateinit var footerClickListener: FooterClickListener

    var footerVisibilityState = MutableStateFlow(true)

    /**
     * FooterClickListener
     * Section 내 Footer 의 클릭 이벤트 Interface
     */
    interface FooterClickListener {
        fun onClick()
    }
}

/**
 * MainScreen
 * @param list List<Data>
 */
@Composable
fun MainScreen(list: List<Item>?) {
    val lazyListState = rememberLazyListState()
    list?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            state = lazyListState,
        ) {
            items(list) { item ->
                val type = ContentType.values().find { it.name == item.contents.type }
                    ?: throw Exception("Type Not Defined")
                val section = SectionInfoProvider(
                    viewModel = hiltViewModel(),
                    lazyListState = lazyListState
                ).apply { contentType = type }

                CompositionLocalProvider(LocalSectionInfoProvider provides section) {
                    Section(item)
                }
            }
        }
    } ?: EmptyListScreen() /* 데이터 없는 경우 */
}

/**
 * Section
 * [list data 의 한 '구간' 을 나타 내는 뷰]
 * @param item
 * - header, footer Optional
 */
@Composable
private fun Section(item: Item) = with(item) {
    Column {
        /** HEADER **/
        header?.let { HeaderView(it) }

        /** CONTENTS **/
        ContentsView(item.contents)

        /** FOOTER **/
        footer?.let { FooterView(it) }
    }
}

/**
 * Header
 * [Header 뷰 영역]
 * @param header
 * - iconURL, linkURL Optional
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
 * - contentType 값에 따라 뷰 적용
 */
@Composable
private fun ContentsView(contents: Contents) {
    when (LocalSectionInfoProvider.current.contentType) {
        ContentType.BANNER -> MusinsaStyleBanner(contents.banners)
        ContentType.GRID -> MusinsaStyleGrid(contents.goods)
        ContentType.STYLE -> MusinsaStyleGrid(contents.styles)
        ContentType.SCROLL -> MusinsaStyleScroll(contents.goods)
    }
}

/**
 * Footer
 * [Footer 뷰 영역]
 * @param footer
 */
@Composable
private fun FooterView(footer: Footer) {
    val localSectionInfo = LocalSectionInfoProvider.current

    val footerType = FooterType.values().find { it.name == footer.type }
    val isFooterVisible: State<Boolean> = localSectionInfo.footerVisibilityState.collectAsState()

    require(footerType != null)

    if (isFooterVisible.value) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            OutlinedButton(
                onClick = {
                    /* local 로 전달 (listener callback in local) */
                    localSectionInfo.footerClickListener.onClick()
                },
                modifier = Modifier.fillMaxWidth(0.90f),
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