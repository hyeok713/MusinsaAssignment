package com.hyeokbeom.musinsa.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
fun GoodView(
    good: Good = Good(),
    modifier: Modifier = Modifier
) = with(good) {
    ConstraintLayout(modifier = modifier.padding(4.dp)) {
        val (column, row) = createRefs()
        Column(modifier = Modifier.constrainAs(column) {}) {
            Box (contentAlignment = Alignment.BottomStart){
                AsyncImage(
                    model = thumbnailURL,
                    contentDescription = "상품 이미지"
                )

                takeIf { good.hasCoupon }?.let { Coupon() }

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