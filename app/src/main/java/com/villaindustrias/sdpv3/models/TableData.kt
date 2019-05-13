package com.villaindustrias.sdpv3.models

import io.realm.annotations.PrimaryKey

open class TableData  (
    @PrimaryKey
    var id:String = "",
    var dayMin:String? = "",
    var dayMax:String?="",
    var dayProm:String?="",
    var weekMin:String? = "",
    var weekMax:String?="",
    var weekProm:String?="",
    var monthMin:String? = "",
    var monthMax:String?="",
    var monthProm:String?="",
    var yearMin:String? = "",
    var yearMax:String?="",
    var yearProm:String?=""
) {
    override fun toString(): String {
        return "TableData(id='$id', dayMin='$dayMin', dayMax='$dayMax', dayProm='$dayProm', weekMin='$weekMin', weekMax='$weekMax', weekProm='$weekProm', monthMin='$monthMin', monthMax='$monthMax', monthProm='$monthProm', yearMin='$yearMin', yearMax='$yearMax', yearProm='$yearProm')"
    }
}