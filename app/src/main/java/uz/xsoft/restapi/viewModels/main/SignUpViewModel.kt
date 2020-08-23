package uz.xsoft.restapi.viewModels.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import uz.xsoft.restapi.contracts.SignUpContract
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.data.models.RegisterData
import uz.xsoft.restapi.utils.TextWatchers
import java.lang.Exception

class SignUpViewModel(application: Application, private val repository: SignUpContract.Model) :
    AndroidViewModel(application),
    SignUpContract.ViewModel {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToVerificationFragment = MutableLiveData<String>()
    val navigateToVerificationFragment: LiveData<String>
        get() = _navigateToVerificationFragment
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

    override fun btSignUp() {
        coroutineScope.launch {
            _isConnected.value = true
            if (TextWatchers.name.isEmpty()) {
                _toast.value = "Please. Enter name!!!"
                return@launch
            }
            if (TextWatchers.surName.isEmpty()) {
                _toast.value = "Please. Enter last name!!!"
                return@launch
            }
            if (TextWatchers.phone.isEmpty() || TextWatchers.phone.length != 17) {
                _toast.value = "Please. Check phone number!!!"
                return@launch
            }
            if (TextWatchers.password.isEmpty()) {
                _toast.value = "Please. Enter password!!!"
                return@launch
            }
            if (TextWatchers.repeatPassword.isEmpty()) {
                _toast.value = "Please. Enter repeat password!!!"
                return@launch
            }
            if (TextWatchers.repeatPassword != TextWatchers.password) {
                _toast.value = "Please. Check repeat password!!!"
                return@launch
            }
            if (isNotConnected) {
                _toast.value = "Check your internet connection."
                return@launch
            }
            val data = RegisterData(
                lastName = TextWatchers.surName,
                firstName = TextWatchers.name,
                password = TextWatchers.password,
                phoneNumber = TextWatchers.phone.replace(" ", "")
            )
            val deferred = repository.addToServerAsync(data)
            val response: Response<ResponseData<String>>
            try {
                _status.value = ApiStatus.LOADING
                response = deferred.await()
                _status.value = ApiStatus.DONE
                if (response.code() == 200 && deferred.isCompleted) {
                    if (response.body()?.status == "OK") {
                        _toast.value = response.body()?.message
                        _status.value = ApiStatus.DONE
                        _navigateToVerificationFragment.value = TextWatchers.phone
                        return@launch
                    }
                    if (response.body()?.status == "ERROR") {
                        _toast.value = response.body()?.message
                        _status.value = ApiStatus.DONE
                        return@launch
                    }
                }
                if (response.code() == 502) {
                    _toast.value = "Server is not working."
                    _status.value = ApiStatus.DONE
                    return@launch
                }
                if (response.code() == 404) {
                    _status.value = ApiStatus.DONE
                    _toast.value = "Server is not found."
                    return@launch
                }
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _toast.value = "Something went wrong. Please try again"
            }
        }
    }

    fun onNavigateVerificationFragmentCompleted() {
        _navigateToVerificationFragment.value = null
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