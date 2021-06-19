package mak.onelinecoding.qrcodepdfapp

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PdfModel(
    @field:Json(name = "file_path") val filePath: String,
    @field:Json(name = "thumb_url") val thumbUrl: String,
    @field:Json(name = "exhibitor_logo") val exhibitorLogo: String,
    @field:Json(name = "exhibitor_name") val exhibitorName: String,
    @field:Json(name = "exhibitor_token") val exhibitorToken: String
) : Parcelable {

    val name: String
        get() = filePath.substring(filePath.lastIndexOf('/') + 1)

    val localPath: String
        get() = "$exhibitorToken/$name"
}