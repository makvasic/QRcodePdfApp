package mak.onelinecoding.qrcodepdfapp

import android.util.Log
import okio.Okio
import java.io.File


class PdfRepository(private val webApi: WebApi) {

    suspend fun getPdf(pdfModel: PdfModel, pdfFile: File): File? {

        try {
            if (pdfFile.exists()) {
                return pdfFile
            }

            pdfFile.parentFile?.mkdirs()
            pdfFile.createNewFile()

            val inputStream = webApi.downloadPdf(pdfModel.filePath)?.byteStream()

            if (inputStream == null) {
                Log.w("PdfRepository", "inputStream is null")
                return null
            }

            val source = Okio.buffer(Okio.source(inputStream))
            val sink = Okio.buffer(Okio.sink(pdfFile))

            sink.writeAll(source)
            sink.close()
            source.close()

            return pdfFile
        } catch (t: Throwable) {
            Log.e("PdfRepository", "getPdf", t)
            return null
        }
    }

}