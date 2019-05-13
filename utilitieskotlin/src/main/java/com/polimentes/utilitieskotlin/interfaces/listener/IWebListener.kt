package com.polimentes.utilitieskotlin.interfaces.listener

interface IWebListener {
    fun onNotInternetConnection()
    fun onWebError(message: Any)
}