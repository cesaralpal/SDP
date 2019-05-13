package com.villaindustrias.sdpv3.home.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polimentes.utilitieskotlin.Util
import com.polimentes.utilitieskotlin.interfaces.view.IView
import com.polimentes.utilitieskotlin.recyclers.IRecyclerListener
import com.villaindustrias.sdpv3.R
import com.villaindustrias.sdpv3.eventos.ChangeFragment
import com.villaindustrias.sdpv3.home.presenter.PlaceAdapter
import com.villaindustrias.sdpv3.models.Place
import kotlinx.android.synthetic.main.fragment_location.*
import org.greenrobot.eventbus.EventBus
import kotlin.collections.ArrayList


class LocationFragment : Fragment(), IView, IRecyclerListener<Place?> {

    private var adapter: PlaceAdapter? = null
    var places: ArrayList<Place?> = ArrayList()

    companion object {
        fun newInstance(): Fragment {
            return LocationFragment()
        }
    }

    /**
     * Método utilizado para escuchar click de los recyclers
     * @param item Elemento enviado por el recycler
     */


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        places.add(Place("tienda 1","Equipo 1",0,0))
        places.add(Place("tienda 2","Equipo 2",1,1))
        places.add(Place("tienda 3","Equipo 3",2,2))
        places.add(Place("tienda 4","Equipo 4",7,3))
        places.add(Place("tienda 5","Equipo 5",0,4))
        places.add(Place("tienda 6","Equipo 6",1,5))
        places.add(Place("tienda 7","Equipo 7",1,6))

        setData()
        setListeners()
        setAdapter(places)
    }

    override fun setData() {
        setAdapter(places)
    }

    override fun setListeners() {

    }


    fun setAdapter(places: ArrayList<Place?>) {
        val layoutManager = GridLayoutManager(context, 1)
        val spacing = resources.getDimensionPixelSize(R.dimen.dimen_short)
        adapter =  PlaceAdapter(ArrayList(),this)
        adapter?.set(places)
        recyclerPlaces.layoutManager = layoutManager
        recyclerPlaces.adapter = adapter
    }
    override fun onItemClick(item: Place?) {
        val change: ChangeFragment = ChangeFragment(2)
        EventBus.getDefault().post(change)
    }

    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {

    }

    override fun showMessage(messageResource: Any) {
        Util.showMessage(activity!!,messageResource)
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }



}


