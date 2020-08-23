package uz.xsoft.restapi.data.repositories

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.networks.ApiClient
import uz.xsoft.restapi.networks.ContactApi
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.contracts.SignUpContract
import uz.xsoft.restapi.data.models.RegisterData

class SignUpRepository : SignUpContract.Model {
    private val customerApi = ApiClient.retrofit.create(ContactApi::class.java)
    override fun addToServerAsync(registerData: RegisterData): Deferred<Response<ResponseData<String>>> {
        return customerApi.registerAsync(registerData)
    }
}