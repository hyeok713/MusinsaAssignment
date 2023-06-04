package com.hyeokbeom.shared

import java.text.DecimalFormat

val Int.decimalFormat: String
    get() = DecimalFormat("#,###").format(this)

