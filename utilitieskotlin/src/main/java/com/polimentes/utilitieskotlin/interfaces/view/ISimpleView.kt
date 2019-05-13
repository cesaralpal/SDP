package com.polimentes.utilitieskotlin.interfaces.view

/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 08/03/2018
 */
interface ISimpleView {
    fun setData()
    fun setListeners()
    fun showMessage(messageResource: Any)
    /**
     * Oculta o muestra al usuario un dialogo de progreso
     * @param isVisible: Bandera que indica si se debe de mostrar el dialogo de progreso
     */
    fun setVisibilityProgressBar(isVisible: Boolean)
}