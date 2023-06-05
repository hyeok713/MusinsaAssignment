package com.hyeokbeom.musinsa

import com.hyeokbeom.musinsa.ui.MusinsaTextStyle

enum class ContentType(val row: Int = 0) {
    BANNER,
    GRID(3),
    SCROLL,
    STYLE(3)
}

enum class FooterType(
    val fontStyle: MusinsaTextStyle,
    val label: String
) {
    MORE(fontStyle = MusinsaTextStyle.FooterMore, label = "더보기"),
    REFRESH(fontStyle = MusinsaTextStyle.FooterRefresh, label = "새로운 추천"),
}
