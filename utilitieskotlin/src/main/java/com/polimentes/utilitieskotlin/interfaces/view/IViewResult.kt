package com.polimentes.utilitieskotlin.interfaces.view

import android.content.Context
import android.content.Intent
import android.os.Bundle

interface IViewResult : IView {
    /**
     * Redirige al usuario a una nueva pantalla. Se pueden agregar banderas o parámetros para
     * realizar la redirección
     * @param context: Contexto desde donde se realiza la redirección
     * @param classToRedirect: Clase a la cual se redirige
     * @param requestCode: Codigo de reconocimiento
     * @param params: Parametros que se agregan para realizar el redireccionamiento
     */
    fun redirectToResult(classToRedirect: Class<*>,
                         requestCode: Int,
                         params: Bundle? = null)

    fun setResultActivity(resultCode: Int, data: Intent? = null)
}