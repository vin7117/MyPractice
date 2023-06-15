package com.mukesh.template.ui.views.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mukesh.easydatastoremethods.calldatastore.CallDataStore
import com.mukesh.template.dataStore.REMEMBER_ENABLE
import com.mukesh.template.databinding.SplashBinding
import com.mukesh.template.utils.mainThread
import com.mukesh.template.utils.navigateDirection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class Splash : Fragment() {

    private var _binding: SplashBinding? = null
    private val binding get() = _binding!!


    /**
     * On Create View
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashBinding.inflate(inflater, container, false)
        return binding.root
    }


    /**
     * On Resume
     * */
    override fun onResume() {
        super.onResume()
        mainThread {
            delay(2000)
            requireView().navigateDirection(SplashDirections.actionSplashToLogin())
//            getAuthToken {
//                if (it.isNullOrEmpty())
//                    requireView().navigateDirection(SplashDirections.actionSplashToLogin())
//                else
//                    requireView().navigateDirection(SplashDirections.actionSplashToHome2())
//            }
            CallDataStore.getPreferenceData(REMEMBER_ENABLE) {
                if (it == true)
                    requireView().navigateDirection(SplashDirections.actionSplashToHome2())
                else
                    requireView().navigateDirection(SplashDirections.actionSplashToLogin())
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