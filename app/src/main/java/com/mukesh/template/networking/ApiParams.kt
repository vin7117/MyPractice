package com.mukesh.template.networking

import com.mukesh.template.model.CartsDC
import com.mukesh.template.model.ProductsDC
import com.mukesh.template.model.UserDataDC
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiParams {

    @POST(LOGIN)
    suspend fun login(
        @Body requestBody: RequestBody
    ) : Response<UserDataDC>


    @GET(PRODUCTS)
    suspend fun getAllProducts(): Response<ProductsDC>


    @GET(GET_ALL_CART)
    suspend fun getCartList(
        @Path("userId") userId: String
    ): Response<CartsDC>


    @POST(ADD_CART)
    suspend fun addToCart(
        @Body requestBody: RequestBody
    ): Response<Any>

}