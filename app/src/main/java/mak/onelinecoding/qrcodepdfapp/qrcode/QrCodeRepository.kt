package mak.onelinecoding.qrcodepdfapp.qrcode

import android.util.Log
import mak.onelinecoding.qrcodepdfapp.PdfModel
import mak.onelinecoding.qrcodepdfapp.WebApi


class QrCodeRepository(private val webApi: WebApi) {

    suspend fun getPdfModel(url: String): PdfModel? {

        return try {
            webApi.getPdfModel(url)
        } catch (t: Throwable) {
            Log.e("QrCodeRepo", "getPdfModel", t)
            null
        }

    }

}