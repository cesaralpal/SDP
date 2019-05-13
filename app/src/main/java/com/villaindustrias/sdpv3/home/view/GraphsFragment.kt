package com.villaindustrias.sdpv3.home.view

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ederdoski.simpleble.utils.BluetoothLEHelper
import com.github.mikephil.charting.charts.LineChart
import com.polimentes.utilitieskotlin.Util
import com.polimentes.utilitieskotlin.interfaces.view.IView
import com.villaindustrias.sdpv3.R
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.villaindustrias.sdpv3.eventos.DataRealTime
import com.villaindustrias.sdpv3.eventos.OnBluetoothConnection
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_graphs.*
import org.greenrobot.eventbus.EventBus


class GraphsFragment : Fragment(), IView, View.OnClickListener {

    var mChart: LineChart? = null
    var mChartTemp: LineChart? = null

    var ble: BluetoothLEHelper? = null
    var voltaje: Float? = null

    private var thread: Thread? = null

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
        return inflater.inflate(R.layout.fragment_graphs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mChart = activity?.findViewById(R.id.graph) as LineChart
        mChartTemp = activity?.findViewById(R.id.graph) as LineChart
        setData()
        setListeners()

    }

    override fun setData() {
        setupChart()
        setupAxes()
        setupData()
        setLegend()
    }

    override fun setListeners() {
        btnGraph.setOnClickListener(this)
    }


    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {

    }

    override fun showMessage(messageResource: Any) {
        Util.showMessage(activity!!, messageResource)
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }


    private fun addEntry(volts: Float?, temp: Float) {
        val data = mChart?.getData()
        val dataT = mChartTemp?.getData()
        if (data != null || dataT != null) {
            var set: ILineDataSet? = data!!.getDataSetByIndex(0)
            var setT: ILineDataSet? = dataT!!.getDataSetByIndex(0)

            if (set == null || setT == null) {
                set = createSet()
                data.addDataSet(set)
            }

            data.addEntry(Entry(set.entryCount.toFloat(), volts!!), 0)
           // dataT.addEntry(Entry(setT!!.entryCount.toFloat(), temp!!), 0)

            // let the chart know it's data has changed
            data.notifyDataChanged()
            dataT.notifyDataChanged()


            mChart?.notifyDataSetChanged()
            mChartTemp?.notifyDataSetChanged()
            // limit the number of visible entries
            mChart?.setVisibleXRangeMaximum(10f)
            mChartTemp?.setVisibleXRangeMaximum(10f)
            // move to the latest entry
            mChart?.moveViewToX(data.entryCount.toFloat())
            mChartTemp?.moveViewToX(dataT.entryCount.toFloat())

        }
    }

    private fun setupChart() {
        // disable description text
        mChart?.description!!.isEnabled = false
        // enable touch gestures
        mChart?.setTouchEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart?.setPinchZoom(true);
        // enable scaling
        mChart?.setScaleEnabled(true);
        mChart?.setDrawGridBackground(false);
        // set an alternative background color
        mChart?.setBackgroundColor(Color.DKGRAY);
    }

    private fun setupAxes() {
        val xl = mChart?.getXAxis()
        xl?.textColor = Color.WHITE
        xl?.setDrawGridLines(false)
        xl?.setAvoidFirstLastClipping(true)
        xl?.isEnabled = true

        val leftAxis = mChart?.getAxisLeft()
        leftAxis?.textColor = Color.WHITE
        leftAxis?.axisMaximum = 300f
        leftAxis?.axisMinimum = 0f
        leftAxis?.setDrawGridLines(true)

        val rightAxis = mChart?.getAxisRight()
        rightAxis?.isEnabled = false

        // Add a limit line
        val ll = LimitLine(150f, "Voltaje Max")
        ll.lineWidth = 2f
        ll.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        ll.textSize = 10f
        ll.textColor = Color.WHITE
        // reset all limit lines to avoid overlapping lines
        leftAxis?.removeAllLimitLines()
        leftAxis?.addLimitLine(ll)
        // limit lines are drawn behind data (and not on top)
        leftAxis?.setDrawLimitLinesBehindData(true)
    }

    private fun setupData() {
        val data = LineData()
        val dataT = LineData()

        data.setValueTextColor(Color.WHITE)
        dataT.setValueTextColor(Color.WHITE)

        // add empty data
        mChart?.setData(data)
        mChartTemp?.data = dataT
    }

    private fun setLegend() {
        // get the legend (only possible after setting data)
        val l: Legend = mChart!!.getLegend();
        val lt: Legend = mChartTemp!!.getLegend();


        // modify the legend ...
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTextColor(Color.WHITE);


    }

    private fun createSet(): LineDataSet {
        val set = LineDataSet(null, "Voltaje")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.setColors(ColorTemplate.VORDIPLOM_COLORS[0])
        set.setCircleColor(Color.WHITE)
        set.lineWidth = 2f
        set.circleRadius = 4f
        set.valueTextColor = Color.WHITE
        set.valueTextSize = 10f
        // To show values of each point
        set.setDrawValues(true)

        return set
    }

//    private fun createSetT(): LineDataSet {
//        val sett = LineDataSet(null, "temperatura")
//        sett.axisDependency = YAxis.AxisDependency.LEFT
//        sett.setColors(ColorTemplate.VORDIPLOM_COLORS[0])
//        sett.setCircleColor(Color.MAGENTA)
//        sett.lineWidth = 2f
//        sett.circleRadius = 4f
//        sett.valueTextColor = Color.RED
//        set.valueTextSize = 10f
//        // To show values of each point
//        set.setDrawValues(true)
//
//        return set
//    }

    override fun onClick(view: View?) {

        view?.let {
            when (it.id) {
                R.id.btnGraph -> {

                    if (ble!!.isConnected) {
                        btnGraph.text = "DETENER GRÁFICA"
                        if (ble != null) {
                            ble?.write(
                                "00001901-0000-1000-8000-00805f9b34fb",
                                "00002b02-0000-1000-8000-00805f9b34fb",
                                byteArrayOf(0x00)
                            )

                        } else Toast.makeText(
                            activity,
                            "Conectate a un dispotivo Villa Industrias",
                            Toast.LENGTH_LONG
                        ).show()
                    } else Toast.makeText(
                        activity,
                        "Conectate a un dispotivo Villa Industrias",
                        Toast.LENGTH_LONG
                    ).show()

                }
                else -> {
                }
            }


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

    @Subscribe(sticky = true, threadMode = ThreadMode.BACKGROUND)
    fun onBleReceive(event: OnBluetoothConnection) {
        Log.d("supresor", "asignando valor de ble")
        ble = event.ble
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDataReceive(event: DataRealTime) {
        voltaje = event.volts
        addEntry(voltaje!!, event.temperatura!!)

    }
}