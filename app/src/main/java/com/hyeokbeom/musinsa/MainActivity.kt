package com.hyeokbeom.musinsa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.hyeokbeom.domain.model.Item
import com.hyeokbeom.musinsa.ui.theme.MusinsaTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val item: State<List<Item>?> = viewModel.mainListResult.collectAsState()

            MusinsaTheme {
                MainScreen(item.value)
            }
        }
    }
}
