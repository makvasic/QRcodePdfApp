package mak.onelinecoding.qrcodepdfapp

import mak.onelinecoding.qrcodepdfapp.exibitors.Exhibitor
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url


interface WebApi {

    @GET
    suspend fun downloadPdf(@Url url: String): ResponseBody?

    @GET
    suspend fun getPdfModel(@Url url: String): PdfModel

    @GET("api/exhibitors")
    suspend fun getExhibitors(): List<Exhibitor>


}