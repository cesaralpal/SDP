package com.polimentes.utilitieskotlin.interfaces.weberrorlistener


interface IWebErrorListener {
    fun handleError(code: Int, message: String?)
    fun handleException(code: Int, message: String?)
    fun onTimeoutError(message: String?)
    fun onNotReachableServer(message: String?)
    fun onNotInternetConnection(message: String?)
    fun onCustomError(code: Int, message: String)
    fun onUndefinedError(code: Int, message: String?)
}