package com.hyeokbeom.musinsa.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.hyeokbeom.domain.model.Good
import com.hyeokbeom.shared.decimalFormat

/**
 * GridGoodItem
 * [Grid Type 상품 뷰]
 */
@Composable
fun GridGoodItem(good: Good = Good()) = with(good) {
    ConstraintLayout(modifier = Modifier.padding(4.dp)) {
        val (column, row) = createRefs()
        Column(modifier = Modifier.constrainAs(column) {}) {
            Box {
                AsyncImage(
                    model = thumbnailURL,
                    contentDescription = "상품 이미지"
                )

                Coupon().takeIf { good.hasCoupon }
            }

            MusinsaStyleText(text = brandName, style = MusinsaTextStyle.BrandName)
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

/**
 * ScrollGoodItem
 * [Scroll Type 상품 뷰]
 */
@Composable
fun ScrollGoodItem(good: Good) = with(good) {

}

@Composable
fun Coupon() {

}