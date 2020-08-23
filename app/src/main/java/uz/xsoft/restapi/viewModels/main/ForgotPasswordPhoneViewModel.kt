package uz.xsoft.restapi.viewModels.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.Response
import uz.xsoft.restapi.contracts.ForgotPasswordPhoneContract
import uz.xsoft.restapi.data.models.ResetPhoneData
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.utils.TextWatchers
import java.lang.Exception

class ForgotPasswordPhoneViewModel(app: Application, private val repository: ForgotPasswordPhoneContract.Model) :
    AndroidViewModel(app), ForgotPasswordPhoneContract.ViewModel {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _navigateToResetPasswordFragment = MutableLiveData<Boolean>()
    val navigateToResetPasswordFragment: LiveData<Boolean>
        get() = _navigateToResetPasswordFragment
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast
    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected
    private var isNotConnected: Boolean = false


    fun onCompletedToast() {
        _toast.value = null
    }

    fun onCompletedIsConnect() {
        _isConnected.value = false
    }

    fun notConnect() {
        isNotConnected = true
    }

    fun connected() {
        isNotConnected = false
    }

    override fun btSend() {
        coroutineScope.launch {
            _isConnected.value = true
            if (isNotConnected) {
                _toast.value = "Check your internet connection."
                return@launch
            }
            if (TextWatchers.forgotPasswordPhone.length < 5) {
                _toast.value = "Please. Enter phone number"
                return@launch
            }
            if (TextWatchers.forgotPasswordPhone.length != 17) {
                _toast.value = "Please. Check phone number"
                return@launch
            }
            val data = ResetPhoneData(
                TextWatchers.forgotPasswordPhone.replace(" ", "")
            )
            val deferred = repository.sendPhoneAsync(data)
            val response: Response<ResponseData<String>>
            try {
                _status.value = ApiStatus.LOADING
                response = deferred.await()
                if (deferred.isCompleted && response.code() == 200) {
                    if (response.body()?.status == "OK") {
                        _status.value = ApiStatus.DONE
                        _navigateToResetPasswordFragment.value = true
                        return@launch
                    }
                    if (response.body()?.status == "ERROR") {
                        _toast.value = response.body()?.message
                        _status.value = ApiStatus.DONE
                        return@launch
                    }
                }
                if (response.code() == 502) {
                    _status.value = ApiStatus.DONE
                    _toast.value = "Server is not working."
                    return@launch
                }
                if (response.code() == 404) {
                    _status.value = ApiStatus.DONE
                    _toast.value = "Server is not found."
                    return@launch
                }
            } catch (e: Exception) {
                _toast.value = "Qandaydir xatolik. Qaytadan urinib ko'ring"
                _status.value = ApiStatus.ERROR
            }
        }
    }
    fun onNavigateResetPasswordFragmentCompleted() {
        _navigateToResetPasswordFragment.value = false
    }
}