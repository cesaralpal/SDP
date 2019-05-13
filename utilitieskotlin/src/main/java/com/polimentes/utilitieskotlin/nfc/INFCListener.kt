package com.polimentes.utilitieskotlin.nfc

import com.polimentes.utilitieskotlin.enums.Status

/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 28/02/2018
 */
interface INFCListener {
    fun onNFCDataRetrived(mac: String, message: String)
    fun onNFCDataWrote(status: Status)
}