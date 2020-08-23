package uz.xsoft.restapi.viewModels.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.xsoft.restapi.contracts.ForgotPasswordPhoneContract

class ForgotPasswordPhoneViewModelFactory(val app: Application, private val repository: ForgotPasswordPhoneContract.Model) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForgotPasswordPhoneViewModel::class.java)) {
            return ForgotPasswordPhoneViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}