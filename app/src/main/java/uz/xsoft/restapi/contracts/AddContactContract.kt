package uz.xsoft.restapi.contracts

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.models.ResponseData

interface AddContactContract {
    interface Model {
        fun addContactAsync(contactData: ContactData): Deferred<Response<ResponseData<ContactData>>>
        fun getToken(): String
    }

    interface ViewModel {
        fun btAddContact()
        fun btCancel()
    }
}