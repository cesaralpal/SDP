package com.polimentes.utilitieskotlin.retrofit

import java.io.IOException

/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 08/03/2018
 */
class NoInternetException : IOException() {
    override fun getLocalizedMessage(): String {
        return "No connectivity exception"
    }

    override val message: String?
        get() = "No connectivity exception"
}