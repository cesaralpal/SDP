package com.polimentes.utilitieskotlin.interfaces.view


interface IMapView {


    /**
     * Función utilizada para verificar los permisos de ubicación del dispositivo
     * @return True or False dependiendo del estado.
     */
    fun checkLocationPermissions(): Boolean

    /**
     * Método utilizado para solicitar los permisos al usuario
     */
    fun requestLocationPermission()

    /**
     * Metodo utilizado para mover la camara del mapa al lugar seleccionado
     * @param latitude
     * @param longitude
     */
    fun setCamera(latitude: Float, longitude: Float)

    fun setDeviceLocation()

    /**
     * Método que inicia el fragment y la conexion.
     */
    fun setupFragmentMap()


}