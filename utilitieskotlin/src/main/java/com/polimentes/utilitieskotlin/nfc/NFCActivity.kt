package com.polimentes.utilitieskotlin.nfc

import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.NfcAdapter
import android.support.v7.app.AppCompatActivity
import android.content.IntentFilter
import android.app.PendingIntent
import android.nfc.NdefMessage
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Parcelable
import java.nio.charset.Charset
import android.nfc.NdefRecord
import android.util.Log
import com.polimentes.utilitieskotlin.Constants
import com.polimentes.utilitieskotlin.enums.NFCMode
import java.util.*


/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 28/02/2018
 */
abstract class NFCActivity : AppCompatActivity() {

    private val nfcAdapter: NfcAdapter by lazy { initNfcAdapter() }
    //Indica el modo de operación false = Lectura y true = Escritura
    var mode = NFCMode.READING

    protected var payload: String? = null

    override fun onResume() {
        super.onResume()
        if (nfcAdapter.isEnabled) {
            enableForegroundDispatchSystem()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onPause() {
        super.onPause()
        if (nfcAdapter.isEnabled) {
            nfcAdapter.disableForegroundDispatch(this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
            val tag: Tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            val ndef: Ndef? = Ndef.get(tag)
            if (mode == NFCMode.WRITTING && payload != null) {
                ndef?.let {
                    val message: NdefMessage = createNdefMessage(payload!!, Locale.US, true)
                    val status: Boolean = writeMessage(ndef, message)
                    notifyWriteStatus(status)
                }
            } else {
                ndef?.let {
                    val message: String = processNFCIntentForMessage(intent)
                    val mac: String = processNFCIntentForMAC(intent)
                    dispatchTAGData(mac, message)
                }
            }
        }
    }

    protected abstract fun dispatchTAGData(mac: String?, message: String?)

    protected abstract fun notifyWriteStatus(status: Boolean)

    private fun initNfcAdapter(): NfcAdapter {
        return NfcAdapter.getDefaultAdapter(this)
    }

    @SuppressLint("MissingPermission")
    private fun enableForegroundDispatchSystem() {
        val intent = Intent(this, javaClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(this, Constants.REQUEST_CODE_NFC, intent, 0)
        val intentFilters: Array<IntentFilter> = arrayOf()
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
    }

    private fun processNFCIntentForMAC(intent: Intent): String {
        val rawMAC: ByteArray = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
        return buildNFCMAC(rawMAC)
    }

    private fun processNFCIntentForMessage(intent: Intent): String {
        val rawMessages: Array<Parcelable>? = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        return if (rawMessages != null && rawMessages.isNotEmpty())
            buildNFCMessage(rawMessages[0] as NdefMessage) else "No message"
    }

    private fun buildNFCMAC(rawMAC: ByteArray): String {
        val builder = StringBuilder()
        rawMAC
                .map { String.format("%02x:", it.toInt() and 0xFF) }
                .forEach { builder.append(it) }
        builder.deleteCharAt(builder.lastIndex)
        return builder.toString().toUpperCase()
    }

    private fun buildNFCMessage(ndefMessage: NdefMessage): String {
        val payload: ByteArray = ndefMessage.records[0].payload
        if (payload.isNotEmpty()) {
            val textEncoding: Charset = if ((payload[0].toInt() and 128) == 0) Charsets.UTF_8 else Charsets.UTF_16
            val languageCodeLength = payload[0].toInt() and 0x33
            return String(payload, languageCodeLength + 1, payload.size - languageCodeLength - 1, textEncoding)
        }
        return "No message"
    }

    protected fun setMessage(payload: String?) {
        this.payload = payload
    }

    private fun createNdefMessage(payload: String, locale: Locale, encodeInUtf8: Boolean): NdefMessage {
        val langBytes = locale.language.toByteArray(Charset.forName("US-ASCII"))
        val utfEncoding = if (encodeInUtf8) Charsets.UTF_8 else Charsets.UTF_16
        val textBytes = payload.toByteArray(utfEncoding)
        val utfBit = if (encodeInUtf8) 0 else 1 shl 7
        val status = (utfBit + langBytes.size).toChar()
        val data = ByteArray(1 + langBytes.size + textBytes.size)
        data[0] = status.toByte()
        System.arraycopy(langBytes, 0, data, 1, langBytes.size)
        System.arraycopy(textBytes, 0, data, 1 + langBytes.size, textBytes.size)
        val record: NdefRecord = NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT,
                ByteArray(0),
                data)
        return NdefMessage(arrayOf(record))
    }

    @SuppressLint("MissingPermission")
    private fun writeMessage(ndef: Ndef, message: NdefMessage): Boolean {
        try {
            ndef.connect()
            if (message.toByteArray().size > ndef.maxSize) {
                return false
            }
            return if (ndef.isWritable) {
                ndef.writeNdefMessage(message)
                true
            } else {
                false
            }
        } catch (exception: Exception) {
            Log.e(Constants.LOG_UTILITIES, "Error ${exception.message}")

        }
        return false
    }


}