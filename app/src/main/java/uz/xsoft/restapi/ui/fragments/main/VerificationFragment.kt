package uz.xsoft.restapi.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.databinding.FragmentVerificationBinding
import uz.xsoft.restapi.data.repositories.VerificationRepository
import uz.xsoft.restapi.utils.TextWatchers
import uz.xsoft.restapi.utils.extensions.isConnected
import uz.xsoft.restapi.viewModels.main.VerificationViewModel
import uz.xsoft.restapi.viewModels.main.VerificationViewModelFactory

class VerificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentVerificationBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val viewModelFactory = VerificationViewModelFactory(
            App.instance,
            VerificationRepository()
        )
        val safeArgs = VerificationFragmentArgs.fromBundle(arguments!!)
        binding.textPhoneNumber = safeArgs.phone
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(VerificationViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.toast.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.onCompletedToast()
            }
        })
        viewModel.navigateToHomeFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController()
                    .navigate(VerificationFragmentDirections.actionVerificationFragmentToHomeActivity())
                viewModel.onCompletedNavigateToHomeActivity()
                requireActivity().finish()
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
        binding.textWatcher = TextWatchers.INSTANCE
        return binding.root
    }
}