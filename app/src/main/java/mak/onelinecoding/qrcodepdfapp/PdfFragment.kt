package mak.onelinecoding.qrcodepdfapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_pdf.*

class PdfFragment : BaseFragment() {

    lateinit var pdfModel: PdfModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pdfModel = PdfFragmentArgs.fromBundle(arguments!!).pdfModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pdf, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pdfViewModel = ViewModelProvider(this).get(PdfViewModel::class.java)

        pdfViewModel.pdfFileLiveData.observe(viewLifecycleOwner, Observer { pdfFile ->

            progressBar.visibility = View.GONE
            if (pdfFile == null) {
                Toast.makeText(context, "There was error", Toast.LENGTH_LONG).show()
                return@Observer
            }

            pdfView.fromFile(pdfFile).load()
        })

        pdfViewModel.getPdf(pdfModel)
    }
}