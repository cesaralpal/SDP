package com.polimentes.utilitieskotlin.recyclers


/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 28/03/2018
 */
interface IRecyclerListener<T> {
    fun onItemClick(item: T)

    interface Complex<T, U> {
        fun onItemClick(item: T, companion: U)
    }
}