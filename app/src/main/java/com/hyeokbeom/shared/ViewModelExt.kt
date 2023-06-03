package com.hyeokbeom.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

inline fun ViewModel.launchOnMain(
    crossinline body: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(Dispatchers.Main) { body(this) }

inline fun ViewModel.launchOnIO(
    crossinline body: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(Dispatchers.IO) { body(this) }

inline fun ViewModel.launchOnDefault(
    crossinline body: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(Dispatchers.Default) { body(this) }