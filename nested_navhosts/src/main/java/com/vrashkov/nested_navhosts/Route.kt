package com.vrashkov.nested_navhosts

import androidx.navigation.*

const val ROOT_ROUTE = "root"
const val BASE_ROUTE = "base"
const val PRODUCT_ROUTE = "product"

sealed class Route(val link: String, val arguments: List<NamedNavArgument> = emptyList()) {

    object Base_tab_1: Route(link = "base_tab_1")
    object Base_tab_2: Route(link = "base_tab_2")
    object Base_tab_3: Route(link = "base_tab_3")

    object ProductList: Route(link = "product_list")
    object ProductDetails: Route(link = "product_details")
}