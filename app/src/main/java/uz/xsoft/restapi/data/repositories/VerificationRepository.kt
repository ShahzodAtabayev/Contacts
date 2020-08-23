package uz.xsoft.restapi.data.repositories

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.networks.ApiClient
import uz.xsoft.restapi.networks.ContactApi
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.contracts.VerificationContract
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.local.storage.LocalStorage
import uz.xsoft.restapi.data.models.RegisterData
import uz.xsoft.restapi.data.models.SmsCodeData

class VerificationRepository : VerificationContract.Model {
    private val contactApi = ApiClient.retrofit.create(ContactApi::class.java)
    private val localStorage = LocalStorage.instance
    override fun addCodeToServerAsync(smsCodeData: SmsCodeData): Deferred<Response<ResponseData<String>>> {
        return contactApi.verifyAsync(smsCodeData)
    }

    override fun saveProfileData(registerData: RegisterData) {
        localStorage.profileData = registerData
    }

    override fun saveToken(token: String) {
        localStorage.token = token
    }

    override fun saveIsLogin(boolean: Boolean) {
        localStorage.isLogin = boolean
    }
}