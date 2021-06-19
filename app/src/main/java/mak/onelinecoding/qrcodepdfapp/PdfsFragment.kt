package mak.onelinecoding.qrcodepdfapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_pdfs.*


class PdfsFragment : BaseFragment(), PdfModelsAdapter.Listener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var exhibitorToken: String

    override val appBarExpanded: Boolean
        get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exhibitorToken = PdfsFragmentArgs.fromBundle(arguments!!).exibitorToken
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pdfs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)

        mainViewModel.oldToken = mainViewModel.exhibitorsToken
        mainViewModel.exhibitorsToken = exhibitorToken

        val adapter = PdfModelsAdapter(this)
        recyclerView.adapter = adapter


        mainViewModel.pdfModelsLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        mainViewModel.getPdfModels()
    }

    override fun onItemClicked(pdfModel: PdfModel) {
        NavHostFragment.findNavController(this)
            .navigate(PdfsFragmentDirections.actionPdfsFragmentToPdfFragment(pdfModel))
    }

    override fun onItemDeleted(pdfModel: PdfModel) {
        mainViewModel.removePdfModel(pdfModel)
    }

    override fun onItemShared(pdfModel: PdfModel) {
        val shareFileIntent = Intent(Intent.ACTION_SEND)
        shareFileIntent.type = "application/pdf"
        shareFileIntent.putExtra(
            Intent.EXTRA_STREAM,
            FileProvider.getUriForFile(
                context!!,
                "mak.onelinecoding.qrcodepdfapp.fileprovider",
                FileFactory.getFile(context!!, pdfModel.localPath)
            )
        )

        shareFileIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            pdfModel.exhibitorName + " exhibitor"
        )
        shareFileIntent.putExtra(Intent.EXTRA_TEXT, pdfModel.name + " catalog")

        startActivity(Intent.createChooser(shareFileIntent, "Share catalog"))
    }
}
