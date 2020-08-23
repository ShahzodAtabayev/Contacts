package uz.xsoft.restapi.contracts

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.data.models.LoginData
import uz.xsoft.restapi.data.models.RegisterData

interface SignInContract {
    interface Model {
        fun loginAsync(loginData: LoginData): Deferred<Response<ResponseData<String>>>
        fun saveUserData(registerData: RegisterData)
        fun saveToken(token: String)
        fun saveIsLogin(boolean: Boolean)
    }

    interface View {

    }

    interface ViewModel {
        fun btSignIn()
        fun btForgotPassword()
    }
}