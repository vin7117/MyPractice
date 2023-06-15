package com.mukesh.template.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@Parcelize
data class CartsDC(
    @SerializedName("carts")
    val carts: List<Cart?>? = null
) : Parcelable {
    @Keep
    @Parcelize
    data class Cart(
        @SerializedName("discountedTotal")
        val discountedTotal: String? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("products")
        val products: List<Product>? = null,
        @SerializedName("total")
        val total: String? = null,
        @SerializedName("totalProducts")
        val totalProducts: String? = null,
        @SerializedName("totalQuantity")
        val totalQuantity: String? = null,
        @SerializedName("userId")
        val userId: String? = null
    ) : Parcelable {
        @Keep
        @Parcelize
        data class Product(
            @SerializedName("discountPercentage")
            val discountPercentage: String? = null,
            @SerializedName("discountedPrice")
            val discountedPrice: String? = null,
            @SerializedName("id")
            val id: Int? = null,
            @SerializedName("price")
            val price: String? = null,
            @SerializedName("quantity")
            val quantity: String? = null,
            @SerializedName("title")
            val title: String? = null,
            @SerializedName("total")
            val total: String? = null
        ) : Parcelable
    }
}