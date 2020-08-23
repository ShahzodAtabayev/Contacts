package uz.xsoft.restapi.contracts

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.data.models.ResetPhoneData
import uz.xsoft.restapi.data.models.ResponseData

interface ForgotPasswordPhoneContract {
    interface Model {
        fun sendPhoneAsync(resetPhoneData: ResetPhoneData): Deferred<Response<ResponseData<String>>>
    }

    interface ViewModel {
        fun btSend()
    }
}