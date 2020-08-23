package uz.xsoft.restapi.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.contracts.ProfileContract
import uz.xsoft.restapi.data.local.room.AppDatabase
import uz.xsoft.restapi.data.local.storage.LocalStorage

class ProfileRepository : ProfileContract.Model {
    private val localStorage = LocalStorage.instance
    private val db = AppDatabase.getDatabase(App.instance)
    override suspend fun logOut() {
        localStorage.profileData = null
        localStorage.isLogin = false
        localStorage.token = ""
        db.clearAllTables()
    }
}