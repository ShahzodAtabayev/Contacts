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
import uz.xsoft.restapi.data.repositories.SignUpRepository
import uz.xsoft.restapi.viewModels.main.SignUpViewModel
import uz.xsoft.restapi.viewModels.main.SignUpViewModelFactory
import uz.xsoft.restapi.utils.TextWatchers
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.databinding.FragmentSignUpBinding
import uz.xsoft.restapi.utils.extensions.isConnected

class SignUpFragment : Fragment() {
    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val app = App.instance
        binding = FragmentSignUpBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val viewModelFactory =
            SignUpViewModelFactory(app, SignUpRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(SignUpViewModel::class.java)
        viewModel.navigateToVerificationFragment.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController()
                    .navigate(EnterFragmentDirections.actionShowVerification(it))
                viewModel.onNavigateVerificationFragmentCompleted()
            }
        })
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

        binding.viewModel = viewModel
        binding.textWatcher = TextWatchers.INSTANCE
        return binding.root
    }

}