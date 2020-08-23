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
import uz.xsoft.restapi.utils.TextWatchers
import uz.xsoft.restapi.viewModels.main.SignInViewModel
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.databinding.FragmentSignInBinding
import uz.xsoft.restapi.data.repositories.SignInRepository
import uz.xsoft.restapi.utils.extensions.isConnected
import uz.xsoft.restapi.viewModels.main.SignInViewModelFactory

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignInBinding.inflate(inflater)
        val viewModelFactory =
            SignInViewModelFactory(App.instance, SignInRepository())
        binding.lifecycleOwner = this
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SignInViewModel::class.java)
        viewModel.navigateToHomeFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController()
                    .navigate(EnterFragmentDirections.actionEnterFragmentToHomeActivity())
                viewModel.onNavigateHomeFragmentCompleted()
                requireActivity().finish()
            }
        })
        viewModel.navigateToForgotPasswordFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(EnterFragmentDirections.actionEnterFragmentToForgotPasswordPhoneFragment())
                viewModel.onNavigateToForgotPasswordFragmentCompleted()
            }
        })
        binding.viewModel = viewModel
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
        binding.textWatcher = TextWatchers.INSTANCE
        return binding.root
    }
}