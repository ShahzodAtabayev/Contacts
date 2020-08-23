package uz.xsoft.restapi.viewModels.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.xsoft.restapi.contracts.ForgotPasswordContract
import uz.xsoft.restapi.contracts.ForgotPasswordPhoneContract

class ForgotPasswordViewModelFactory(val app: Application, private val repository: ForgotPasswordContract.Model) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)) {
            return ForgotPasswordViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}