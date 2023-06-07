package com.hyeokbeom.musinsa

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hyeokbeom.domain.model.Style
import com.hyeokbeom.musinsa.ui.StyleView
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StyleViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * asyncImage_clip
     * StyleView 이미지 적용시 last column/row 에 따른 clip 적용 테스트
     */
    @Test
    fun asyncImage_clip() {
        val isLastColumn = true
        val isStartRow = true
        val isLastRow = false

        composeTestRule.setContent {
            StyleView(
                style = Style(
                    linkURL = "",
                    thumbnailURL = "example image url"
                ),
                modifier = Modifier,
                isLastColumn = isLastColumn,
                isStartRow = isStartRow,
                isLastRow = isLastRow
            )
        }

        val asyncImageNode = composeTestRule.onNodeWithContentDescription("상품 이미지")
        val asyncImageBounds = asyncImageNode.getBoundsInRoot()

        /* 아래 조건이 주어지는 경우 clip 적용 (in actual code) */
        val expectedClipBounds: DpRect = when {
            (isStartRow && isLastColumn) -> asyncImageBounds.copy(bottom = asyncImageBounds.left - 6.dp)
            (isLastRow && isLastColumn) -> asyncImageBounds.copy(bottom = asyncImageBounds.right - 6.dp)
            else -> asyncImageBounds
        }

        assertNotEquals(isStartRow, isLastRow)                 // 해당 값은 동일하지 않아야 함
        assertNotEquals(
            asyncImageBounds,
            expectedClipBounds
        )  // clip 이 적용된 경우, Rect 의 정보가 달라야 함 (origin / copied)
    }
}