package com.mukesh.template.ui.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mukesh.template.commonClasses.singleClickListener.setOnSingleClickListener
import com.mukesh.template.databinding.HomeBinding
import com.mukesh.template.ui.viewModels.home.HomeVM
import com.mukesh.template.utils.navigateDirection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {

    private var _binding: HomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeVM>()


    /**
     * On Create View
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvShopping.adapter = viewModel.productAdapter.apply {
            viewModel.getAllProductList()
        }

        binding.ivCart.setOnSingleClickListener {
            requireView().navigateDirection(HomeDirections.actionHome2ToCart())
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