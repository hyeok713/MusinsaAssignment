package com.hyeokbeom.musinsa.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.hyeokbeom.domain.model.Good
import com.hyeokbeom.shared.decimalFormat

/**
 * GridGoodItem
 * [Grid Type 상품 뷰]
 */
@Composable
fun GridGoodItem(good: Good = Good()) = with(good) {
    Column(
        Modifier.wrapContentSize()
    ) {
        Box {
            AsyncImage(
                model = thumbnailURL,
                contentDescription = "상품 이미지"
            )

            Coupon().takeIf { good.hasCoupon }
        }

        MusinsaStyleText(text = brandName, style = MusinsaTextStyle.BrandName)

        Row (horizontalArrangement = Arrangement.SpaceBetween){
            MusinsaStyleText(text = price.decimalFormat, style = MusinsaTextStyle.Price12)
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