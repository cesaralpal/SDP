package com.polimentes.utilitieskotlin

/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 02/03/2018
 */
object Constants {
    const val LOG_UTILITIES = "logUtilities"
    const val TIMEOUT_MS: Long = 25000
    /**
     * Constantes para el uso de SharePrefenrences
     */
    const val SPM_DEFAULT_STRING = "-"
    const val SPM_ACCESS_TOKEN = "ACCESS_TOKEN"
    const val SPM_REFRESH_TOKEN = "REFRESH_TOKEN"
    const val SPM_FIRST_NAME = "FIRST_NAME"
    /**
     * Constantes para dialogos
     */
    const val DIALOG_KEY_XML = "DIALOG_KEY_XML"
    const val DIALOG_KEY_TITLE = "DIALOG_KEY_TITLE"
    const val DIALOG_KEY_BODY = "DIALOG_KEY_BODY"
    const val DIALOG_KEY_IMAGE = "DIALOG_KEY_IMAGE"
    const val DIALOG_KEY_IDS = "DIALOG_KEY_IDS"
    const val DIALOG_ID_TITLE = "DIALOG_ID_TITLE"
    const val DIALOG_ID_BODY = "DIALOG_ID_BODY"
    const val DIALOG_ID_IMAGE = "DIALOG_ID_IMAGE"
    const val DIALOG_ID_ACCEPT = "DIALOG_ID_ACCEPT"
    const val DIALOG_ID_CANCEL = "DIALOG_ID_CANCEL"
    /**
     * Constantes utilizadas para request code
     */
    const val REQUEST_CODE_NFC = 100

    /**
     * Etiquetas para headers de peticiones
     */
    const val AUTH: String = "Authorization"
    const val BASIC_AUTH = "Basic"
    const val TYPE_TOKEN: String = "Bearer"
    const val CONTENT_TYPE: String = "Content-Type"
    const val APPLICATION_JSON: String = "application/json"
    const val APPLICATION_XWWFORM: String = "application/x-www-form-urlencoded; charset=UTF-8"

    /**
     * Constantes utilizadas para REGEX
     */
    const val NOT_NUMBERS = "[^0-9.]+"
    const val REGEX_EMAIL = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)"

}