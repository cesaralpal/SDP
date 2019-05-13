package com.polimentes.utilitieskotlin.dialogs

/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 12/04/2018
 */
interface IDialogView {
    fun setData()
    fun setListeners()
    fun setBody(body: Any)
    fun setTitle(title: Any)
    fun setImage(image: Int)

}