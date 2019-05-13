package com.polimentes.utilitieskotlin.recyclers

import android.bluetooth.BluetoothAdapter
import android.view.View
import android.widget.SimpleAdapter

interface IRecyclerPosition {
    fun getCurrentItemPosition(view: View?, position: Int)
}