package com.mukesh.template.ui.views.privacyPolicy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mukesh.template.commonClasses.singleClickListener.setOnSingleClickListener
import com.mukesh.template.databinding.AcceptPolicyBinding
import com.mukesh.template.databinding.SplashBinding
import com.mukesh.template.utils.mainThread
import com.mukesh.template.utils.navigateDirection
import com.mukesh.template.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class PrivacyPolicy : Fragment() {


    private var _binding: AcceptPolicyBinding? = null
    private val binding get() = _binding!!


    /**
     * On Create View
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AcceptPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickHandler()
    }


    private fun clickHandler(){
        binding.btAccept.setOnSingleClickListener {
            if (binding.cbRememberMe.isChecked){
                navigateDirection(PrivacyPolicyDirections.actionPrivacyPolicyToHome2())
            } else {
                showSnackBar("*Please accept terms condition")
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