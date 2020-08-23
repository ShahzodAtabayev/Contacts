package uz.xsoft.restapi.contracts

import kotlinx.coroutines.Deferred
import retrofit2.Response
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.data.models.RegisterData

interface SignUpContract {
    interface Model {
        fun addToServerAsync(registerData: RegisterData): Deferred<Response<ResponseData<String>>>
    }

    interface ViewModel {
        fun btSignUp()
    }
}