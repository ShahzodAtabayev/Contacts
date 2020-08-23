package uz.xsoft.restapi.data.repositories

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.contracts.BasketContract
import uz.xsoft.restapi.data.local.room.AppDatabase
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.networks.ApiClient
import uz.xsoft.restapi.networks.ContactApi

class BasketRepository : BasketContract.Model {
    private val db = AppDatabase.getDatabase(App.instance).contactDao()
    private val contactApi = ApiClient.retrofit.create(ContactApi::class.java)
    override suspend fun getAllContact(): List<ContactData> = db.getAll(true)

    override suspend fun removeContact(contactData: ContactData) {
        db.delete(contactData)
    }

    override fun resetContactAsync(contactData: ContactData): Deferred<Response<ResponseData<ContactData>>> {
        return contactApi.addContactAsync(contactData)
    }

    override suspend fun update(contactData: ContactData) {
        db.update(contactData)
    }
}