package com.vrashkov.navigation_values

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.*
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val BASE_ROUTE = "base"
const val PRODUCT_ROUTE = "product"


data class ProductParameters(
    val id: Int = -1
) {
//    override fun toString(): String {
//        return Gson().toJson(this)
//    }
}

class ProductParametersType : NavType<ProductParameters>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): ProductParameters? {
        return  bundle.getString(key)?.let { parseValue(it) }
    }
    override fun parseValue(value: String): ProductParameters {
        return Gson().fromJson(value, ProductParameters::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: ProductParameters) {
        bundle.putString(key, Gson().toJson(value))
    }
    override fun serializeAsValue(value: ProductParameters): String {
        return Gson().toJson(value)
    }
}

object NavRouteName {
    const val product_list = "product_list"
    const val product_details = "product_details"
}
object NavArguments {
    const val product_details_parameters = "product_details_parameters"
}

sealed class Route(val link: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object ProductList: Route(link = "${NavRouteName.product_list}")
    object ProductDetails: Route(
        link = "${NavRouteName.product_details}/{${NavArguments.product_details_parameters}}",
        arguments = listOf(
            navArgument(
                name = NavArguments.product_details_parameters
            ) {
                type = ProductParametersType()
            }
        )
    )
}