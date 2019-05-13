package com.polimentes.utilitieskotlin.di.base

interface IBasePresenter<V> {
    fun attachView(view: V)
    fun detachView()
}