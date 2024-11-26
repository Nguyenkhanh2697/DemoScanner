package com.documentscanner

import android.content.Intent
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ActivityEventListener
import com.df.document_scanner.DocumentScanner
import com.facebook.react.bridge.Arguments

class DocumentScannerModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext), ActivityEventListener {

    private val documentScanner = DocumentScanner()
    private var scannerPromise: Promise? = null
    private var galleryPromise: Promise? = null

    init {
        reactContext.addActivityEventListener(this)
    }

    override fun getName(): String {
        return NAME
    }

    @ReactMethod
    fun startDocumentScanner(promise: Promise) {
        try {
            val activity = currentActivity
            if (activity == null) {
                promise.reject("ACTIVITY_NOT_FOUND", "Activity not found")
                return
            }
            scannerPromise = promise
            documentScanner.startDocumentScanner(activity, 5)
        } catch (e: Exception) {
            promise.reject("UNEXPECTED_ERROR", e.message)
        }
    }

    @ReactMethod
    fun openGalleryScanner(promise: Promise) {
        try {
            val activity = currentActivity
            if (activity == null) {
                promise.reject("ACTIVITY_NOT_FOUND", "Activity not found")
                return
            }
            galleryPromise = promise
            documentScanner.openGalleryScanner(activity, 5)
        } catch (e: Exception) {
            promise.reject("UNEXPECTED_ERROR", e.message)
        }
    }

    override fun onActivityResult(activity: android.app.Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            val result = documentScanner.handleActivityResult(requestCode, resultCode, data)
            if (result != null) {
                val writableMap = Arguments.createMap()
                for ((key, value) in result) {
                    writableMap.putString(key, value.toString())
                }
                if (scannerPromise != null) {
                    scannerPromise?.resolve(writableMap)
                } else if (galleryPromise != null) {
                    galleryPromise?.resolve(writableMap)
                }
            } else {
                scannerPromise?.reject("NO_RESULT", "No result from document scanner")
                galleryPromise?.reject("NO_RESULT", "No result from gallery scanner")
            }
        } catch (e: Exception) {
            scannerPromise?.reject("UNEXPECTED_ERROR", e.message)
            galleryPromise?.reject("UNEXPECTED_ERROR", e.message)
        } finally {
            scannerPromise = null
            galleryPromise = null
        }
    }

    override fun onNewIntent(intent: Intent?) {
    }

    companion object {
        const val NAME = "DocumentScanner"
    }
}
