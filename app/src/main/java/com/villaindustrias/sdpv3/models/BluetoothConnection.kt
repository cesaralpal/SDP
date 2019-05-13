package com.villaindustrias.sdpv3.models

import android.bluetooth.BluetoothSocket
import java.util.logging.Handler

class BluetoothConnection {

    private val handler: Handler? = null
    private val socket: BluetoothSocket? = null

    private val estado: Int = 0

    companion object {
        private val TAG = "bluetoothService"

        val ESTADO_NINGUNO = 0
        val ESTADO_CONECTADO = 1
        val ESTADO_REALIZANDO_CONEXION = 2
        val ESTADO_ATENDIENDO_PETICIONES = 3

        val MSG_LEER = 11
        val MSG_ESCRIBIR = 12
    }

}