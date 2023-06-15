package com.mukesh.template.ui.viewModels.productDetail

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.template.dataStore.getLoginData
import com.mukesh.template.model.CartsDC
import com.mukesh.template.networking.ApiParams
import com.mukesh.template.networking.CallHandler
import com.mukesh.template.networking.Repository
import com.mukesh.template.networking.getJsonRequestBody
import com.mukesh.template.ui.views.productDetail.ProductDetailDirections
import com.mukesh.template.utils.navigateDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductDetailVM @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var productId: String? = null


    fun addToCart(view: View) = getLoginData { userData ->
        viewModelScope.launch {
            repository.callApi(callHandler = object : CallHandler<Response<Any>>{
                override suspend fun sendRequest(apiInterface: ApiParams): Response<Any>  =
                    apiInterface.addToCart(requestBody = JSONObject().apply {
                        put("userId", userData?.id ?: 0)
                        put("merge", true)
                        put("products", JSONArray().apply {
                            put(JSONObject().apply {
                                put("id", productId?.toIntOrNull() ?: 0)
                                put("quantity", 1)
                            })
                        })
                    }.toString().getJsonRequestBody())

                override fun success(response: Response<Any>) {
                    view.navigateDirection(ProductDetailDirections.actionProductDetailToCart())
                }
            })
        }
    }

}