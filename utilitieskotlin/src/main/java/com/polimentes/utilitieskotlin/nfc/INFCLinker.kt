package com.polimentes.utilitieskotlin.nfc

/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 11/04/2018
 */
interface INFCLinker {
    fun settingModeRead()
    fun settingModeWrite(payload: String)
}