package uz.xsoft.restapi.viewModels.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.Response
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.contracts.BasketContract
import uz.xsoft.restapi.contracts.ProfileContract
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.models.ResponseData
import uz.xsoft.restapi.utils.extensions.isConnected
import uz.xsoft.restapi.viewModels.main.ApiStatus
import java.lang.Exception

class ProfileViewModel(app: Application, private val repository: ProfileContract.Model) :
    AndroidViewModel(app), ProfileContract.ViewModel {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _exit = MutableLiveData<Boolean>()
    val exit: LiveData<Boolean>
        get() = _exit
    private val _navigateToEditProfile = MutableLiveData<Boolean>()
    val navigateToEditProfile: LiveData<Boolean>
        get() = _navigateToEditProfile
    private val _navigateToSettings = MutableLiveData<Boolean>()
    val navigateToSettings: LiveData<Boolean>
        get() = _navigateToSettings
    private val _contacts = MutableLiveData<Boolean>()
    val contacts: LiveData<Boolean>
        get() = _contacts
    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast

    override fun btLogOut() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                repository.logOut()
            }
            _exit.value = true
        }
    }

    override fun btEditProfile() {
        _navigateToEditProfile.value = true
    }

    override fun btSettings() {
        _navigateToSettings.value = true
    }

    override fun btContacts() {
        _contacts.value = true
    }

    fun onCompletedToast() {
        _toast.value = null
    }

    fun onCompletedExit() {
        _exit.value = false
    }

    fun onCompletedContacts() {
        _contacts.value = false
    }

    fun onCompletedEditProfile() {
        _navigateToEditProfile.value = false
    }

    fun onCompletedSettings() {
        _navigateToSettings.value = false
    }
}