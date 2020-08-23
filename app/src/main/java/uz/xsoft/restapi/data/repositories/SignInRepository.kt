package uz.xsoft.restapi.data.repositories

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.networks.ApiClient
import uz.xsoft.restapi.networks.ContactApi
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.contracts.SignInContract
import uz.xsoft.restapi.data.local.storage.LocalStorage
import uz.xsoft.restapi.data.models.LoginData
import uz.xsoft.restapi.data.models.RegisterData

class SignInRepository : SignInContract.Model {
    private val contactApi = ApiClient.retrofit.create(ContactApi::class.java)
    private val localStorage = LocalStorage.instance

    override fun loginAsync(loginData: LoginData): Deferred<Response<ResponseData<String>>> {
        return contactApi.loginAsync(loginData)
    }

    override fun saveUserData(registerData: RegisterData) {
        localStorage.profileData = registerData
    }

    override fun saveToken(token: String) {
        localStorage.token = token
    }

    override fun saveIsLogin(boolean: Boolean) {
        localStorage.isLogin = boolean
    }


}