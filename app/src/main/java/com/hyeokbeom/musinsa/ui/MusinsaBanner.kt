import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hyeokbeom.domain.model.Banner
import com.hyeokbeom.musinsa.ui.MusinsaStyleText
import com.hyeokbeom.musinsa.ui.MusinsaTextStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

private val currentBannerPage = MutableStateFlow(Int.MAX_VALUE / 2)

private fun setCurrentPage(page: Int) {
    currentBannerPage.value = page
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusinsaStyleBanner(banners: List<Banner>) {
    val bannerSize = banners.size
    val numPages = Int.MAX_VALUE / bannerSize
    val startPage = numPages / 2
    val startIndex = (startPage * bannerSize) + 0

    val pagerState = rememberPagerState(initialPage = startIndex)

    /* currentBannerPage 상태값 변경시 3초 후 페이지 스크롤 */
    LaunchedEffect(key1 = currentBannerPage.collectAsState().value) {
        delay(3000)
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
                val thumbnailUrl = this.thumbnailURL
                val title = this.title
                val description = this.description

                Box {
                    AsyncImage(
                        model = thumbnailUrl,
                        contentDescription = "배너 이미지",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillWidth,
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MusinsaStyleText(text = title, style = MusinsaTextStyle.BannerTitle)

                        Spacer(modifier = Modifier.height(4.dp))

                        MusinsaStyleText(
                            text = description,
                            style = MusinsaTextStyle.BannerDescription
                        )
                    }
                }

                /* 적용된 페이지 상태값 변경 */
                setCurrentPage(pagerState.settledPage)
            }
        }

        BannerPagerIndicator(
            modifier = Modifier.align(Alignment.BottomEnd),
            size = bannerSize,
        )
    }
}

/**
 * BannerSliderIndicator
 * [배너 영역 인디케이터]
 * - 현재 구간 표시
 */
@Composable
private fun BannerPagerIndicator(
    modifier: Modifier = Modifier,
    size: Int,
) {
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