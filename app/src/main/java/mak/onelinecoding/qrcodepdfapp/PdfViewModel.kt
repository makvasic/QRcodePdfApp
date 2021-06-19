package mak.onelinecoding.qrcodepdfapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File

class PdfViewModel(application: Application) : AndroidViewModel(application) {

    private val pdfRepository = PdfRepository(WebApiFactory.webApi)

    private val _pdfFileLiveData = MutableLiveData<File?>()
    val pdfFileLiveData: LiveData<File?> = _pdfFileLiveData

    fun getPdf(pdfModel: PdfModel) {
        if (_pdfFileLiveData.value != null) return

        viewModelScope.launch {
            _pdfFileLiveData.value =
                pdfRepository.getPdf(
                    pdfModel,
                    FileFactory.getFile(getApplication(), pdfModel.localPath)
                )
        }
    }
}