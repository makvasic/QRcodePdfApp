package mak.onelinecoding.qrcodepdfapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var oldToken = " "
    var exhibitorsToken = " "

    private val mainRepository =
        MainRepository(application.getSharedPreferences("pdfs_preferences", Context.MODE_PRIVATE))

    private val _pdfModelsLiveData = MutableLiveData<List<PdfModel>>()
    val pdfModelsLiveData: LiveData<List<PdfModel>> = _pdfModelsLiveData

    fun getPdfModels(changed: Boolean = exhibitorsToken != oldToken) {
        if (_pdfModelsLiveData.value != null && !changed) return

        _pdfModelsLiveData.value = if (exhibitorsToken.isBlank()) {
            mainRepository.getPdfModels()
        } else {
            mainRepository.getFilteredPdfModels(exhibitorsToken)
        }
    }

    fun addPdfModel(pdfModel: PdfModel) {
        mainRepository.addPdfModel(pdfModel)
        getPdfModels(true)
    }

    fun removePdfModel(pdfModel: PdfModel) {
        mainRepository.removePdfModel(pdfModel)
        getPdfModels(true)

        viewModelScope.launch(Dispatchers.IO) {
            val pdfFile = FileFactory.getFile(getApplication(), pdfModel.localPath)
            pdfFile.delete()
        }
    }
}