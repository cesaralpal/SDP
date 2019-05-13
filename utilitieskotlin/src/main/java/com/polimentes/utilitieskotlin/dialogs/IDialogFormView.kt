package com.polimentes.utilitieskotlin.dialogs

interface IDialogFormView {
    fun setBody(body: Any)
    fun setTitle(title: Any)
    fun setData()
    fun setListeners()
    fun validateFields(): Boolean
}