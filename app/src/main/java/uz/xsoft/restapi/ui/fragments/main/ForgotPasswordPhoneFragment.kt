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
import uz.xsoft.restapi.data.repositories.ForgotPasswordPhoneRepository
import uz.xsoft.restapi.databinding.FragmentForgotPasswordPhoneBinding
import uz.xsoft.restapi.utils.TextWatchers
import uz.xsoft.restapi.utils.extensions.isConnected
import uz.xsoft.restapi.viewModels.main.ForgotPasswordPhoneViewModel
import uz.xsoft.restapi.viewModels.main.ForgotPasswordPhoneViewModelFactory

class ForgotPasswordPhoneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentForgotPasswordPhoneBinding.inflate(inflater)
        val viewModelFactory = ForgotPasswordPhoneViewModelFactory(App.instance, ForgotPasswordPhoneRepository())
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ForgotPasswordPhoneViewModel::class.java)
        binding.viewModel = viewModel
        binding.textWatcher = TextWatchers.INSTANCE
        binding.lifecycleOwner = this
        viewModel.navigateToResetPasswordFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController()
                    .navigate(ForgotPasswordPhoneFragmentDirections.actionForgotPasswordPhoneFragmentToForgotPasswordFragment())
                viewModel.onNavigateResetPasswordFragmentCompleted()
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
        viewModel.toast.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.onCompletedToast()
            }
        })
        return binding.root
    }
}