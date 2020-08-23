package uz.xsoft.restapi.data.repositories

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.contracts.HomeContract
import uz.xsoft.restapi.data.local.room.AppDatabase
import uz.xsoft.restapi.data.local.room.AppDatabase_Impl
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.local.storage.LocalStorage
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.networks.ApiClient
import uz.xsoft.restapi.networks.ContactApi

class HomeRepository : HomeContract.Model {
    private val contactApi = ApiClient.retrofit.create(ContactApi::class.java)
    private val localStorage = LocalStorage.instance
    private val db = AppDatabase.getDatabase(App.instance).contactDao()
    override fun getAllContactAsync(token: String): Deferred<Response<ResponseData<List<ContactData>>>> =
        contactApi.getAllContactAsync(token)

    override fun addContactAsync(contactData: ContactData): Deferred<Response<ResponseData<ContactData>>> {
        return contactApi.addContactAsync(contactData)
    }

    override fun removeContactAsync(contactData: ContactData): Deferred<Response<ResponseData<ContactData>>> {
        return contactApi.removeContactAsync(contactData)
    }

    override fun editContactAsync(contactData: ContactData): Deferred<Response<ResponseData<ContactData>>> {
        return contactApi.updateAsync(contactData)
    }

    override fun getToken(): String = localStorage.token
    override fun addAllToDB(list: List<ContactData>) {
        db.insertAll(list)
    }

    override fun getAllDB(): List<ContactData> = db.getAll(false)

    override fun deleteAllDB() = db.deleteAll()
    override fun updateDb(contactData: ContactData) {
        db.update(contactData)
    }

}