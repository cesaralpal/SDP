package com.villaindustrias.sdpv3.home.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.polimentes.utilitieskotlin.Util
import com.polimentes.utilitieskotlin.interfaces.view.IView
import com.villaindustrias.sdpv3.R


class FirmwareFragment : Fragment(), IView {



    companion object {
        fun newInstance(): Fragment {
            return FirmwareFragment()
        }
    }

    /**
     * Método utilizado para escuchar click de los recyclers
     * @param item Elemento enviado por el recycler
     */


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_firmware, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setData()
        setListeners()
    }

    override fun setData() {
    }

    override fun setListeners() {

    }




    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {

    }

    override fun showMessage(messageResource: Any) {
        Util.showMessage(activity!!,messageResource)
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }



}


