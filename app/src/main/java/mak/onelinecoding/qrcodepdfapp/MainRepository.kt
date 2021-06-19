package mak.onelinecoding.qrcodepdfapp

import android.content.SharedPreferences
import android.util.Log
import java.io.IOException


class MainRepository(private val sharedPreferences: SharedPreferences) {

    fun getPdfModels(): List<PdfModel> {
        return try {
            WebApiFactory.adapter.fromJson(sharedPreferences.getString("pdfs", "[]")!!)
                ?: emptyList()
        } catch (e: IOException) {
            Log.e("MainRepo", "getPdfModels", e)
            emptyList()
        }
    }

    fun getFilteredPdfModels(exhibitorToken: String): List<PdfModel> {
        return try {
            val pdfModels = getPdfModels()

            val filtered = ArrayList<PdfModel>(pdfModels.size)
            pdfModels.forEach {
                if (it.exhibitorToken == exhibitorToken) {
                    filtered.add(it)
                }

            }
            filtered

        } catch (e: IOException) {
            emptyList()
        }
    }

    fun addPdfModel(pdfModel: PdfModel) {
        val pdfModels = getPdfModels().toMutableSet()
        pdfModels.add(pdfModel)

        val jsonPdfModels = WebApiFactory.adapter.toJson(pdfModels.toList())

        sharedPreferences.edit().putString("pdfs", jsonPdfModels).apply()
    }

    fun removePdfModel(pdfModel: PdfModel) {
        val pdfModels = getPdfModels().toMutableSet()
        pdfModels.remove(pdfModel)

        val jsonPdfModels = WebApiFactory.adapter.toJson(pdfModels.toList())

        sharedPreferences.edit().putString("pdfs", jsonPdfModels).apply()


    }
}