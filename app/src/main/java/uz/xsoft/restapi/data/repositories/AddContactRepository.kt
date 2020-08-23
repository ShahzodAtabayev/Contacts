package uz.xsoft.restapi.data.repositories

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.contracts.AddContactContract
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.local.storage.LocalStorage
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.networks.ApiClient
import uz.xsoft.restapi.networks.ContactApi

class AddContactRepository : AddContactContract.Model {
    private val contactApi = ApiClient.retrofit.create(ContactApi::class.java)
    private val localStorage = LocalStorage.instance
    override fun addContactAsync(contactData: ContactData): Deferred<Response<ResponseData<ContactData>>> {
        return contactApi.addContactAsync(contactData)
    }

    override fun getToken(): String = localStorage.token

}