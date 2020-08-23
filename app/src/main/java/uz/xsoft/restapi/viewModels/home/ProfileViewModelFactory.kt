package uz.xsoft.restapi.viewModels.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.xsoft.restapi.contracts.AddContactContract
import uz.xsoft.restapi.contracts.BasketContract
import uz.xsoft.restapi.contracts.HomeContract
import uz.xsoft.restapi.contracts.ProfileContract

class ProfileViewModelFactory(val app: Application, private val repository: ProfileContract.Model) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}