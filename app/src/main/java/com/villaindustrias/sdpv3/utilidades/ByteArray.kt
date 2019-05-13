package com.villaindustrias.sdpv3.utilidades

fun ByteArray.toHex() = joinToString("") { String.format("%02X", (it.toInt() and 0xff)) }