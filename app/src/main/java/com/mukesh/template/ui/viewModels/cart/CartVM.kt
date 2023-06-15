package com.mukesh.template.ui.viewModels.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.template.commonClasses.genericAdapter.recyclerAdapter.GenericAdapter
import com.mukesh.template.dataStore.getLoginData
import com.mukesh.template.databinding.ItemCartBinding
import com.mukesh.template.databinding.ItemHomeBinding
import com.mukesh.template.model.CartsDC
import com.mukesh.template.model.ProductsDC
import com.mukesh.template.networking.ApiParams
import com.mukesh.template.networking.CallHandler
import com.mukesh.template.networking.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CartVM @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val cartList by lazy { ArrayList<CartsDC.Cart.Product>() }

    val cartAdapter = object : GenericAdapter<ItemCartBinding, CartsDC.Cart.Product>() {
        override fun onCreateView(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemCartBinding.inflate(layoutInflater, parent, false)

        override fun onBindHolder(holder: ItemCartBinding, dataClass: CartsDC.Cart.Product) {
            holder.tvProductName.text = dataClass.title.orEmpty()
            holder.tvPrice.text = "Price:- ${dataClass.price.orEmpty().ifEmpty { "0.0" }}"
        }

    }


    /**
     * Get All Product List
     * */
    fun getCartList() = getLoginData { userData ->
        viewModelScope.launch {
            repository.callApi(callHandler = object : CallHandler<Response<CartsDC>> {
                override suspend fun sendRequest(apiInterface: ApiParams): Response<CartsDC> {
                    return apiInterface.getCartList(userId = userData?.id.toString())
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun success(response: Response<CartsDC>) {
                    cartList.clear()
                    cartList.addAll(response.body()?.carts?.firstOrNull()?.products ?: emptyList())
                    cartAdapter.submitList(cartList)
                    cartAdapter.notifyDataSetChanged()
                }
            })
        }
    }

}