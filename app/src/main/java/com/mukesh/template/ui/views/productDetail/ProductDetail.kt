package com.mukesh.template.ui.views.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mukesh.template.commonClasses.singleClickListener.setOnSingleClickListener
import com.mukesh.template.databinding.ProductDetailBinding
import com.mukesh.template.ui.viewModels.productDetail.ProductDetailVM
import com.mukesh.template.utils.loadImage
import com.mukesh.template.utils.navigateBack
import com.mukesh.template.utils.navigateDirection
import com.mukesh.template.utils.showDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetail : Fragment() {

    private var _binding: ProductDetailBinding? = null
    private val binding get() = _binding!!
    private val navArgs by navArgs<ProductDetailArgs>()
    private val viewModel by viewModels<ProductDetailVM>()


    /**
     * On Create View
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickHandler()
        setUpUI()
    }


    private fun clickHandler() {
        binding.ivBack.setOnClickListener {
            requireView().navigateBack()
        }

        binding.btAddToCart.setOnSingleClickListener {
            viewModel.addToCart(view = requireView())
        }

        binding.btBuyNow.setOnSingleClickListener {
            requireActivity().showDialog(
                title = "Buy Product",
                message = "You product buy successfully.",
                positiveString = "Ok"
            ) {
                requireView().navigateDirection(ProductDetailDirections.actionProductDetailToHome2())
            }
        }
    }


    /**
     * Set Up UI
     * */
    private fun setUpUI() {
        navArgs.productData?.let {
            viewModel.productId = it.id.toString()
            binding.ivProduct.loadImage(url = it.images?.firstOrNull().orEmpty())
            binding.tvProductName.text = it.title.orEmpty()
            binding.tvPrice.text = it.price.orEmpty().ifEmpty { "0.0" }
            binding.tvDescription.text = it.description.orEmpty()
        }
    }


    /**
     * On Destroy View
     * */
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}