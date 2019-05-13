package com.villaindustrias.sdpv3.models

import android.bluetooth.BluetoothSocket
import java.io.InputStream
import java.io.OutputStream

private class HilodeConexion : Thread() {
    private val socket: BluetoothSocket? = null         // Socket
    private val inputStream: InputStream? = null    // Flujo de entrada (lecturas)
    private val outputStream: OutputStream? = null   // Flujo de salida (escrituras)

}