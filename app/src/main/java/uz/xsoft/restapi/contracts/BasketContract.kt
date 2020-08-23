package uz.xsoft.restapi.contracts

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.models.ResponseData

interface BasketContract {
    interface Model {
        suspend fun getAllContact(): List<ContactData>
        suspend fun removeContact(contactData: ContactData)
        fun resetContactAsync(contactData: ContactData): Deferred<Response<ResponseData<ContactData>>>
        suspend fun update(contactData: ContactData)
    }

    interface ViewModel {
        fun removeContact(contactData: ContactData)
        fun resetContact(contactData: ContactData)
    }
}