package com.mukesh.template.ui.views.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mukesh.easydatastoremethods.calldatastore.CallDataStore
import com.mukesh.template.commonClasses.singleClickListener.setOnSingleClickListener
import com.mukesh.template.dataStore.REMEMBER_ENABLE
import com.mukesh.template.dataStore.getLoginData
import com.mukesh.template.databinding.LoginBinding
import com.mukesh.template.ui.viewModels.login.LoginVM
import com.mukesh.template.utils.getValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : Fragment() {


    private var _binding: LoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginVM>()


    /**
     * On Create View
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickHandler()
        CallDataStore.getPreferenceData(REMEMBER_ENABLE){
            if (it == true){
                getLoginData {
                    binding.etUsername.setText(it?.username.orEmpty())
                }
            }
        }
    }


    /**
     * Click Handler
     * */
    private fun clickHandler() {
        binding.btLogin.setOnSingleClickListener {
            viewModel.checkValidation(
                view = requireView(),
                username = binding.etUsername.getValue(),
                password = binding.etPassword.getValue(),
                isRememberEnable = binding.cbRememberMe.isChecked
            )
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