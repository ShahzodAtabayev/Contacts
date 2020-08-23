package uz.xsoft.restapi.ui.fragments.home

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.data.local.storage.LocalStorage
import uz.xsoft.restapi.data.repositories.ProfileRepository
import uz.xsoft.restapi.databinding.FragmentProfileBinding
import uz.xsoft.restapi.utils.Constants
import uz.xsoft.restapi.viewModels.home.ProfileViewModel
import uz.xsoft.restapi.viewModels.home.ProfileViewModelFactory

class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater)
        binding.profileData = LocalStorage.instance.profileData
        val viewModelFactory = ProfileViewModelFactory(App.instance, ProfileRepository())
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.exit.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Are you sure you want to exit the program?")
                dialog.setPositiveButton("ok") { d, _ ->
                    requireActivity().finish()
                    d.cancel()
                }
                dialog.setNegativeButton("cancel") { d, _ ->
                    d.cancel()
                }
                dialog.show()
                viewModel.onCompletedExit()
            }
        })
        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                startActivity(
                    Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse(String.format("tel:%s", Constants.call_center))
                    )
                )
                viewModel.onCompletedContacts()
            }
        })
        viewModel.navigateToEditProfile.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
                viewModel.onCompletedEditProfile()
            }
        })
        viewModel.navigateToSettings.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
                viewModel.onCompletedSettings()
            }
        })
        return binding.root
    }
}