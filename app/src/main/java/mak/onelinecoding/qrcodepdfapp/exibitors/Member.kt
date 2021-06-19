package mak.onelinecoding.qrcodepdfapp.exibitors

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Member(
    @field:Json(name = "public_email_1") val email1: String,
    @field:Json(name = "public_email_2") val email2: String,
    @field:Json(name = "public_email_3") val email3: String,
    @field:Json(name = "public_email_4") val email4: String,
    @field:Json(name = "public_email_5") val email5: String,
    @field:Json(name = "public_phone_1") val phone1: String,
    @field:Json(name = "public_phone_2") val phone2: String,
    @field:Json(name = "public_phone_3") val phone3: String,
    @field:Json(name = "wechat") val wechat: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "photo") val photo: String
) : Parcelable