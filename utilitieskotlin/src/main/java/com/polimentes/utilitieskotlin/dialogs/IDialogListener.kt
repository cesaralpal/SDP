package com.polimentes.utilitieskotlin.dialogs


/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 12/04/2018
 */
interface IDialogListener {
    interface Simple {
        fun onAcceptClickListener(tag: String)
        fun onDialogCanceled(tag: String)
    }

    interface InputField {
        fun onDataRetrived(data: ArrayList<String>, tag: String)
        fun onDialogCanceled(tag: String)
    }

}