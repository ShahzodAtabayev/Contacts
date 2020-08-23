package uz.xsoft.restapi.viewModels.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.Response
import uz.xsoft.restapi.contracts.ForgotPasswordContract
import uz.xsoft.restapi.data.models.ResetPasswordData
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.utils.TextWatchers
import java.lang.Exception

class ForgotPasswordViewModel(app: Application, private val repository: ForgotPasswordContract.Model) :
    AndroidViewModel(app), ForgotPasswordContract.ViewModel {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _navigateToSignInFragment = MutableLiveData<Boolean>()
    val navigateToSignInFragment: LiveData<Boolean>
        get() = _navigateToSignInFragment
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

    override fun btChangePassword() {
        coroutineScope.launch {
            _isConnected.value = true
            if (isNotConnected) {
                _toast.value = "Check your internet connection."
                return@launch
            }
            if (TextWatchers.resetPassword.isEmpty()) {
                _toast.value = "Please. Enter password"
                return@launch
            }
            if (TextWatchers.codeResetPassword.length != 6) {
                _toast.value = "Please. Check code verify"
                return@launch
            }
            val data = ResetPasswordData(
                phoneNumber = TextWatchers.forgotPasswordPhone.replace(" ", ""),
                code = TextWatchers.codeResetPassword,
                password = TextWatchers.resetPassword
            )
            val deferred = repository.sendChangePasswordAsync(data)
            val response: Response<ResponseData<String>>
            try {
                _status.value = ApiStatus.LOADING
                response = deferred.await()
                if (deferred.isCompleted && response.code() == 200) {
                    if (response.body()?.status == "OK") {
                        _status.value = ApiStatus.DONE
                        _navigateToSignInFragment.value = true
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
                _toast.value = "Something went wrong. Please try again"
                _status.value = ApiStatus.ERROR
            }
        }
    }


    fun onNavigateToSignInFragmentCompleted() {
        _navigateToSignInFragment.value = false
    }
}