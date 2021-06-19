package mak.onelinecoding.qrcodepdfapp.exibitors

import android.util.Log
import mak.onelinecoding.qrcodepdfapp.WebApi


class ExhibitorsRepository(private val webApi: WebApi) {

    suspend fun getExhibitors(): ExhibitorsState {
        return try {
//            val exhibitors = ArrayList<Exhibitor>()
//            for (i in 0..20) {
//                exhibitors.add(Exhibitor("", "Test Exhibitor", "test_token", emptyList()))
//            }
//            ExhibitorsState.Success(exhibitors)
            ExhibitorsState.Success(webApi.getExhibitors())
        } catch (t: Throwable) {
            Log.e("ExhibitorsRepo", "getExhibitors", t)
            ExhibitorsState.Error(t)
        }
    }

}