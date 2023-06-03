package com.hyeokbeom.shared

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.hyeokbeom.musinsaassignment.BuildConfig

/**
 * Log
 * [커스텀 로그]
 */
object Log {
    private const val TAG = "[MUSINSA]"

    /** Error **/
    fun e(message: String?) {
        if (isLoggable()) Log.e(TAG, buildLogMsg(message))
    }

    /** Warning **/
    fun w(message: String?) {
        if (isLoggable()) Log.w(TAG, buildLogMsg(message))
    }

    /** Information **/
    fun i(message: String?) {
        if (isLoggable()) Log.i(TAG, buildLogMsg(message))
    }

    /** Debug **/
    fun d(message: String?) {
        if (isLoggable()) Log.d(TAG, buildLogMsg(message))
    }

    /** Verbose **/
    fun v(message: String?) {
        if (isLoggable()) Log.v(TAG, buildLogMsg(message))
    }

    private fun isLoggable(): Boolean = BuildConfig.DEBUG

    private fun buildLogMsg(message: String?): String {
        val ste = Thread.currentThread().stackTrace[4]
        val sb = StringBuilder().apply {
            append("[")
            append(ste.fileName.replace(".kt", ""))
            append("::")
            append(ste.methodName)
            append("] ")
        }

        val result: String = if (message == null) {
            "message is empty"
        } else if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val prettyPrintJson = GsonBuilder().setPrettyPrinting()
                    .create().toJson(JsonParser.parseString(message))
                prettyPrintJson
            } catch (m: JsonSyntaxException) {
                message
            }
        } else {
            message
        }

        sb.append(result)
        return sb.toString()
    }
}