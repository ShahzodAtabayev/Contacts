package uz.xsoft.restapi.ui.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.xsoft.restapi.R
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.repositories.BasketRepository
import uz.xsoft.restapi.databinding.FragmentBasketBinding
import uz.xsoft.restapi.ui.adapters.ContactAdapter
import uz.xsoft.restapi.viewModels.home.BasketViewModel
import uz.xsoft.restapi.viewModels.home.BasketViewModelFactory

class BasketFragment : Fragment() {
    private lateinit var viewModel: BasketViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBasketBinding.inflate(inflater)
        val viewModelFactory = BasketViewModelFactory(App.instance, BasketRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(BasketViewModel::class.java)
        val adapter = ContactAdapter(R.menu.contact_menu_basket)
        binding.listContactBasket.adapter = adapter
        binding.viewModel = viewModel
        viewModel.allContact.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.submitList(it)
            }
        })
        Log.d("TTT", "1:" + adapter.currentList.size.toString())
        adapter.setOnDeleteListener { viewModel.removeContact(it) }
        adapter.setOnEditListener { viewModel.resetContact(it) }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllContact()
    }
}