package uz.xsoft.restapi.viewModels.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.Response
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.contracts.BasketContract
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.utils.extensions.isConnected
import uz.xsoft.restapi.viewModels.main.ApiStatus
import java.lang.Exception

class BasketViewModel(app: Application, private val repository: BasketContract.Model) :
    AndroidViewModel(app), BasketContract.ViewModel {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private var _allContact = MutableLiveData<List<ContactData>>()
    val allContact: MutableLiveData<List<ContactData>>
        get() = _allContact
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

    init {
        getAllContact()
    }

    fun getAllContact() {
        coroutineScope.launch {
            val data: List<ContactData>
            withContext(Dispatchers.IO) {
                data = repository.getAllContact()
            }
            _allContact.value = data
        }
    }

    override fun removeContact(contactData: ContactData) {
        coroutineScope.launch {
            val data: List<ContactData>
            withContext(Dispatchers.IO) {
                repository.removeContact(contactData)
                data = repository.getAllContact()
            }
            _allContact.value = data
        }
    }

    override fun resetContact(contactData: ContactData) {
        coroutineScope.launch {
            _isConnected.value = true
            if (isNotConnected) {
                _toast.value = "Check your internet connection."
                return@launch
            }
            val deferred = repository.resetContactAsync(contactData)
            val response: Response<ResponseData<ContactData>>
            try {
                _status.value = ApiStatus.LOADING
                response = deferred.await()
                if (deferred.isCompleted && response.code() == 200) {
                    if (response.body()?.status == "OK") {
                        _status.value = ApiStatus.DONE
                        _toast.value = response.body()?.message
                        contactData.isDelete = false
                        withContext(Dispatchers.IO) {
                            repository.update(contactData)
                        }
                        getAllContact()
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