package uz.xsoft.restapi.apps

import android.app.Application
import uz.xsoft.restapi.data.local.storage.LocalStorage
import uz.xsoft.restapi.utils.TextWatchers

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        TextWatchers.get()
        LocalStorage.init(this)
    }

    companion object {
        lateinit var instance: App
    }
}