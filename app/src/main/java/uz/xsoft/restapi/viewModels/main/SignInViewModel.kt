package uz.xsoft.restapi.viewModels.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.Response
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.contracts.SignInContract
import uz.xsoft.restapi.utils.TextWatchers
import uz.xsoft.restapi.data.models.LoginData
import uz.xsoft.restapi.data.models.RegisterData
import java.lang.Exception

enum class ApiStatus { LOADING, ERROR, DONE }
class SignInViewModel(app: Application, private val repository: SignInContract.Model) :
    AndroidViewModel(app), SignInContract.ViewModel {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _navigateToHomeFragment = MutableLiveData<Boolean>()
    val navigateToHomeFragment: LiveData<Boolean>
        get() = _navigateToHomeFragment
    private val _navigateToForgotPasswordFragment = MutableLiveData<Boolean>()
    val navigateToForgotPasswordFragment: LiveData<Boolean>
        get() = _navigateToForgotPasswordFragment
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

    override fun btSignIn() {
        coroutineScope.launch {
            _isConnected.value = true
            if (isNotConnected) {
                _toast.value = "Check your internet connection."
                return@launch
            }
            if (TextWatchers.login.isEmpty()) {
                _toast.value = "Please. Enter phone number"
                return@launch
            }
            if (TextWatchers.login.length != 17 && TextWatchers.login.substring(0, 1) != "+") {
                _toast.value = "Please. Check phone number"
                return@launch
            }
            if (TextWatchers.loginPassword.length < 4 || TextWatchers.loginPassword.length > 30) {
                _toast.value = "Password length should be 4 to 30 characters"
                return@launch
            }
            if (TextWatchers.loginPassword.isEmpty()) {
                _toast.value = "Please. Enter password"
                return@launch
            }

            val data = LoginData(
                TextWatchers.login.replace(" ", ""),
                TextWatchers.loginPassword
            )
            val deferred = repository.loginAsync(data)
            val response: Response<ResponseData<String>>
            try {
                _status.value = ApiStatus.LOADING
                response = deferred.await()
                if (deferred.isCompleted && response.code() == 200) {
                    if (response.body()?.status == "OK") {
                        repository.saveToken(response.body()?.data!!)
                        _status.value = ApiStatus.DONE
                        repository.saveIsLogin(true)
                        repository.saveUserData(
                            RegisterData(
                                lastName = "",
                                firstName = "",
                                phoneNumber = TextWatchers.login,
                                password = TextWatchers.loginPassword
                            )
                        )
                        _navigateToHomeFragment.value = true
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

    override fun btForgotPassword() {
        _navigateToForgotPasswordFragment.value = true
    }

    fun onNavigateHomeFragmentCompleted() {
        _navigateToHomeFragment.value = false
    }

    fun onNavigateToForgotPasswordFragmentCompleted() {
        _navigateToForgotPasswordFragment.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

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
}