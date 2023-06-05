package com.hyeokbeom.musinsa

import com.hyeokbeom.musinsa.ui.MusinsaTextStyle

enum class ContentType {
    BANNER, GRID, SCROLL, STYLE
}

enum class FooterType(
    val fontStyle: MusinsaTextStyle,
    val label: String
) {
    MORE(fontStyle = MusinsaTextStyle.FooterMore, label = "더보기"),
    REFRESH(fontStyle = MusinsaTextStyle.FooterRefresh, label = "새로운 추천"),
}
