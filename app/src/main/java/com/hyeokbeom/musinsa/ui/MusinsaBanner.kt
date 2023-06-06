@file:OptIn(ExperimentalFoundationApi::class)

package com.hyeokbeom.musinsa.ui

import android.content.res.Resources
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.hyeokbeom.domain.model.Banner
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * staticLocalComposition for Banner
 */
private val LocalBannerUiInfoProvider = staticCompositionLocalOf { BannerUiInfoProvider() }

private val currentBannerPage = MutableStateFlow(0)

private fun setCurrentPage(page: Int) {
    currentBannerPage.value = page
}

/**
 * MusinsaStyleBanner
 * [배너 컨텐츠 뷰 페이저]
 * @param banners
 */
@Composable
fun MusinsaStyleBanner(banners: List<Banner>) {
    val uiInfo = BannerUiInfoProvider.create(
        screenWidthDp = LocalConfiguration.current.screenWidthDp.dp.value,
        itemWidthDp = LocalConfiguration.current.screenWidthDp.dp.value,
        parallaxOffsetFactor = .66f,
    )

    CompositionLocalProvider(LocalBannerUiInfoProvider provides uiInfo) {
        val bannerSize = banners.size
        val numPages = Int.MAX_VALUE / bannerSize
        val startPage = numPages / 2
        val startIndex = (startPage * bannerSize) + 0

        val pagerState = rememberPagerState(initialPage = startIndex)
        val scrollDelayTimeMillis = 3000L

        /* currentBannerPage 상태값 변경시 페이지 스크롤 */
        LaunchedEffect(key1 = currentBannerPage.collectAsState().value) {
            delay(scrollDelayTimeMillis)
            pagerState.animateScrollToPage(page = pagerState.currentPage.inc())
        }

        Box {
            HorizontalPager(
                pageCount = Int.MAX_VALUE,
                modifier = Modifier.fillMaxWidth(),
                state = pagerState,
            ) { index ->
                val pageIndex = (index - startIndex).floorMod(bannerSize)
                with(banners[pageIndex]) {
                    /** Banner **/
                    BannerItem(this)
                    /* 페이지 인덱스 상태값 변경 */
                    setCurrentPage(pagerState.settledPage)
                }
            }

            BannerPagerIndicator(
                modifier = Modifier.align(Alignment.BottomEnd),
                size = bannerSize,
            )
        }
    }
}

/**
 * BannerUiInfo
 * [Parallax Scroll Banner 구현을 위한  Ui 정보]
 * @property itemWidthDp 배너 뷰 크기
 * @property xForCenteredItemDp 화면상 배너의 중간값
 * @property xForCenteredItemPx  xForCenteredItemDp to Pixel
 * @property parallaxOffsetFactor offset velocity 증/감 parameter
 * - parallaxOffsetFactor 설정값 -> 기본 속도 대비 설정값 만큼 빠르게 이동
 */
data class BannerUiInfoProvider(
    val itemWidthDp: Float = 0f,
    val xForCenteredItemDp: Float = 0f,
    val xForCenteredItemPx: Float = 0f,
    val parallaxOffsetFactor: Float = 0f
) {
    companion object {
        private val SCREEN_DENSITY = Resources.getSystem().displayMetrics.density

        fun create(
            screenWidthDp: Float,
            itemWidthDp: Float,
            parallaxOffsetFactor: Float,
        ): BannerUiInfoProvider {
            val xForCenteredItemDp = (screenWidthDp - itemWidthDp) / 2
            val xForCenteredItemPx = xForCenteredItemDp * SCREEN_DENSITY
            return BannerUiInfoProvider(
                itemWidthDp = itemWidthDp,
                xForCenteredItemDp = xForCenteredItemDp,
                xForCenteredItemPx = xForCenteredItemPx,
                parallaxOffsetFactor = parallaxOffsetFactor,
            )
        }
    }
}

/**
 * BannerItem
 * [배너 뷰]
 * @param banner
 *
 */
@Composable
fun BannerItem(banner: Banner) {
    val bannerUiInfo = LocalBannerUiInfoProvider.current
    var itemX by remember { mutableStateOf(0f) }

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (title, description) = createRefs()

        AsyncImage(
            model = banner.thumbnailURL,
            contentDescription = "배너 이미지",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { itemX = it.positionInWindow().x }
                .offset { IntOffset(x = if (itemX > 0f) 0 else -itemX.toInt(), y = 0) }
        )

        val offsetFromCenterPx = itemX - bannerUiInfo.xForCenteredItemPx

        /** Parallax Text View **/
        MusinsaStyleText(
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.bottom)
                }
                .offset {
                    IntOffset(
                        x = (offsetFromCenterPx
                                * bannerUiInfo.parallaxOffsetFactor).toInt(), y = -250
                    )
                },
            text = banner.title,
            style = MusinsaTextStyle.BannerTitle
        )

        /** Parallax Text View **/
        MusinsaStyleText(
            modifier = Modifier
                .constrainAs(description) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.bottom)
                }
                .offset {
                    IntOffset(
                        /* 위 텍스트 대비 절반 속도 */
                        x = ((offsetFromCenterPx
                                * bannerUiInfo.parallaxOffsetFactor).toInt() / 2), y = -170
                    )
                },
            text = banner.description,
            style = MusinsaTextStyle.BannerDescription
        )
    }
}

/**
 * BannerSliderIndicator
 * [배너 영역 인디케이터]
 * - 현재 구간 표시
 */
@Composable
private fun BannerPagerIndicator(modifier: Modifier = Modifier, size: Int) {
    val currentPage = currentBannerPage.collectAsState().value.floorMod(size)

    Box(
        modifier = modifier
            .background(
                color = Color.Black.copy(alpha = 0.5f),
                shape = RoundedCornerShape(topStart = 2.dp)
            )
            .padding(10.dp, 4.dp)

    ) {
        MusinsaStyleText(
            text = "${currentPage + 1} / $size",
            style = MusinsaTextStyle.BannerIndicator
        )
    }
}

/**
 * floorMod
 * [for Infinite Paging]
 */
fun Int.floorMod(max: Int): Int = when (max) {
    0 -> this
    else -> this - floorDiv(max) * max
}