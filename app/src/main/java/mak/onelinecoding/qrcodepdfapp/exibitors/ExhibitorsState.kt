package mak.onelinecoding.qrcodepdfapp.exibitors

sealed class ExhibitorsState {
    object Loading : ExhibitorsState()
    data class Success(val exhibitors: List<Exhibitor>) : ExhibitorsState()
    data class Error(val throwable: Throwable) : ExhibitorsState()
}