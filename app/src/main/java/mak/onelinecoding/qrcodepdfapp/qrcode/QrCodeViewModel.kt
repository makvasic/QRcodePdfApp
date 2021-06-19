package mak.onelinecoding.qrcodepdfapp.qrcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mak.onelinecoding.qrcodepdfapp.PdfModel
import mak.onelinecoding.qrcodepdfapp.WebApiFactory

class QrCodeViewModel : ViewModel() {

    private val qrCodeRepository = QrCodeRepository(WebApiFactory.webApi)

    private val _pdfModelLiveData = MutableLiveData<PdfModel>()
    val pdfModelLiveData: LiveData<PdfModel> = _pdfModelLiveData

    fun getPdfModel(url: String) {

        viewModelScope.launch {
            _pdfModelLiveData.value = qrCodeRepository.getPdfModel(url)
        }
    }
}