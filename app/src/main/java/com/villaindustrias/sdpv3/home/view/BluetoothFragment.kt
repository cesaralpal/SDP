package com.villaindustrias.sdpv3.home.view

import android.bluetooth.*
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.*
import android.widget.Toast
import com.ederdoski.simpleble.interfaces.BleCallback
import com.ederdoski.simpleble.models.BluetoothLE
import com.ederdoski.simpleble.utils.BluetoothLEHelper
import com.polimentes.utilitieskotlin.Util
import com.polimentes.utilitieskotlin.interfaces.view.IView
import com.polimentes.utilitieskotlin.recyclers.IRecyclerListener
import com.villaindustrias.sdpv3.R
import com.villaindustrias.sdpv3.home.presenter.BluetoohAdapter
import kotlinx.android.synthetic.main.fragment_bluetooth.*
import kotlin.collections.ArrayList
import com.polimentes.utilitieskotlin.recyclers.IRecyclerPosition
import com.villaindustrias.sdpv3.eventos.OnBluetoothConnection
import com.villaindustrias.sdpv3.eventos.OnCharacteristicRead
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.villaindustrias.sdpv3.constants.App
import com.villaindustrias.sdpv3.eventos.ButtonReadWrite
import com.villaindustrias.sdpv3.eventos.DataRealTime
import com.villaindustrias.sdpv3.utilidades.Utilities
import com.villaindustrias.sdpv3.utilidades.toHex
import java.lang.Integer.parseInt
import java.lang.Long.parseLong
import java.util.*
import kotlin.experimental.inv


class BluetoothFragment : Fragment(), IView, IRecyclerListener.Complex<BluetoothLE, Boolean>, IRecyclerPosition {
    private var itemPosition: Int? = null
    private var progressbar: View? = null
    var data: String? = null
    private var mBluetoothGatt: BluetoothGatt? = null
    private var adapter: BluetoohAdapter? = null
    var bondedDevices: ArrayList<BluetoothDevice?> = ArrayList()
    val byte: ArrayList<String?> = ArrayList()
    private lateinit var ble: BluetoothLEHelper
    protected val CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID =
        UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

    var uuid: UUID = UUID.fromString("00001901-0000-1000-8000-00805f9b34fb")
    val characteristicUuidOne: UUID = UUID.fromString("00002b01-0000-1000-8000-00805f9b34fb")
    val characteristicUuidTwo: UUID = UUID.fromString("00002b04-0000-1000-8000-00805f9b34fb")
    val characteristicUuidThree: UUID = UUID.fromString("00002b05-0000-1000-8000-00805f9b34fb")
    val characteristicUuidOneR: UUID = UUID.fromString("00002b02-0000-1000-8000-00805f9b34fb")
    val characteristicUuidTwoR: UUID = UUID.fromString("00002b03-0000-1000-8000-00805f9b34fb")
    val characteristicUuidThreeR: UUID = UUID.fromString("00002b06-0000-1000-8000-00805f9b34fb")
    val characteristicUuidFourR: UUID = UUID.fromString("00002b07-0000-1000-8000-00805f9b34fb")
    /**
     * Variables de toda la data
     * */
    var volts: Float? = null
    var condensadorEncendido: String? = null
    var iluminacion: Int? = null
    var potenciaReactiva:Float? = null
    var energiaActiva:Float? = null
    var energiaAparenteDia:Float? = null
    var energiaActivaMes:Float? = null
    var energiaReactivaAno:Float? = null
    var energiaAparenteTotal:Float? = null

    var corriente:Float? = null
    var evaporador:String? = null
    var frecuencia:Float? = null
    var potenciaAparente:Float? = null
    var factorDePotencia:Float? = null
    var energiaActivaDelDia:Float? = null
    var energiaReactivaDelBimestre:Float? = null
    var energiaAparenteDelAno:Float? = null
    var energiaActivaTotal:Float? = null

    var temperatura:Float? = null
    var deshielo:String? = null
    var periodo:Float? = null
    var energiaReactiva:Float? = null
    var anguloFasorial:Float? = null
    var energiaReactivaDelmes:Float? = null
    var energiaAparenteDelBimestre:Float? = null
    var energiaActivaDelAno:Float? = null

