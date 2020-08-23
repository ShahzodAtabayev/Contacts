package uz.xsoft.restapi.contracts

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.models.ResponseData

interface HomeContract {
    interface Model {
        fun getAllContactAsync(token: String): Deferred<Response<ResponseData<List<ContactData>>>>
        fun addContactAsync(contactData: ContactData): Deferred<Response<ResponseData<ContactData>>>
        fun removeContactAsync(contactData: ContactData): Deferred<Response<ResponseData<ContactData>>>
        fun editContactAsync(contactData: ContactData): Deferred<Response<ResponseData<ContactData>>>
        fun getToken(): String
        fun addAllToDB(list: List<ContactData>)
        fun getAllDB(): List<ContactData>
        fun deleteAllDB()
        fun updateDb(contactData: ContactData)
    }

    interface ViewModel {
        fun btEdit(contactData: ContactData)
        fun btRemove(contactData: ContactData)
    }
}