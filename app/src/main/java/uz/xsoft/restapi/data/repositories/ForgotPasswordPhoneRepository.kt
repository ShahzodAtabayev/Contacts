package uz.xsoft.restapi.data.repositories

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.contracts.ForgotPasswordPhoneContract
import uz.xsoft.restapi.data.models.ResetPhoneData
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.networks.ApiClient
import uz.xsoft.restapi.networks.ContactApi

class ForgotPasswordPhoneRepository : ForgotPasswordPhoneContract.Model {
    private val contactApi = ApiClient.retrofit.create(ContactApi::class.java)
    override fun sendPhoneAsync(resetPhoneData: ResetPhoneData): Deferred<Response<ResponseData<String>>> {
        return contactApi.sendPhoneAsync(resetPhoneData)
    }

}