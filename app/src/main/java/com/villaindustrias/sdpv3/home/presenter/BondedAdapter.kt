package com.villaindustrias.sdpv3.home.presenter

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polimentes.utilitieskotlin.recyclers.IRecyclerListener
import com.villaindustrias.sdpv3.R
import kotlinx.android.synthetic.main.item_bluetooth.view.*



class BondedAdapter(var items: ArrayList<BluetoothDevice?>, private val listener: IRecyclerListener.Complex<BluetoothDevice?,Boolean>) :
    RecyclerView.Adapter<BondedAdapter.ViewHolder>() {
    private var lastSelectedPosition = -1

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.selectionState.isChecked = lastSelectedPosition == position
        holder.selectionState.setOnCheckedChangeListener { _, _ ->
            this.lastSelectedPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
    }

    fun add(items: ArrayList<BluetoothDevice?>) {
        val length = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(length, items.size)
    }

    fun set(items: ArrayList<BluetoothDevice?>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var selectionState = itemView.chkBluetooth
        fun bind(item: BluetoothDevice?) {

            itemView.tvBluetoothName.text = item?.name
            itemView.tvBluetoothMAC.text = item?.address
            itemView.chkBluetooth.setOnCheckedChangeListener { _, isChecked ->

                if (isChecked) {
                    Log.d("conexion","cambie le check box")
                    listener.onItemClick(item,true)
                }
                else
                {
                    Log.d("conexion","voy a mandar false")
                    listener.onItemClick(item,false)

                }

            }

        }
    }



}
