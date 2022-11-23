package com.vrashkov.shared_image

import com.vrashkov.androidtutorials.R

data class ProductsData(
    val productList: List<Int> = listOf(
        R.drawable.bags_2, R.drawable.bags_3,
        R.drawable.shoe_1, R.drawable.shoe_2, R.drawable.shoe_3, R.drawable.shoe_4,
        R.drawable.bags_2, R.drawable.bags_3,
        R.drawable.shoe_1, R.drawable.shoe_2, R.drawable.shoe_3, R.drawable.shoe_4
    )
)
