package com.polimentes.utilitieskotlin.recyclers

/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 22/06/2018
 */
interface IRecyclerEntry<T> {
    fun addItem(item: T)
    fun addList(list: ArrayList<T>)
    fun deleteItem(position: Int)

}