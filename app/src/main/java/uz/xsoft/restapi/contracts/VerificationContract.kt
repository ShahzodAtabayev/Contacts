package uz.xsoft.restapi.contracts

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.models.RegisterData
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.data.models.SmsCodeData

interface VerificationContract {
    interface Model {
        fun addCodeToServerAsync(smsCodeData: SmsCodeData): Deferred<Response<ResponseData<String>>>
        fun saveProfileData(registerData: RegisterData)
        fun saveToken(token: String)
        fun saveIsLogin(boolean: Boolean)
    }

    interface ViewModel {
        fun btSignInVerification()
    }
}