package com.villaindustrias.sdpv3.home.presenter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polimentes.utilitieskotlin.recyclers.IRecyclerListener
import com.villaindustrias.sdpv3.R
import com.villaindustrias.sdpv3.models.Place
import kotlinx.android.synthetic.main.item_place.view.*


class PlaceAdapter(var items: ArrayList<Place?>, private val listener: IRecyclerListener<Place?>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun add(items: ArrayList<Place?>) {
        val length = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(length, items.size)
    }

    fun set(items: ArrayList<Place?>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Place?) {
            var color = when(item?.color){
            0 -> {
                ContextCompat.getColor(itemView.context,R.color.color_status_green)
            }
            1 ->{
                ContextCompat.getColor(itemView.context,R.color.color_status_red)
            }
                    else -> ContextCompat.getColor(itemView.context,R.color.color_status_yellow)

            }

            itemView.tvPlace.text = item?.namePlace
            itemView.tvEquipment.text = item?.nameEquipment
            itemView.tvIndex.text = item?.index.toString()
            itemView.imgStatus.setBackgroundColor(color)
            itemView.item.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }
}
