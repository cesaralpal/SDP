package com.villaindustrias.sdpv3.home.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ederdoski.simpleble.utils.BluetoothLEHelper
import com.polimentes.utilitieskotlin.Util
import com.polimentes.utilitieskotlin.interfaces.view.IView
import com.villaindustrias.sdpv3.R
import com.villaindustrias.sdpv3.eventos.DataRealTime
import com.villaindustrias.sdpv3.eventos.OnBluetoothConnection
import kotlinx.android.synthetic.main.fragment_maintenance.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MaintenanceFragment : Fragment(), IView, View.OnClickListener {


    var ble: BluetoothLEHelper? = null
    var statusButton:Int = 0

    companion object {
        fun newInstance(): Fragment {
            return AlertsFragment()
        }
    }

    /**
     * Método utilizado para escuchar click de los recyclers
     * @param item Elemento enviado por el recycler
     */


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maintenance, container, false)
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
        btnStartTest.setOnClickListener(this)
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {

    }

    override fun showMessage(messageResource: Any) {
        Util.showMessage(activity!!, messageResource)
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }

    override fun onClick(view: View?) {
        view?.let {
            when (it.id) {
                R.id.btnStartTest -> {

                    if (statusButton == 0 && ble!!.isConnected) {
                        imgStart.setImageResource(R.drawable.ic_stop_black_24dp)
                        btnStartTest.text = "DETENER PRUEBA"
                        if (ble != null) {
                            ble?.write(
                                "00001901-0000-1000-8000-00805f9b34fb",
                                "00002b02-0000-1000-8000-00805f9b34fb",
                                byteArrayOf(0x00)
                            )
                            statusButton = 1
                        } else Toast.makeText(activity,"Conectate a un dispotivo Villa Industrias", Toast.LENGTH_LONG).show()
                    } else {
                        imgStart.setImageResource(R.drawable.ic_polygon)
                        btnStartTest.text = "INICIAR MODO DE PRUEBA"
                        if (ble != null) {
                            ble?.write(
                                "00001901-0000-1000-8000-00805f9b34fb",
                                "00002b02-0000-1000-8000-00805f9b34fb",
                                byteArrayOf(0x07)
                            )
                            statusButton = 0
                        }
                    }
                }
            }
        }

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.BACKGROUND)
    fun onBleReceive(event: OnBluetoothConnection) {
        Log.d("supresor", "asignando valor de ble")
        ble = event.ble
        //connectionObservable = prepareConnectionObservable()


        //   bleDevice = rxBleClient.getBleDevice(event.ble!!)
        // Log.d("supresor","cambio de voltaje ${  event.ble!!}" )

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDataReceive(event: DataRealTime) {
        tvVoltajeStatus.text = "${event.volts} V"
        tvCorrienteStatus.text = "${event.corriente} A"
        tvFrecuenciaStatus.text = "${event.frecuencia} Hz"
        tvPicosVoltajeStatus.text = "${19} "
        tvCaidasVoltajeStatus.text = "${3}"
        tvPeriodoStatus.text = "${event.periodo} ms"

        tvPotenciaActivaStatus.text = "${event.potenciaActiva} W"
        tvCostosEnergiaStatus.text = "$${123.45}"
        tvPotenciaReactivaStatus.text = "${event.potenciaReactiva} VAr"
        tvPotenciaAparenteStatus.text = "${event.potenciaAparente} VA"
        tvEnergiaReactivaStatus.text = "${event.energiaReactiva}VArh"
        tvEnergiaAparenteStatus.text = "${event.energiaAparente} VAh"
        tvEnergiaActivaStatus.text = "${event.energiaActiva} Wh"
        tvFactorPotenciaStatus.text = "${event.factorDePotencia}"
        tvAnguloFasorialStatus.text = "${event.anguloFasorial} °"

        tvEnergiaReactivaDiaStatus.text = "${event.energiaReactivaDelDia} VAr"
        tvEnergiaAparenteDiaStatus.text = "${event.energiaAparenteDia} kVA"

        tvEnergiaActivaDiaStatus.text  = "${event.energiaActivaDelDia} W"
        tvEnergiaReactivaMesStatus.text = "${event.energiaReactivaDelmes} VAr"

        tvEnergiaAparenteMesStatus.text = "${event.energiaAparenteDelMes} kVA"
        tvEnergiaActivaMesStatus.text = "${event.energiaActivaMes} kVA"

        tvEnergiaReactivaBimestreStatus.text = "${event.energiaReactivaDelBimestre} VAr"
        tvEnergiaAparenteBimestreStatus.text = "${event.energiaAparenteDelBimestre} kVA"

        tvEnergiaActivaBimestreStatus.text = "${event.energiaActivaDelBimestre}kW"
        tvEnergiaReactivaAnoStatus.text = "${event.energiaReactivaAno}VAr"

        tvEnergiaAparenteAnoStatus.text = "${event.energiaAparenteDelAno} kVA"
        tvEnergiaActivaAnoStatus.text = "${event.energiaActivaDelAno} kW"

        tvEnergiaReactivaTotalStatus.text = "${event.energiaReactivaTotal} 600VAr"
        tvEnergiaAparenteTotalStatus.text = "${event.energiaAparenteTotal} kVA"

        tvEnergiaActivaTotalStatus.text = "${event.energiaActivaTotal}kW"

        tvTES3Status.text = "${event.temperatura} °"

    }

}


