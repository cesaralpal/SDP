package com.villaindustrias.sdpv3.utilidades

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Utilities {



    fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("EEEE dd 'de' MMMM")
        return dateFormat.format(date).capitalize()
    }

    fun formatDateTwo(date: Date): String {
        val dateFormat = SimpleDateFormat("dd 'de' MMMM YYYY")
        return dateFormat.format(date).capitalize()
    }

    fun formatVolts(volts:Int):String{
        val voltFormat = DecimalFormat("###.#")
        return voltFormat.format(volts)
    }

    fun formatVoltstwo(volts:Int):String{
        val voltFormat = DecimalFormat("##.#")
        return voltFormat.format(volts)
    }

    fun formatTemperature(temperature:Int):String{
        val voltFormat = DecimalFormat("##.#")
        return voltFormat.format(temperature)
    }
}