    var consumoDeEnergia:Float? = null
    var puerta:String? = null
    var potenciaActiva:Float? = null
    var energiaAparente:Float? = null
    var energiaReactivaDelDia:Float? = null
    var energiaAparenteDelMes:Float? = null
    var energiaActivaDelBimestre:Float? = null
    var energiaReactivaTotal:Float? = null

    private var bleCallbacks = object : BleCallback() {

        override fun onBleConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            super.onBleConnectionStateChange(gatt, status, newState)

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "Connected to GATT server.", Toast.LENGTH_SHORT).show()
                    adapter?.getStatusConnection(progressbar!!, itemPosition!!)
                    sendBluetoothHelper()
                }
            }

            if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                activity?.runOnUiThread {
                    Toast.makeText(activity!!, "Disconnected from GATT server.", Toast.LENGTH_SHORT).show()
                }
            }
        }


        override fun onBleServiceDiscovered(gatt: BluetoothGatt, status: Int) {
            super.onBleServiceDiscovered(gatt, status)
            if (status != BluetoothGatt.GATT_SUCCESS) {
                Log.e("BleServiceDiscovered", "onServicesDiscovered received: $status")
            }
            val characteristic = gatt
                .getService(uuid)
                .getCharacteristic(characteristicUuidOne)
            Log.d(App.TAG_LOG, characteristic.toString())
            val descriptor = characteristic.getDescriptor(CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID)
            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            gatt.setCharacteristicNotification(characteristic, true)
            gatt.writeDescriptor(descriptor)

            //if (ble.isConnected) ble.read("00001901-0000-1000-8000-00805f9b34fb","00002b01-0000-1000-8000-00805f9b34fb")

        }

        override fun onBleCharacteristicChange(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
            super.onBleCharacteristicChange(gatt, characteristic)
            Log.i("BluetoothLEHelper", "onCharacteristicChanged Value: " + characteristic.value.toHex())
            data = characteristic.value.toHex()

            var unixTime = data!!.substring(0, 7)
            var dateL = parseLong(unixTime, 16)
            val date = Date(dateL)
            val format = Utilities.formatDate(date)
            var valorID1 = data!!.substring(10, 16)
            var dataValueID1 = parseInt(valorID1, 16).toFloat() / 10

            var valorID2 = data!!.substring(18, 24)
            var dataValueID2 = parseInt(valorID2, 16).toFloat() / 10

            var valorID3 = data!!.substring(26, 32)
            var dataValueID3 = parseInt(valorID3, 16).toFloat() / 10

            var valorID4 = data!!.substring(34, 40)
            var dataValueID4 = parseInt(valorID4, 16).toFloat() / 10
            when (data!!.substring(8, 10)) {
                "01" -> {
                    volts = parseInt(valorID1, 16).toFloat() / 10
                    Log.d("data", "soy voltaje$volts")
                }
                "07" -> {
                    condensadorEncendido = if (valorID1 == "0000001")
                        "Encendido"
                    else
                        "Apagado"


                    Log.d("data", "Compresor$condensadorEncendido")

                }
                "0C" -> {
                    iluminacion = parseInt(valorID1, 16)/10
                    Log.d("data", "Ilumincacion$iluminacion")
                }
                "15" -> {
                    potenciaReactiva = parseInt(valorID1, 16).toFloat() / 10
                    Log.d("data", "Potencia reactiva$potenciaReactiva")

                }
                "19" ->{
                    energiaActiva = parseInt(valorID1, 16).toFloat() / 10
                    Log.d("data", "Energía Activa$energiaActiva")

                }
                "1D" -> {
                    energiaAparenteDia = parseInt(valorID1, 16).toFloat() / 10
                    Log.d("data", "Eenrgía parente del dia$energiaAparenteDia")


                }
                "21" -> {
                    energiaActivaMes = dataValueID1
                    Log.d("data", "Energía Activa del mes$energiaAparenteDia")
            }
                "25" -> {
                    energiaReactivaAno = dataValueID1
                    Log.d("data", "Energía reactiva del año$energiaReactivaAno")
            }
                "2F" -> {
                    energiaAparenteTotal = dataValueID1
                    Log.d("data", "Energía aparente total$energiaAparenteTotal")

            }
            }
            when (data!!.substring(16, 18)) {
                "02" -> {

                    corriente =  parseInt(valorID2, 16).toFloat() / 10
                    Log.d("data", "soy corriente$corriente")
                }
                "08" -> {
                    evaporador = if (valorID2 == "0000001")
                        "Encendido"
                    else
                        "Apagado"
                    Log.d("data", "evaporador$evaporador")
                }
                "0F" -> {
                    frecuencia = dataValueID2*10
                    Log.d("data", "frecuencia$frecuencia")
                }
                "16" -> {
                    potenciaAparente = dataValueID2
                    Log.d("data", "potenciaAparente$potenciaAparente")
                }
                "1A" ->{
                    factorDePotencia = dataValueID2
                    Log.d("data", "factorDePotencia$factorDePotencia")
                }
                "1E" -> {
                    energiaActivaDelDia = dataValueID2
                    Log.d("data", "energiaActivaDelDia$energiaActivaDelDia")
                }
                "22" -> {
                    energiaReactivaDelBimestre = dataValueID2
                    Log.d("data", "energiaReactivaDelBimestre$energiaReactivaDelBimestre")
                }
                "26" -> {
                    energiaAparenteDelAno = dataValueID2
                    Log.d("data", "energiaAparenteDelAno$energiaAparenteDelAno")
                }
                "30" -> {
                    energiaActivaTotal= dataValueID2
                    Log.d("data", "energiaActivaTotal$energiaActivaTotal")
                }
            }
            when (data!!.substring(24, 26)) {
                "03" -> {
                    if (dataValueID3>150f){
                        val temp = dataValueID3*10
                        Log.d("temperatura","$dataValueID3")
                        Log.d("temperatura","${convertDecimalToBinary(dataValueID3.toInt())}")
                        Log.d("temperatura","${convertDecimalToBinary(dataValueID3.toInt()).inv()}")

                        temperatura = ConvertBinaryToDecimal(convertDecimalToBinary(temp.toInt()).inv() + convertDecimalToBinary(1)
                        ).toFloat()/10
                    } else
                    temperatura = dataValueID3

                    Log.d("data", "soy temperatura $temperatura")
                }
                "0A" -> {
                    deshielo = if (valorID3 == "0000001")
                        "Encendido"
                    else
                        "Apagado"
                    Log.d("data", "deshielo $deshielo")
                }
                "10" -> {
                    periodo = dataValueID3
                    Log.d("data", "periodo$periodo")
                }
                "17" -> {
                    energiaReactiva = dataValueID3
                    Log.d("data", "energiaReactiva$energiaReactiva")
                }
                "1B" ->{
                    anguloFasorial = dataValueID3
                    Log.d("data", "anguloFasorial$anguloFasorial")
                }
                "1F" -> {
                    energiaReactivaDelmes = dataValueID3
                    Log.d("data", "energiaReactivaDelmes$energiaReactivaDelmes")
                }
                "23" -> {
                    energiaAparenteDelBimestre = dataValueID2
                    Log.d("data", "energiaAparenteDelBimestre$energiaAparenteDelBimestre")
                }
                "27" -> {
                    energiaActivaDelAno = dataValueID3
                    Log.d("data", "energiaActivaDelAno$energiaActivaDelAno")
                }
                "00" -> {
                }
            }
            when (data!!.substring(32, 34)) {
                "04" -> {

                    consumoDeEnergia = dataValueID4
                    Log.d("data", "consumoDeEnergia$consumoDeEnergia")
                }
                "0B" -> {
                    puerta = if (valorID2 == "0000001")
                        "Abierta"
                    else
                        "Cerrada"
                    Log.d("data", "puerta$puerta")
                }
                "11" -> {
                    potenciaActiva = dataValueID4
                    Log.d("data", "potenciaActiva$potenciaActiva")
                }
                "18" -> {
                    energiaAparente = dataValueID4
                    Log.d("data", "energiaAparente$energiaAparente")
                }
                "1C" ->{
                    energiaReactivaDelDia = dataValueID4
                    Log.d("data", "energiaReactivaDelDia$energiaReactivaDelDia")
                }
                "20" -> {
                    energiaAparenteDelMes = dataValueID4
                    Log.d("data", "energiaAparenteDelMes$energiaAparenteDelMes")
                }
                "24" -> {
                    energiaActivaDelBimestre = dataValueID4
                    Log.d("data", "energiaActivaDelBimestre$energiaActivaDelBimestre")
                }
                "2E" -> {
                    energiaReactivaTotal = dataValueID4
                    Log.d("data", "energiaReactivaTotal$energiaReactivaTotal")
                }
                "00" -> {
                }
            }
            sendData()

        }

        override fun onBleRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
            super.onBleRead(gatt, characteristic, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                var labelVoltaje: String? = null
                var labelCorriente: String? = null
                var labelTemperatura: String? = null

                byte.clear()

                for (b in characteristic.value) {
                    val st = String.format("%02X", b)
                    byte.add(st)
                }
                if (byte[4] == "01") labelVoltaje = "VCA"
                if (byte[8] == "02") labelCorriente = "ACA"
                if (byte[12] == "03") labelTemperatura = "T"
                Log.i("supresor", byte[4])

                var getCurrent = byte.subList(9, 13).joinToString().replace(", ", "")
                var getTemp = byte.subList(14, 17).joinToString().replace(", ", "")
                var unixTime = byte.subList(0, 4).joinToString().replace(", ", "")
                var dateL = parseLong(unixTime, 16)
                var current: Float = parseInt(getCurrent, 16).toFloat() / 10
                var temp: Float = parseInt(getTemp, 16).toFloat() / 10
                val date = Date(dateL)
                val format = Utilities.formatDate(date)

                Log.i("supresor", byte.toString())
                Log.i("supresor", "voltaje " + volts)
                Log.i("supresor", "corriente " + current)
                Log.i("supresor", "temp " + temp)
                Log.i("supresor", "date " + format)


//                var data = OnCharacteristicRead(format,labelVoltaje!!,volts,labelCorriente!!,current,labelTemperatura!!,temp)
                // EventBus.getDefault().postSticky(data)
                activity?.runOnUiThread {
                    Toast.makeText(
                        activity,
                        "onCharacteristicRead : " + Arrays.toString(characteristic.value),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }

        override fun onBleWrite(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
            super.onBleWrite(gatt, characteristic, status)

        }
    }
    private var bluetoothArray: ArrayList<BluetoothLE?> = ArrayList()
    private val bTAdapter: BluetoothAdapter? = null
    val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


    companion object {
        fun newInstance(): Fragment {
            return BluetoothFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(activity!!, permissions,0)
        ble =  BluetoothLEHelper(this.activity)

        return inflater.inflate(R.layout.fragment_bluetooth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Esta variable permite filtrar el BLE con el servicio especificado
        ble.setFilterService("00001901-0000-1000-8000-00805f9b34fb")

        setData()
        setListeners()
    }


    override fun setData() {

        mBluetoothAdapter.bondedDevices.forEach {
            if (!bondedDevices.contains(it)) bondedDevices.add(it)
        }
        setAdapterBondedDevices(bondedDevices)

        //onScanToggleClick()
    }


    override fun setListeners() {
        scan.setOnClickListener {
            scan.visibility = View.GONE
            progressScan.visibility = View.VISIBLE
            scanner()

        }
        //setAdapter(bluetoothArray)
    }


    fun sendBluetoothHelper() {
        val bleAux = OnBluetoothConnection(ble)
        //EventBus.getDefault().post(bleAux)
        EventBus.getDefault().postSticky(bleAux)
    }

    override fun onItemClick(item: BluetoothLE, flag: Boolean) {
        bluetoothController(item, flag)
    }


    fun setAdapter(devices: java.util.ArrayList<BluetoothLE?>) {
        val layoutManager = GridLayoutManager(context, 1)
        adapter = BluetoohAdapter(ArrayList(), this, this)
        adapter?.set(devices)
        recyclerBluetooth?.layoutManager = layoutManager
        recyclerBluetooth?.adapter = adapter

        adapter?.notifyDataSetChanged()
    }

    private fun setAdapterBondedDevices(devices: ArrayList<BluetoothDevice?>) {
        Log.d("bonded", "" + devices)
        val mlayoutManager = GridLayoutManager(context, 1)
        // adapterBonded = BluetoohAdapter(ArrayList(),this,this)
        // adapterBonded?.set(devices)
        // recyclerBondedBluetooth?.layoutManager = mlayoutManager
        //   recyclerBondedBluetooth?.adapter = adapterBonded
        // adapterBonded?.notifyDataSetChanged()
    }

    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {

    }

    override fun showMessage(messageResource: Any) {
        Util.showMessage(activity!!, messageResource)
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }


    /**
     * Métodos para el escaner
     * */
    fun scanner() {

        if (ble.isReadyForScan) {
            Log.d("bluetoothProof","estoy listo")

            val mHandler = Handler()
            ble.scanLeDevice(true)
            Log.d("bluetoothProof","estoy en el esacner")
            mHandler.postDelayed({
                progressScan.visibility = View.GONE
                scan.visibility = View.VISIBLE
                val listaBL: ArrayList<BluetoothLE?> = ArrayList()
                val inedex = if (ble.listDevices?.size!! > 5) 5 else ble?.listDevices?.size
                for (i in 0 until inedex!!) {
                    listaBL.add(ble.listDevices?.get(i))
                }
                setAdapter(listaBL)

            }, 5000)
        }else             Log.d("bluetoothProof"," no estoy listo")


    }

    /**
     *Métodos para conexión bluetooth
     */

    private fun bluetoothController(device: BluetoothLE?, flag: Boolean) {
        if (flag) {
            ble.connect(device!!.device, bleCallbacks)

        } else triggerDisconnect()
    }

    /**
     * Desconexion del bluetooth
     */
    private fun triggerDisconnect() {
        mBluetoothGatt?.close()
        ble.disconnect()
        ble = BluetoothLEHelper(activity)
    }

    /**
     * Obtiene el item donde esta posicionado
     */

    override fun getCurrentItemPosition(view: View?, position: Int) {

        itemPosition = position
        this.progressbar = view
        Log.d(App.TAG_LOG, "$itemPosition y $progressbar")
    }



    /***
     * Solicitud de permisos
     * **/



    /**
     * Mandar data del bluetooth
     */
    fun sendData() {
        var datos = DataRealTime( volts,
            condensadorEncendido,
            iluminacion,
            potenciaReactiva,
            energiaActiva,
            energiaAparenteDia,
            energiaActivaMes,
            energiaReactivaAno,
            energiaAparenteTotal,
            corriente,
            evaporador,
            frecuencia,
            potenciaAparente,
            factorDePotencia,
            energiaActivaDelDia,
            energiaReactivaDelBimestre,
            energiaAparenteDelAno,
            energiaActivaTotal,
            temperatura,
            deshielo,
            periodo,
            energiaReactiva,
            anguloFasorial,
            energiaReactivaDelmes,
            energiaAparenteDelBimestre,
            energiaActivaDelAno,
            consumoDeEnergia,
            puerta,
            potenciaActiva,
            energiaAparente,
            energiaReactivaDelDia,
            energiaAparenteDelMes,
            energiaActivaDelBimestre,
            energiaReactivaTotal)
        Log.d("corriente en blufrag", "$anguloFasorial")
        EventBus.getDefault().postSticky(datos)

    }


    //Métodos del event bus



    @Subscribe(sticky = true, threadMode = ThreadMode.BACKGROUND)
    fun onClick(event: ButtonReadWrite) {
        when (event.typeRW) {
            1 -> {
                Log.d(App.TAG_LOG, "entre al event")
            }

            2 -> {
            }
        }

    }

    fun ConvertBinaryToDecimal(num:Long):Int{
        var num = num
        var decimalNimber = 0
        var i = 0
        var remainder: Long
        while (num.toInt() != 0 ){
            remainder = num % 10
            num /= 10
            decimalNimber += (remainder * Math.pow(2.0,i.toDouble())).toInt()
            i++


        }
        return decimalNimber
    }

    fun convertDecimalToBinary(n: Int):Long{

        var n = n
        var binaryNumber:Long = 0
        var reaminder:Int
        var i = 1
        var step = 1
        n/=2

        while(n != 0){
            reaminder = n%2
            n/=2
            binaryNumber += (reaminder *i).toLong()
            i *= 10
        }
        return binaryNumber
    }
}


