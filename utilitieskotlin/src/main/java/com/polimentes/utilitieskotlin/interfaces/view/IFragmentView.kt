package com.polimentes.utilitieskotlin.interfaces.view

import android.support.v4.app.Fragment


interface IFragmentView : ISimpleView {
    fun changeFragment(fragment: Fragment, addToStack: Boolean, tag: String)
}