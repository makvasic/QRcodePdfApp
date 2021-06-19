package mak.onelinecoding.qrcodepdfapp.qrcode

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata


class QrCodeAnalyzer(
    private val onQrCodesDetected: (pdfUrl: String) -> Unit
) : ImageAnalysis.Analyzer {

    private var analyzing = false
    private var scanned = false

    private val qrCodeDetector: FirebaseVisionBarcodeDetector by lazy {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
            .build()

        FirebaseVision.getInstance().getVisionBarcodeDetector(options)
    }

    override fun analyze(image: ImageProxy, rotationDegrees: Int) {
        val cameraImage = image.image ?: return

        if (analyzing) return
        analyzing = true

        val visionImage = FirebaseVisionImage.fromMediaImage(
            cameraImage,
            rotationDegreesToFirebaseRotation(rotationDegrees)
        )

        qrCodeDetector.detectInImage(visionImage)
            .addOnSuccessListener { barcodes ->
                analyzing = false
                if (barcodes.isNotEmpty() && !scanned) {
                    scanned = true
                    onQrCodesDetected(barcodes[0].rawValue!!)
                }
                cameraImage.close()
            }
            .addOnFailureListener {
                analyzing = false
                Log.e("QrCodeAnalyzer", "something went wrong", it)
                cameraImage.close()
            }

    }

    private fun rotationDegreesToFirebaseRotation(rotationDegrees: Int): Int {
        return when (rotationDegrees) {
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> FirebaseVisionImageMetadata.ROTATION_0
        }
    }
}