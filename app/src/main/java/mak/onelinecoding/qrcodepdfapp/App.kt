package mak.onelinecoding.qrcodepdfapp

import android.app.Application
import android.provider.Settings
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig

class App : Application(), CameraXConfig.Provider {

    override fun onCreate() {
        super.onCreate()

        DEVICE_ID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
//        DEVICE_ID = "d554a13e60580e8e"
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig(this)
    }

    companion object {
        var DEVICE_ID = "d554a13e60580e8e"
    }
}