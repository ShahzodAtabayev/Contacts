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
import uz.xsoft.restapi.contracts.VerificationContract
import uz.xsoft.restapi.utils.TextWatchers
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.data.models.RegisterData
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.data.models.SmsCodeData
import uz.xsoft.restapi.utils.extensions.isConnected

enum class CountDownTimerWorker { START, STOP }

class VerificationViewModel(app: Application, private val repository: VerificationContract.Model) :
    AndroidViewModel(app), VerificationContract.ViewModel {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _timerWorker = MutableLiveData<CountDownTimerWorker>()
    val timerWorker: LiveData<CountDownTimerWorker>
        get() = _timerWorker
    private val _navigateToHomeFragment = MutableLiveData<Boolean>()
    val navigateToHomeFragment: LiveData<Boolean>
        get() = _navigateToHomeFragment
    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected
    private var isNotConnected: Boolean = false

    init {
        _timerWorker.value = CountDownTimerWorker.START
    }

    override fun btSignInVerification() {
        coroutineScope.launch {
            _isConnected.value = true
            if (TextWatchers.codeVerification.isEmpty()) {
                _toast.value = "Please. Enter Code!!"
                return@launch
            }
            if (TextWatchers.codeVerification.length != 6) {
                _toast.value = "Please. Check your code!!!"
                return@launch
            }
            if (isNotConnected) {
                _toast.value = "Check your internet connection."
                return@launch
            }
            val responseData: Response<ResponseData<String>>
            val deferred = repository.addCodeToServerAsync(
                SmsCodeData(
                    phoneNumber = TextWatchers.phone.replace(" ", ""),
                    code = TextWatchers.codeVerification
                )
            )
            try {
                _status.value = ApiStatus.LOADING
                responseData = deferred.await()
                if (responseData.code() == 200) {
                    if (responseData.body()?.status == "OK") {
                        repository.saveIsLogin(true)
                        repository.saveToken(responseData.body()?.data!!)
                        _timerWorker.value = CountDownTimerWorker.STOP
                        _status.value = ApiStatus.DONE
                        repository.saveProfileData(
                            RegisterData(
                                phoneNumber = TextWatchers.phone,
                                lastName = TextWatchers.surName,
                                firstName = TextWatchers.name,
                                password = TextWatchers.password
                            )
                        )
                        _navigateToHomeFragment.value = true
                        return@launch
                    }
                    if (responseData.body()?.status == "ERROR") {
                        _toast.value = responseData.body()?.message
                        _status.value = ApiStatus.DONE
                        return@launch
                    }
                }
                if (responseData.code() == 502) {
                    _status.value = ApiStatus.DONE
                    _toast.value = "Server is not working."
                    return@launch
                }
                if (responseData.code() == 404) {
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

    fun onCompletedNavigateToHomeActivity() {
        _navigateToHomeFragment.value = false
    }

    fun onCompletedToast() {
        _toast.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        _timerWorker.value = CountDownTimerWorker.STOP
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
