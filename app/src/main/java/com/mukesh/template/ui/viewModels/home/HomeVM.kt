package com.mukesh.template.ui.viewModels.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.template.commonClasses.genericAdapter.recyclerAdapter.GenericAdapter
import com.mukesh.template.commonClasses.singleClickListener.setOnSingleClickListener
import com.mukesh.template.databinding.ItemHomeBinding
import com.mukesh.template.model.Product
import com.mukesh.template.model.ProductsDC
import com.mukesh.template.networking.ApiParams
import com.mukesh.template.networking.CallHandler
import com.mukesh.template.networking.Repository
import com.mukesh.template.ui.views.home.HomeDirections
import com.mukesh.template.utils.loadImage
import com.mukesh.template.utils.navigateDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val productsList by lazy { ArrayList<Product>() }

    val productAdapter = object : GenericAdapter<ItemHomeBinding, Product>(){
        override fun onCreateView(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemHomeBinding.inflate(layoutInflater, parent, false)

        override fun onBindHolder(holder: ItemHomeBinding, dataClass: Product) {

            holder.ivProduct.loadImage(url = dataClass.images?.firstOrNull().orEmpty())
            holder.tvProductName.text = dataClass.title.orEmpty()
            holder.tvPrice.text = dataClass.price.orEmpty().ifEmpty { "0.0" }

            holder.root.setOnSingleClickListener {
                navigateDirection(HomeDirections.actionHome2ToProductDetail(productData = dataClass))
            }
        }

    }


    /**
     * Get All Product List
     * */
    fun getAllProductList() {
        viewModelScope.launch {
            repository.callApi(callHandler = object : CallHandler<Response<ProductsDC>>{
                override suspend fun sendRequest(apiInterface: ApiParams): Response<ProductsDC> {
                    return apiInterface.getAllProducts()
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun success(response: Response<ProductsDC>) {
                    productsList.clear()
                    productsList.addAll(response.body()?.products ?: emptyList())
                    productAdapter.submitList(productsList)
                    productAdapter.notifyDataSetChanged()
                }
            })
        }
    }
}