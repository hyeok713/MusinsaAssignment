package com.hyeokbeom.musinsa.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hyeokbeom.domain.model.Good

/**
 * MusinsaStyleGrid
 * [3x2 Grid 뷰]
 */
@Composable
fun MusinsaStyleGrid(goods: List<Good>) {
    var initSize by remember { mutableStateOf(2) }
    val chunkSize = 3
    val nestedGoods: List<List<Good>> = goods.chunked(chunkSize)

//    LaunchedEffect(key1 = onRequest) {
//        initSize++
//    }

    Column(
        modifier = Modifier.padding(start = 6.dp, end = 6.dp)
    ) {
        repeat(initSize) { y ->
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                repeat(chunkSize) { x ->
                    GridGoodItem(good = nestedGoods[y][x])
                }
            }
        }
    }
}


