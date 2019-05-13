package com.villaindustrias.sdpv3.home.presenter

import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.polimentes.utilitieskotlin.recyclers.IRecyclerListener
import com.polimentes.utilitieskotlin.recyclers.IRecyclerPosition
import com.villaindustrias.sdpv3.R
import com.villaindustrias.sdpv3.utilidades.Utilities
import kotlinx.android.synthetic.main.item_bluetooth.view.*
import org.greenrobot.eventbus.EventBus
import java.util.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.widget.CompoundButton
import android.widget.ImageView
import com.ederdoski.simpleble.models.BluetoothLE
import com.villaindustrias.sdpv3.constants.App
import com.villaindustrias.sdpv3.eventos.ChangeFragment


class BluetoohAdapter(var items: ArrayList<BluetoothLE?>, private val listener: IRecyclerListener.Complex<BluetoothLE,Boolean>, private val mlistener:IRecyclerPosition) :
    RecyclerView.Adapter<BluetoohAdapter.ViewHolder>() {


    var lastSelectedPosition = -1
    var onBind:Boolean? = null
    var statusC:Boolean? = false

    class MessageEvent/* Additional fields if needed */
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth, parent, false)
        return ViewHolder(view)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(items[position])
            onBind = true
            holder.itemView.chkBluetooth.isChecked = lastSelectedPosition == position
            onBind = false
            holder.selectionState.setOnCheckedChangeListener { _:CompoundButton, isChecked ->

                if (!onBind!!) {
                    if (isChecked) {
                        Log.d(App.TAG_LOG, "estre a is checked")
                        holder.itemView.progressConnected.visibility = View.VISIBLE
                        Log.d("conexion", "cambie le check box")
                        lastSelectedPosition = holder.adapterPosition
                        listener.onItemClick(items[holder.adapterPosition]!!, true)
                        mlistener.getCurrentItemPosition(holder.itemView, holder.adapterPosition)
                        holder.itemView.itemBluetooth.focusable = View.FOCUSABLE
                        notifyDataSetChanged()
                    } else {
                        Log.d("conexion", "voy a mandar false")
                        lastSelectedPosition = -1
                        holder.itemView.progressConnected.visibility = View.GONE

                        listener.onItemClick(items[holder.adapterPosition]!!, false)
                        holder.itemView.tvBluetoothMAC.paintFlags = 0
                        holder.itemView.tvBluetoothName.paintFlags = 0
                        holder.itemView.imgRefri.setImageResource(R.drawable.ic_refri)
                        holder.itemView.tvBluetoothName.setTextColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.colorAccent
                            )
                        )

                        holder.itemView.itemBluetooth.focusable = View.NOT_FOCUSABLE
                        holder.itemView.tvBluetoothMAC.setTextColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.colorAccent
                            )
                        )
                        notifyDataSetChanged()
                    }
                }
            }
        }


    fun add(items: ArrayList<BluetoothLE?>) {
        val length = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(length+1, items.size)
    }

    fun set(items: ArrayList<BluetoothLE?>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var selectionState = itemView.chkBluetooth!!

        fun bind(item: BluetoothLE?) {

            itemView.chkBluetooth.setOnCheckedChangeListener(null)
            itemView.tvBluetoothName.text = item?.name
            itemView.tvBluetoothMAC.text = item?.macAddress

        }
    }




    fun getStatusConnection(view: View, position: Int) {
        Log.d(App.TAG_LOG, "cambiando estilo")
            val calendar = Calendar.getInstance()
            var date = calendar.time
            var bar = view.findViewById<ProgressBar>(R.id.progressConnected)
            var text = view.findViewById<TextView>(R.id.tvBluetoothName)
            var textDate = view.findViewById<TextView>(R.id.tvBluetoothMAC)
            var icon = view.findViewById<ImageView>(R.id.imgRefri)
            bar.visibility = View.GONE
            icon.setImageResource(R.drawable.ic_group_73)
            text.setTextColor(ContextCompat.getColor(view.context, R.color.colorPrimary))
            text.paintFlags = text.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            textDate.text = "Última conexión ${Utilities.formatDateTwo(date)}"
            textDate.setTextColor(ContextCompat.getColor(view.context, R.color.colorPrimary))
            textDate.paintFlags = text.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            view.findViewById<ConstraintLayout>(R.id.itemBluetooth).itemBluetooth.setOnClickListener {
                val changeFragment: ChangeFragment = ChangeFragment(1)
                EventBus.getDefault().post(changeFragment)
            }

        }
}
