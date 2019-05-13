package com.villaindustrias.sdpv3.home.view

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.polimentes.utilitieskotlin.Util
import com.polimentes.utilitieskotlin.interfaces.view.IMapView
import com.polimentes.utilitieskotlin.interfaces.view.IView
import com.villaindustrias.sdpv3.R
import com.villaindustrias.sdpv3.constants.App
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition


class MapFragment : Fragment(), IView, OnMapReadyCallback {
    private lateinit var parent:View
    private lateinit var mapView: MapView

    private  var mapFragment: SupportMapFragment? = null
    private var mapsSupported = true




    private lateinit var lastLocation: Location
    private lateinit var mgoogleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val position: Location = Location(LocationManager.GPS_PROVIDER)
    companion object {
        fun newInstance(): Fragment {
            return MapFragment()
        }
    }

    /**
     * Método utilizado para escuchar click de los recyclers
     * @param item Elemento enviado por el recycler
     */


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         parent = inflater.inflate(R.layout.fragment_map, container, false)


        return parent
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = parent.findViewById(R.id.map) as MapView
        if (mapView != null){
            mapView.onCreate(null)
            mapView.onResume()
            mapView.getMapAsync(this)
        }

        mapFragment?.getMapAsync(this)
        setHasOptionsMenu(true)
        setData()
        setListeners()


    }

    override fun onMapReady(googleMap: GoogleMap) {
            MapsInitializer.initialize(context)
        mgoogleMap = googleMap
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        //googleMap.addMarker()
//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Libe))
    }

    override fun setData() {
        showMessage("En construccion")
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

/**
 * metodos de IMAPView
 */



}


