package mak.onelinecoding.qrcodepdfapp

import android.content.Context
import java.io.File

object FileFactory {

    private const val CATALOGS = "catalogs/"

    fun getFile(context: Context, localFilePath: String): File {

        return File(context.filesDir, CATALOGS + localFilePath)
    }


}