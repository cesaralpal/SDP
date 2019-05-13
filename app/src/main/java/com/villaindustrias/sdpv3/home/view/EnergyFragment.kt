package com.villaindustrias.sdpv3.home.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.Toast
import com.ederdoski.simpleble.utils.BluetoothLEHelper
import com.polidea.rxandroidble2.RxBleConnection
import com.polimentes.utilitieskotlin.Util
import com.polimentes.utilitieskotlin.interfaces.view.IView
import com.villaindustrias.sdpv3.R
import com.villaindustrias.sdpv3.eventos.ButtonReadWrite
import com.villaindustrias.sdpv3.eventos.OnBluetoothConnection
import com.villaindustrias.sdpv3.eventos.DataRealTime
import com.villaindustrias.sdpv3.home.presenter.ExpLEnergy
import com.villaindustrias.sdpv3.models.TableData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_energy.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.collections.ArrayList




class EnergyFragment : Fragment(), IView, View.OnClickListener {
    var ble: BluetoothLEHelper? = null
    lateinit var faqsListView: ExpandableListView
    lateinit var listAdapter: ExpLEnergy
    lateinit var faqsView: View
    private lateinit var connectionObservable: Observable<RxBleConnection>
    private val disconnectTriggerSubject = PublishSubject.create<Unit>()
    private lateinit var characteristicUuid: UUID
    private val connectionDisposable = CompositeDisposable()
    val characteristicUuidOne: UUID = UUID.fromString("00002b01-0000-1000-8000-00805f9b34fb")
    val characteristicUuidTwo: UUID = UUID.fromString("00002b04-0000-1000-8000-00805f9b34fb")
    val characteristicUuidThree: UUID = UUID.fromString("00002b05-0000-1000-8000-00805f9b34fb")
    val characteristicUuidOneR: UUID = UUID.fromString("00002b02-0000-1000-8000-00805f9b34fb")
    val characteristicUuidTwoR: UUID = UUID.fromString("00002b03-0000-1000-8000-00805f9b34fb")
    val characteristicUuidThreeR: UUID = UUID.fromString("00002b06-0000-1000-8000-00805f9b34fb")
    val characteristicUuidFourR: UUID = UUID.fromString("00002b07-0000-1000-8000-00805f9b34fb")


    private  var faqsListParent:List<String> = listOf("PICOS DE VOLTAJE","CAÍDAS DE VOLTAJE","SOBRECARGA",
            "FALLA ELÉCTRICA","VOLTAJE EXTREMADAMENTE ALTO","VOLTAJE EXTREMADAMENTE BAJO")

    var faqsListChild:ArrayList<TableData> =
        ArrayList( listOf(TableData("01","123","122","123","123")))



    companion object {
        fun newInstance(arguments:Bundle?): Fragment {
            return EnergyFragment()
        }
    }


    /**
     * Método utilizado para escuchar click de los recyclers
     * @param item Elemento enviado por el recycler
     */


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        faqsView = inflater.inflate(R.layout.fragment_energy, container, false)
        faqsListView = faqsView.findViewById(R.id.expandableFAQS) as ExpandableListView
        faqsListView.divider
        faqsListView.setIndicatorBoundsRelative(faqsListView.right - 40, faqsListView.width)


        listAdapter = ExpLEnergy(activity!!, faqsListParent,faqsListChild )
        faqsListView.setAdapter(listAdapter)
        return faqsView
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }
    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
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
        btnTest.setOnClickListener(this)
        btnWrite.setOnClickListener(this)
    }




    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {

    }

    override fun showMessage(messageResource: Any) {
        Util.showMessage(activity!!,messageResource)
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }


    override fun onClick(view: View?) {
        view?.let {
            when (it.id) {
                R.id.btnWrite -> {
                    if(ble!=null){ ble?.write("00001901-0000-1000-8000-00805f9b34fb","00002b02-0000-1000-8000-00805f9b34fb", byteArrayOf(0x00))
                }                    else Toast.makeText(activity,"noconectado",Toast.LENGTH_LONG).show()


                }
                R.id.btnTest -> {
                    if (ble!!.isConnected) {
                        ble?.read(
                            "00001901-0000-1000-8000-00805f9b34fb",
                            "00002b01-0000-1000-8000-00805f9b34fb"
                        )
                    }else Toast.makeText(activity,"noconectado",Toast.LENGTH_LONG).show()
                }
else -> Toast.makeText(activity,"noconectado",Toast.LENGTH_LONG).show()
            }
        }
    }






    @Subscribe( sticky = true,threadMode = ThreadMode.BACKGROUND)
    fun onBleReceive(event: OnBluetoothConnection) {
        Log.d("supresor","asignando valor de ble" )
        ble = event.ble
        //connectionObservable = prepareConnectionObservable()


        //   bleDevice = rxBleClient.getBleDevice(event.ble!!)
       // Log.d("supresor","cambio de voltaje ${  event.ble!!}" )

    }


    @Subscribe(sticky = true, threadMode = ThreadMode.BACKGROUND)
    fun onClick(event: ButtonReadWrite) {

    }


}


