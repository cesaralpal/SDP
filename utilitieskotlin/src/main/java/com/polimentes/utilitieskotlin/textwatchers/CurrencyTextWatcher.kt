package com.polimentes.utilitieskotlin.textwatchers

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.polimentes.utilitieskotlin.Constants
import com.polimentes.utilitieskotlin.Util


class CurrencyTextWatcher(private val editText: EditText) : TextWatcher {

    override fun afterTextChanged(editable: Editable?) {
        editText.removeTextChangedListener(this)
        formatMoneyField(editable!!)
        editText.addTextChangedListener(this)

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        Log.d("Util", "Before")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    private fun formatMoneyField(field: Editable) {
        Log.i("formatter", "enter")
        var cleanAmount: String
        cleanAmount = field.toString().replace(Constants.NOT_NUMBERS.toRegex(), "")
        Log.i("formatter", "strNumbers: $cleanAmount")
        if (cleanAmount.isNotEmpty()) {
            var decimals = ""
            if (cleanAmount.contains(".")) {
                val indexBeforePeriod = cleanAmount.lastIndexOf('.')
                decimals = cleanAmount.substring(indexBeforePeriod)
                if (cleanAmount.lastIndexOf('.') == cleanAmount.length - 1) {
                    if (cleanAmount.length > 1 && cleanAmount[cleanAmount.length - 2] == '.') {
                        field.replace(field.length - 1, field.length, "")
                        cleanAmount = cleanAmount.substring(0, cleanAmount.length - 1)
                    }
                }
                if (cleanAmount.length == 1) {
                    cleanAmount = "0"
                }
            }
            Log.i("formatter", "strNumbers: $cleanAmount")
            val amount = java.lang.Float.valueOf(cleanAmount)
            val strCommaAmount = Util.commaSeparatedFloat(amount)
            field.clear()
            field.insert(0, "$ $strCommaAmount")
            Log.i("formatter", "final: " + field.toString())
            if (!decimals.isEmpty() && decimals.length < 3) {
                if (!(decimals.length > 1 && decimals.toFloat() > 0))
                    field.append(decimals)
            }
        } else {
            field.clear()
        }
    }
}