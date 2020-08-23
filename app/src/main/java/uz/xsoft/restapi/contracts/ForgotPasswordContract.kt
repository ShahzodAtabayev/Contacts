package uz.xsoft.restapi.contracts

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.data.models.ResetPasswordData
import uz.xsoft.restapi.data.models.ResponseData

interface ForgotPasswordContract {
    interface Model {
        fun sendChangePasswordAsync(resetPasswordData: ResetPasswordData): Deferred<Response<ResponseData<String>>>
    }

    interface ViewModel {
        fun btChangePassword()
    }
}