package com.hyeokbeom.musinsa.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * MusinsaTextStyle
 * [무신사 앱 내에서 사용하는 텍스트의 스타일 옵션]
 * @property fontSize
 * @property color
 * @property fontWeight Optional, Default Normal
 * @property type Optional, 타입의 정의가 필요한 경우
 */
enum class MusinsaTextStyle(
    var fontSize: Int,
    var color: Color,
    var fontWeight: FontWeight = FontWeight.Normal,
    val type: String = ""
) {
    Header(14, Color.Black, FontWeight.Bold),
    HeaderOption(12, Color.Gray, FontWeight.Light),
    BrandName(11, Color.Gray),
    Price12(12, Color.Black),
    Price13(13, Color.Black),
    SaleRate12(12, Color.Red, FontWeight(450)),
    SaleRate13(13, Color.Red, FontWeight(450)),
    Banner(32, Color.White),
    BannerTitle(16, Color.White, FontWeight.Bold),
    BannerDescription(12, Color.White),
    BannerIndicator(10, Color.White),
    Indicator(14, Color.White),
    FooterRefresh(10, Color.Black, FontWeight.SemiBold, type = "REFRESH"),
    FooterMore(10, Color.Black, FontWeight.SemiBold, type = "MORE")
}

@Composable
fun MusinsaStyleText(
    modifier: Modifier = Modifier,
    text: String,
    style: MusinsaTextStyle
): Unit = Text(
    text = text,
    modifier = modifier,
    style = TextStyle.Default,
    fontSize = style.fontSize.sp,
    color = style.color,
    fontWeight = style.fontWeight,
    maxLines = 1
)

