package uz.xsoft.restapi.data.local.storage

import android.content.Context
import com.securepreferences.SecurePreferences
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.models.RegisterData

class LocalStorage private constructor(context: Context) {
    companion object {
        lateinit var instance: LocalStorage; private set

        fun init(context: Context) {
            instance = LocalStorage(context)
        }
    }

    private val pref = SecurePreferences(context, "123456", "contact")
    var isLogin: Boolean by BooleanPreference(pref, false)
    var profileData: RegisterData?
        set(value) {
            val edit = pref.edit()
            edit.putString("PHONE", value?.phoneNumber)
            edit.putString("LAST_NAME", value?.lastName)
            edit.putString("NAME", value?.firstName)
            edit.putString("PASSWORD", value?.password)
            edit.apply()
        }
        get() {
            return RegisterData(
                phoneNumber = pref.getString("PHONE", "+998999999999")!!,
                lastName = pref.getString("LAST_NAME", "Yandex")!!,
                firstName = pref.getString("NAME", "Alisa")!!,
                password = pref.getString("PASSWORD", "alisa.com")!!
            )
        }
    var token: String by StringPreference(pref, "")
}