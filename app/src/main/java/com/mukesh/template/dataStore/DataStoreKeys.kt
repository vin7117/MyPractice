package com.mukesh.template.dataStore

import com.mukesh.template.BuildConfig
import com.mukesh.template.networking.convertStringIntoClass
import com.mukesh.easydatastoremethods.calldatastore.CallDataStore
import com.mukesh.easydatastoremethods.getpreferencekey.getDataPreferenceKey
import com.mukesh.template.model.UserDataDC


/**
 * Data Store Keys
 * */
val AUTH_TOKEN = String.getDataPreferenceKey(BuildConfig.APPLICATION_ID.plus(".authToken"))

val LOGIN_DATA = String.getDataPreferenceKey(BuildConfig.APPLICATION_ID.plus(".loginData"))

val REMEMBER_ENABLE = Boolean.getDataPreferenceKey(BuildConfig.APPLICATION_ID.plus(".rememberEnable"))



/**
 * Get Auth Token
 * */
fun getAuthToken(authToken: (authToken : String) -> Unit){
    CallDataStore.getPreferenceData(AUTH_TOKEN){
        authToken(it ?: "")
    }
}


/**
 * Get Login Data
 * */
fun getLoginData(loginData: (loginData: UserDataDC?) -> Unit){
    CallDataStore.getPreferenceData(LOGIN_DATA){
        if (it.isNullOrEmpty()) loginData(UserDataDC())
        else loginData(it.convertStringIntoClass())
    }
}

