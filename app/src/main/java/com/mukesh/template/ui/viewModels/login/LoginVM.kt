package com.mukesh.template.ui.viewModels.login

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.easydatastoremethods.calldatastore.CallDataStore
import com.mukesh.template.dataStore.AUTH_TOKEN
import com.mukesh.template.dataStore.LOGIN_DATA
import com.mukesh.template.dataStore.REMEMBER_ENABLE
import com.mukesh.template.model.UserDataDC
import com.mukesh.template.networking.ApiParams
import com.mukesh.template.networking.CallHandler
import com.mukesh.template.networking.Repository
import com.mukesh.template.networking.convertGsonString
import com.mukesh.template.networking.getJsonRequestBody
import com.mukesh.template.ui.views.login.LoginDirections
import com.mukesh.template.utils.isRequired
import com.mukesh.template.utils.navigateDirection
import com.mukesh.template.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    /**
     * CHeck Validation
     * */
    fun checkValidation(view: View, isRememberEnable: Boolean ,username: String, password: String) {
        when {
            username.isRequired() -> showSnackBar("*Please enter username.")

            password.isRequired() -> showSnackBar("*please enter password")

            else -> view.login(isRememberEnable, username, password)
        }
    }


    /**
     * Login
     * */
    private fun View.login(isRememberEnable: Boolean, username: String, password: String) {
        viewModelScope.launch {
            repository.callApi(callHandler = object : CallHandler<Response<UserDataDC>> {
                override suspend fun sendRequest(apiInterface: ApiParams) =
                    apiInterface.login(requestBody = JSONObject().apply {
                        put("username", username)
                        put("password", password)
                    }.toString().getJsonRequestBody())

                override fun success(response: Response<UserDataDC>) {
                    CallDataStore.storeData(AUTH_TOKEN, response.body()?.token.orEmpty())
                    CallDataStore.storeData(
                        LOGIN_DATA,
                        response.body()?.convertGsonString().orEmpty()
                    )
                    CallDataStore.storeData(REMEMBER_ENABLE, isRememberEnable)
                    navigateDirection(LoginDirections.actionLoginToPrivacyPolicy())
                }
            })
        }
    }

}