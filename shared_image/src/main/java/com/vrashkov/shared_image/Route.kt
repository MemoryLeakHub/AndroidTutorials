package com.vrashkov.shared_image

import androidx.navigation.*

const val BASE_ROUTE = "base"
const val PRODUCT_ROUTE = "product"

sealed class Route(val link: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object ProductList: Route(link = "product_list")
    object ProductDetails: Route(link = "product_details")
}