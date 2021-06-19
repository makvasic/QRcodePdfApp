package mak.onelinecoding.qrcodepdfapp.qrcode

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.LensFacing
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.android.synthetic.main.fragment_qrcode.*
import mak.onelinecoding.qrcodepdfapp.BaseFragment
import mak.onelinecoding.qrcodepdfapp.MainViewModel
import mak.onelinecoding.qrcodepdfapp.R

class QrCodeFragment : BaseFragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var qrCodeViewModel: QrCodeViewModel
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraProviderFuture = ProcessCameraProvider.getInstance(context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qrcode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        } else {
            init()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init()
            }
        }
    }

    private fun init() {

        mainViewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)
        qrCodeViewModel = ViewModelProvider(this).get(QrCodeViewModel::class.java)
        qrCodeViewModel.pdfModelLiveData.observe(viewLifecycleOwner, Observer { pdfModel ->

            if (pdfModel == null) {
                Toast.makeText(context, "There was and error", Toast.LENGTH_LONG).show()
                return@Observer
            }

            mainViewModel.addPdfModel(pdfModel)

            NavHostFragment.findNavController(this)
                .navigate(
                    QrCodeFragmentDirections.actionQrCodeFragmentToPdfFragment(pdfModel)
                )
        })


        cameraView.post {
            setUpCameraX()
        }
    }

    private fun setUpCameraX() {

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(LensFacing.BACK)
            .build()

        val preview = Preview.Builder()
            .build()

        preview.previewSurfaceProvider = previewView.previewSurfaceProvider


        val imageAnalysis = ImageAnalysis.Builder()
            .build()

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context), QrCodeAnalyzer {
            Log.d("QRAnalyzer", "QR Code detected: $it")
            qrCodeViewModel.getPdfModel(it)
        })

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageAnalysis
            )
        }, ContextCompat.getMainExecutor(context))
    }


    companion object {
        private const val CAMERA_REQUEST_CODE = 643
    }
}