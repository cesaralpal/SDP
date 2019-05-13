package com.polimentes.utilitieskotlin.textwatchers

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.polimentes.utilitieskotlin.Constants
import com.polimentes.utilitieskotlin.R
import java.util.*
import java.text.SimpleDateFormat


class ExpirationDateWatcher(private val editText: EditText) : TextWatcher {
    private var wasBackPressed = false
    override fun afterTextChanged(editable: Editable?) {
        editText.removeTextChangedListener(this)
        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
        val stringBuilder: StringBuilder = StringBuilder(sequence)
        val length = stringBuilder.length
        val error = editText.context.getString(R.string.error_date)
        if (stringBuilder.contains("/")) {
            val numbers = stringBuilder.split("/")
            for (number in numbers) {
                if (number.length > 2) {
                    editText.setText("")
                    editText.error = error
                }
            }
        }
        if (length == 2 && before == 0) {
            val month = stringBuilder.toString().toInt()
            if (month <= 0 || month > 12) {

                stringBuilder.replace(length - 1, length, "")
                editText.setText(stringBuilder)
                editText.error = error
                editText.setSelection(stringBuilder.length)
            } else {
                stringBuilder.append("/")
                editText.setText(stringBuilder)
                editText.setSelection(stringBuilder.length)
            }
        }
        if (length == 2 && before == 1) {
            wasBackPressed = true
        }
        if (length == 3 && before == 0) {
            if (wasBackPressed) {
                stringBuilder.insert(2, "/")
                editText.setText(stringBuilder)
                editText.setSelection(stringBuilder.length)
            }

        }
        if (length == 5 && before == 0) {
            val cleanText = stringBuilder.replace(Constants.NOT_NUMBERS.toRegex(), "")
            val yearString = cleanText.substring(cleanText.length - 2, cleanText.length)
            val year = yearString.toInt()
            val formatter = SimpleDateFormat("yy")
            val formattedYear = formatter.format(Calendar.getInstance().time)
            val currentYear = formattedYear.toInt()
            if (year < currentYear) {
                stringBuilder.replace(length - 1, length, "")
                editText.setText(stringBuilder)
                editText.error = error
                editText.setSelection(stringBuilder.length)
            }

        }
    }

    private fun formatDate(field: Editable) {
        val text = field.toString()
        if (text.length == 2) {
            val month = text.toInt()
            if (month <= 0 || month > 12) {
                val error = editText.context.getString(R.string.error_date)
                editText.error = error
                field.replace(field.length - 1, field.length, "")
            } else {
                field.clear()
                field.insert(0, "$text/")
                editText.setSelection(field.length)
            }
        }
        if (text.length == 5) {
            val cleanText = text.replace(Constants.NOT_NUMBERS.toRegex(), "")
            val yearString = cleanText.substring(cleanText.length - 2, cleanText.length)
            val year = yearString.toInt()
            val formatter = SimpleDateFormat("yy")
            val formattedYear = formatter.format(Calendar.getInstance().time)
            val currentYear = formattedYear.toInt()
            if (year < currentYear) {
                val error = editText.context.getString(R.string.error_date)
                editText.error = error
                field.replace(field.length - 1, field.length, "")
            }

        }


    }
}