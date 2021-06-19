package mak.onelinecoding.qrcodepdfapp.exibitors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mak.onelinecoding.qrcodepdfapp.WebApiFactory

class ExhibitorsViewModel(application: Application) : AndroidViewModel(application) {

    private val exhibitorsRepository = ExhibitorsRepository(WebApiFactory.webApi)

    private val _exhibitorsLiveData = MutableLiveData<ExhibitorsState>()
    val exhibitorsLiveData: LiveData<ExhibitorsState> = _exhibitorsLiveData

    fun getExhibitors(forceReload: Boolean = false) {
        if (_exhibitorsLiveData.value != null && !forceReload) return

        _exhibitorsLiveData.value = ExhibitorsState.Loading
        viewModelScope.launch {
            _exhibitorsLiveData.value = exhibitorsRepository.getExhibitors()
        }
    }
}