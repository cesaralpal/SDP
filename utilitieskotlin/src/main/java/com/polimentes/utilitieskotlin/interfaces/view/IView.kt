package com.polimentes.utilitieskotlin.interfaces.view

import android.content.Context
import android.os.Bundle

/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 08/03/2018
 */
interface IView : ISimpleView {
    /**
     * Redirige al usuario a una nueva pantalla. Se pueden agregar banderas o parámetros para
     * realizar la redirección
     * @param classToRedirect: Clase a la cual se redirige
     * @param flags: Banderas que se agregan para realizar el redireccionamiento
     * @param params: Parametros que se agregan para realizar el redireccionamiento
     */
    fun redirectTo(classToRedirect: Class<*>,
                   flags: Array<Int>? = null,
                   params: Bundle? = null,
                   finish: Boolean? = false)
}