package uz.xsoft.restapi.data.repositories

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.contracts.ForgotPasswordContract
import uz.xsoft.restapi.data.models.ResetPasswordData
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.networks.ApiClient
import uz.xsoft.restapi.networks.ContactApi

class ForgotPasswordRepository : ForgotPasswordContract.Model {
    private val contactApi = ApiClient.retrofit.create(ContactApi::class.java)
    override fun sendChangePasswordAsync(resetPasswordData: ResetPasswordData): Deferred<Response<ResponseData<String>>> {
        return contactApi.sendChangePasswordAsync(resetPasswordData)
    }
}