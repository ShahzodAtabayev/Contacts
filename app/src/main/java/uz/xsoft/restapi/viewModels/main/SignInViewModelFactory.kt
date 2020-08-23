package uz.xsoft.restapi.viewModels.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.xsoft.restapi.contracts.SignInContract

class SignInViewModelFactory(val app: Application, private val repository: SignInContract.Model) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}