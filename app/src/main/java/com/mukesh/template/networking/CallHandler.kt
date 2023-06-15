package com.mukesh.template.networking

import com.mukesh.template.utils.Logger
import com.mukesh.template.utils.showSnackBar

/**
 * Call Handler
 * There is a three methods
 * send request , success , error
 * */
fun interface CallHandler<T> {

    /**
     * This Method for call a send
     * request with api interface
     * */
    suspend fun sendRequest(apiInterface: ApiParams): T


    /**
     * This method is for call
     * when api start and show loader
     * */
    fun loading(){
        Logger.debug("API_LOADING","API Loading Start..............")
    }


    /**
     * This method is for get
     * a success response
     * */
    fun success(response: T){
        Logger.debug("API_SUCCESS","API dats is ----> $response")
    }

    /**
     * This method is for
     * get a error messages
     * */
    fun error(message: String){
        showSnackBar(message)
    }

}