package com.hyeokbeom.musinsa.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * MusinsaTextStyle
 * [무신사 앱 내에서 사용하는 텍스트의 스타일 옵션]
 */
enum class MusinsaTextStyle(
    var fontSize: Int,
    var color: Color,
    var fontWeight: FontWeight = FontWeight.Normal
) {
    Header(14, Color.Black, FontWeight.Bold),
    HeaderOption(12, Color.Gray, FontWeight.Light),
    Footer(26, Color.Black),
    BrandName(11, Color.Gray),
    Price12(12, Color.Black),
    Price13(13, Color.Black),
    SaleRate12(12, Color.Red),
    SaleRate13(13, Color.Red),
    Banner(32, Color.White),
    BannerTitle(16, Color.White, FontWeight.Bold),
    BannerDescription(12, Color.White),
    BannerIndicator(10, Color.White),
    Indicator(14, Color.White)
}

@Composable
fun MusinsaStyleText(
    modifier: Modifier = Modifier,
    text: String,
    style: MusinsaTextStyle
): Unit = Text(
    text = text,
    modifier = modifier,
    fontSize = style.fontSize.sp,
    color = style.color,
    fontWeight = style.fontWeight,
    maxLines = 1
)

