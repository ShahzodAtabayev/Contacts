package uz.xsoft.restapi.viewModels.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.contracts.AddContactContract
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.utils.TextWatchers
import uz.xsoft.restapi.utils.extensions.isConnected
import uz.xsoft.restapi.viewModels.main.ApiStatus
import java.lang.Exception

class AddContactViewModel(app: Application, private val repository: AddContactContract.Model) :
    AndroidViewModel(app), AddContactContract.ViewModel {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _navigateToHomeFragment = MutableLiveData<Boolean>()
    val navigateToHomeFragment: LiveData<Boolean>
        get() = _navigateToHomeFragment
    private val _popToHome = MutableLiveData<Boolean>()
    val popToHome: LiveData<Boolean>
        get() = _popToHome
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

    override fun btAddContact() {
        coroutineScope.launch {
            _isConnected.value = true
            if (isNotConnected) {
                _toast.value = "Check your internet connection."
                return@launch
            }
            if (TextWatchers.name.isEmpty()) {
                _toast.value = "Please. Enter first name!!!"
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
            val contactData = ContactData(
                firstName = TextWatchers.name,
                lastName = TextWatchers.surName,
                phoneNumber = TextWatchers.phone.replace(" ", "")
            )
            val deferred = repository.addContactAsync(contactData)
            val response: Response<ResponseData<ContactData>>
            try {
                _status.value = ApiStatus.LOADING
                response = deferred.await()
                if (deferred.isCompleted && response.code() == 200) {
                    if (response.body()?.status == "OK") {
                        _status.value = ApiStatus.DONE
                        _toast.value = response.body()?.message
                        _navigateToHomeFragment.value = true
                        return@launch
                    }
                    if (response.body()?.status == "ERROR") {
                        _status.value = ApiStatus.DONE
                        _toast.value = response.body()?.message
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

    override fun btCancel() {
        _popToHome.value = true
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

    fun onNavigateHomeFragmentCompleted() {
        _navigateToHomeFragment.value = false
    }

    fun onPopToHomeCompleted() {
        _popToHome.value = false
    }
}