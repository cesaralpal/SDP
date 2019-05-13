package com.polimentes.utilitieskotlin.interfaces.request

import retrofit2.HttpException

/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 11/05/2018
 */
interface IRequest {
    fun handleException(error: HttpException)
    fun handleError(code: Int, message: String)
}