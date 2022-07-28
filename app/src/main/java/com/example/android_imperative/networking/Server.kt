package com.example.android_imperative.networking

object Server {

    init {
        System.loadLibrary("keys")
    }

    private external fun getProductionBaseUrl(): String
    private external fun getDeploymentBaseUrl(): String

    const val IS_TESTER = true
    val SERVER_DEVELOPMENT = getDeploymentBaseUrl()
    val SERVER_PRODUCTION = getProductionBaseUrl()
}