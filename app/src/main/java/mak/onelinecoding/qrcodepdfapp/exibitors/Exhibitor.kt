package mak.onelinecoding.qrcodepdfapp.exibitors

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Exhibitor(
    @field:Json(name = "exhibitor_logo") val logo: String,
    @field:Json(name = "exhibitor_name") val name: String,
    @field:Json(name = "exhibitor_token") val token: String,
    @field:Json(name = "members") val members: List<Member>
) : Parcelable