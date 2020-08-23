package uz.xsoft.restapi.viewModels.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.xsoft.restapi.contracts.AddContactContract
import uz.xsoft.restapi.contracts.HomeContract

class AddContactViewModelFactory(val app: Application, private val repository: AddContactContract.Model) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddContactViewModel::class.java)) {
            return AddContactViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}