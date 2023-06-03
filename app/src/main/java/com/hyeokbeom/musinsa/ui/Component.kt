package com.hyeokbeom.musinsa.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
    Header(16, Color.Black, FontWeight.Bold),
    HeaderOption(24, Color.LightGray, FontWeight.Light),
    Footer(26, Color.Black),
    BrandName(22, Color.LightGray),
    Price(24, Color.Black),
    SaleRate(24, Color.Red),
    Banner(32, Color.White),
    Description(26, Color.White),
    Indicator(14, Color.White)
}

@Composable
fun MusinsaStyleText(
    text: String,
    style: MusinsaTextStyle
): Unit = Text(
    text = text,
    fontSize = style.fontSize.sp,
    color = style.color,
    fontWeight = style.fontWeight,
    maxLines = 1
)

