package com.polimentes.utilitieskotlin.di.base

import com.polimentes.utilitieskotlin.interfaces.weberrorlistener.IWebErrorListener

interface IBaseRequest<RL, WEL> {
    fun attachListeners(requestListener: RL, webErrorListener: WEL)
}