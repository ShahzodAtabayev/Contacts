package uz.xsoft.restapi.viewModels.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.xsoft.restapi.contracts.VerificationContract

class VerificationViewModelFactory(
    val app: Application,
    private val repository: VerificationContract.Model
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerificationViewModel::class.java)) {
            return VerificationViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}