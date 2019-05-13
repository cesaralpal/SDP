package com.villaindustrias.sdpv3.home.presenter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TableLayout
import com.villaindustrias.sdpv3.R
import com.villaindustrias.sdpv3.models.TableData
import kotlinx.android.synthetic.main.fragment_energy.view.*
import kotlinx.android.synthetic.main.item_energy_child.view.*
import kotlinx.android.synthetic.main.item_energy_parent.view.*

class ExpLEnergy(val _context: Context,  var listDataHeader: List<String>, private var listChild: List<TableData> // header titles
// child data in format of header title, child title
) : BaseExpandableListAdapter() {


    var listDataHeaderFiltered: List<String>
    var listDataHeaderOriginal = ArrayList<TableData>()

    init {
        listDataHeaderFiltered = listDataHeader
        listDataHeaderOriginal.addAll(listChild)
    }

    override fun getChild(groupPosition: Int, childPosititon: Int): Any {
        return listDataHeaderFiltered[groupPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

      //val childText = getChild(groupPosition, childPosition) as TableLayout

        if (convertView == null) {
            val infalInflater = this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.item_energy_child, null)
        }
       //childText.isStretchAllColumns = true
        convertView!!.tableEnergy.dailyMin.text = listChild[0].dayMin
        convertView.tableEnergy.dailyMax.text = listChild[0].dayMax
        convertView!!.tableEnergy.dailyProm.text = listChild[0].dayProm



        /*val txtListChild = convertView!!
                .findViewById(R.id.lblListItem) as TextView
        txtListChild.text = childText
        */
        return convertView!!
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getGroup(groupPosition: Int): Any {
        return this.listDataHeaderFiltered[groupPosition]
    }

    override fun getGroupCount(): Int {
        return this.listDataHeaderFiltered.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val headerTitle = getGroup(groupPosition) as String
        if (convertView == null) {
            val infalInflater = this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.item_energy_parent, null)
        }

        if(isExpanded){
            convertView!!.imgArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
        } else    convertView!!.imgArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)

        convertView!!.tvVoltaje.text = headerTitle

        return convertView!!
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
