package com.villaindustrias.sdpv3.home.interfaces

import android.support.v4.app.Fragment
import com.polimentes.utilitieskotlin.interfaces.view.IView

interface IFirmwareFragment {
    interface View : IView{
        fun changeFragment(fragment: Fragment, addToStack: Boolean, tag: String)

    }

}