package com.vrashkov.nested_navhosts

import android.os.Bundle
import androidx.navigation.*
import com.google.gson.Gson

const val ROOT_ROUTE = "root"
const val BASE_ROUTE = "base"
const val PRODUCT_ROUTE = "product"

enum class AnimalsTab(val index: Int, val tabName: String) {
    DOG(0, "Dog"),
    CAT(1, "Cat"),
    BIRD(2, "Bird")
}

data class AnimalsParameters(
    val tab: AnimalsTab = AnimalsTab.DOG
)

val AnimalsParametersType: NavType<AnimalsParameters?> = object : NavType<AnimalsParameters?>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): AnimalsParameters? {
        return  bundle.getString(key)?.let { parseValue(it) }
    }
    override fun parseValue(value: String): AnimalsParameters {
        return Gson().fromJson(value, AnimalsParameters::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: AnimalsParameters?) {
        bundle.putString(key, Gson().toJson(value))
    }
    override fun serializeAsValue(value: AnimalsParameters?): String {
        return Gson().toJson(value)
    }
}

object NavRouteName {
    const val product_list = "product_list"
    const val product_details = "product_details"

    const val tab_1 = "base_tab_1"
    const val tab_2 = "base_tab_2"
    const val tab_3 = "base_tab_3"

}
object NavArguments {
    const val tab_3_animals = "tab_3_animals"
}

sealed class Route(val link: String, val arguments: List<NamedNavArgument> = emptyList()) {

    object Base_tab_1: Route(link = NavRouteName.tab_1)
    object Base_tab_2: Route(link = NavRouteName.tab_2)
    object Base_tab_3: Route(
        link = "${NavRouteName.tab_3}/{${NavArguments.tab_3_animals}}",
        arguments = listOf(
            navArgument(
                name = NavArguments.tab_3_animals
            ) {
                type = AnimalsParametersType
            }
        )
    )

    object ProductList: Route(link = NavRouteName.product_list)
    object ProductDetails: Route(link = NavRouteName.product_details)
}