package uz.xsoft.restapi.viewModels.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.xsoft.restapi.contracts.AddContactContract
import uz.xsoft.restapi.contracts.BasketContract
import uz.xsoft.restapi.contracts.HomeContract

class BasketViewModelFactory(val app: Application, private val repository: BasketContract.Model) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BasketViewModel::class.java)) {
            return BasketViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}