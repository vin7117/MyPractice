package com.mukesh.template.networking

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.mukesh.template.controller.Controller
import com.mukesh.template.databinding.ProgressLoaderBinding
import com.mukesh.template.utils.Logger
import com.mukesh.template.utils.STRING_ALIAS
import com.mukesh.template.utils.hideSoftKeyBoard
import com.mukesh.template.utils.ioDispatcher
import com.mukesh.template.utils.mainThread
import com.mukesh.template.utils.showSnackBar
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiInterface: ApiParams
) {

    private val mainDispatcher by lazy { Dispatchers.Main }
    private var alertDialog: AlertDialog? = null


    /**
     * Call Api
     * */
    suspend fun <T> callApi(
        loader: Boolean = true,
        callHandler: CallHandler<T>
    ) {

        /**
         * Hide Soft Keyboard
         * */
        (Controller.context?.get() as Activity).let { it.hideSoftKeyBoard() }


        /**
         * Coroutine Exception Handler
         * */
        val coRoutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            mainThread {
                throwable.message.let {
                    hideLoader()
                    callHandler.error(it.orEmpty())
                }
            }
        }


        /**
         * No Internet Connection Handler
         * */
        if (!hasNetwork()) {
            Controller.context?.get()?.getString(STRING_ALIAS.no_internet_connection)?.let {
                showSnackBar(it)
                callHandler.error(it)
            }
            return
        }


        /**
         * Call Api
         * */
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler + Job()).launch {
            flow {
                emit(callHandler.sendRequest(apiInterface = apiInterface) as Response<*>)
            }.flowOn(ioDispatcher)
                .retryWhen { cause, attempt ->
                    Logger.error("NetworkRetry", "Retry.......  Cause:$cause, Attempt:$attempt")
                    (attempt < HttpStatusCode.RETRY_COUNT) && (cause is IOException)
                }.onStart {
                    callHandler.loading()
                    withContext(mainDispatcher) {
                        if (loader) Controller.context?.get()?.showLoader()
                    }
                }.catch { error ->
                    withContext(mainDispatcher) {
                        hideLoader()
                        showSnackBar(error.localizedMessage.orEmpty())
                        callHandler.error(error.localizedMessage.orEmpty())
                    }
                }.collect { response ->
                    withContext(mainDispatcher) {
                        hideLoader()
                        if (response.isSuccessful)
                            callHandler.success(response as T)
                        else
                            response.errorBody()?.string()
                                ?.let {
                                    callHandler.error(it)
                                    showSnackBar(it)
                                }
                    }
                }
        }
    }


    /**
     * Show Loader
     * */
    private fun Context.showLoader() {
        if (alertDialog == null) {
            val alert = AlertDialog.Builder(this)
            val binding = ProgressLoaderBinding.inflate(LayoutInflater.from(this), null, false)
            alert.setView(binding.root)
            alert.setCancelable(false)
            alertDialog = alert.create()
            alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog?.show()
        }
    }


    /**
     * Hide Loader
     * */
    private fun hideLoader() {
        alertDialog?.dismiss()
        alertDialog = null
    }

}