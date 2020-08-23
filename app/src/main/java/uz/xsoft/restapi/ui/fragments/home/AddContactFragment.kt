package uz.xsoft.restapi.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.xsoft.restapi.R
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.data.repositories.AddContactRepository
import uz.xsoft.restapi.databinding.FragmentAddContactBinding
import uz.xsoft.restapi.utils.TextWatchers
import uz.xsoft.restapi.utils.extensions.isConnected
import uz.xsoft.restapi.viewModels.home.AddContactViewModel
import uz.xsoft.restapi.viewModels.home.AddContactViewModelFactory

class AddContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddContactBinding.inflate(inflater)
        val viewModelFactory = AddContactViewModelFactory(App.instance, AddContactRepository())
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(AddContactViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.textWatcher = TextWatchers.INSTANCE
        viewModel.toast.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.onCompletedToast()
            }
        })
        viewModel.isConnected.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                if (!requireContext().isConnected()!!) {
                    viewModel.notConnect()
                    viewModel.onCompletedIsConnect()
                } else {
                    viewModel.connected()
                    viewModel.onCompletedIsConnect()
                }
            }
        })
        viewModel.navigateToHomeFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().popBackStack()
                viewModel.onNavigateHomeFragmentCompleted()
            }
        })
        viewModel.popToHome.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().popBackStack()
                viewModel.onPopToHomeCompleted()
            }
        })
        return binding.root
    }
}