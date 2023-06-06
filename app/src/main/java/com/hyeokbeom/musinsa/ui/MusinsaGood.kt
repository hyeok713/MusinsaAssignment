package com.hyeokbeom.musinsa.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.hyeokbeom.domain.model.Good
import com.hyeokbeom.domain.model.Style
import com.hyeokbeom.shared.decimalFormat

/**
 * GridGoodItem
 * [Grid Type 상품 뷰]
 */
@Composable
@SuppressLint("ModifierParameter")
fun GoodView(good: Good = Good(),  modifier: Modifier = Modifier) = with(good) {
    ConstraintLayout(modifier = modifier.padding(4.dp)) {
        val (column, row) = createRefs()
        Column(modifier = Modifier.constrainAs(column) {}) {
            Box(contentAlignment = Alignment.BottomStart) {
                AsyncImage(
                    model = thumbnailURL,
                    contentDescription = "상품 이미지"
                )

                takeIf { good.hasCoupon }?.let { Coupon() }

            }

            MusinsaStyleText(
                text = brandName,
                style = MusinsaTextStyle.BrandName,
                modifier = Modifier.padding(top = 6.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(row) {
                    top.linkTo(column.bottom)
                    start.linkTo(column.start)
                    end.linkTo(column.end)
                    width = Dimension.fillToConstraints
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MusinsaStyleText(text = price.decimalFormat, style = MusinsaTextStyle.Price12)
            Spacer(modifier = Modifier.weight(1f))
            MusinsaStyleText(text = "${saleRate}%", style = MusinsaTextStyle.SaleRate12)
        }
    }
}

@Preview
@Composable
private fun Coupon() {
    MusinsaStyleText(
        text = " 쿠폰 ",
        style = MusinsaTextStyle.Coupon,
        modifier = Modifier
            .background(color = com.hyeokbeom.musinsa.ui.theme.Coupon)
    )
}


/**
 * StyleView
 * [Style type Grid 뷰]
 * @param style item
 * @param modifier for using weight from outer tree
 * @param isLastColumn
 * @param isStartRow
 * @param isLastRow
 * - 마지막 Row 의 첫, 마지막 Column 에 대하여 둥근 모서리 설정
 */
@Composable
@SuppressLint("ModifierParameter")
fun StyleView(
    style: Style,
    modifier: Modifier = Modifier,
    isLastColumn:Boolean,
    isStartRow: Boolean,
    isLastRow: Boolean
) = with(style) {
    val shape: Shape = when {
        (isStartRow && isLastColumn) -> RoundedCornerShape(bottomStart = 6.dp)
        (isLastRow && isLastColumn) -> RoundedCornerShape(bottomEnd = 6.dp)
        else -> RectangleShape
    }
    Box(modifier = modifier.padding(2.dp)) {
        AsyncImage(
            model = thumbnailURL,
            modifier = Modifier.clip(shape),
            contentDescription = "상품 이미지"
        )
    }
}
