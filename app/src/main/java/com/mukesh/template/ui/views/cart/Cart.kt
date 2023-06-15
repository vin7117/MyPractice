package com.mukesh.template.ui.views.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mukesh.template.commonClasses.singleClickListener.setOnSingleClickListener
import com.mukesh.template.databinding.CartBinding
import com.mukesh.template.databinding.SplashBinding
import com.mukesh.template.ui.viewModels.cart.CartVM
import com.mukesh.template.ui.views.productDetail.ProductDetailDirections
import com.mukesh.template.ui.views.splash.SplashDirections
import com.mukesh.template.utils.mainThread
import com.mukesh.template.utils.navigateBack
import com.mukesh.template.utils.navigateDirection
import com.mukesh.template.utils.showDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class Cart : Fragment() {

    private var _binding: CartBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CartVM>()


    /**
     * On Create View
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CartBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickHandler()
    }

    private fun clickHandler() {

        binding.rvShopping.adapter = viewModel.cartAdapter.apply {
            viewModel.getCartList()
        }

        binding.ivBack.setOnSingleClickListener {
            requireView().navigateBack()
        }

        binding.btBuyNow.setOnSingleClickListener {
            requireActivity().showDialog(
                title = "Buy Product",
                message = "Your products buy successfully.",
                positiveString = "Ok"
            ) {
                requireView().navigateDirection(CartDirections.actionCartToHome2())
            }
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