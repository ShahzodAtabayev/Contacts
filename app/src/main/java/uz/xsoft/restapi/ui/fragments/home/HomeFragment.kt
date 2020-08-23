package uz.xsoft.restapi.ui.fragments.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import uz.xsoft.restapi.R
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.databinding.FragmentHomeBinding
import uz.xsoft.restapi.data.repositories.HomeRepository
import uz.xsoft.restapi.ui.adapters.ContactAdapter
import uz.xsoft.restapi.ui.dialogs.EditDialog
import uz.xsoft.restapi.viewModels.home.HomeViewModel
import uz.xsoft.restapi.viewModels.home.HomeViewModelFactory
import uz.xsoft.restapi.utils.extensions.isConnected

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater)
        val viewModelFactory = HomeViewModelFactory(App.instance, HomeRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        binding.viewModel = viewModel
        val adapter = ContactAdapter(R.menu.contact_menu)
        binding.listContact.adapter = adapter
        viewModel.toast.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.onCompletedToast()
            }
        })

        adapter.setOnEditListener { data ->
            val dialog = EditDialog(requireContext())
            dialog.loadData(data)
            dialog.setOnEditClickListener {
                viewModel.btEdit(it)
                dialog.cancel()
            }
            dialog.show()
        }
        adapter.setOnDeleteListener { data ->
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Are you sure?")
            dialog.setPositiveButton("ok") { d, _ ->
                viewModel.btRemove(data)
                d.cancel()
            }
            dialog.setNegativeButton("cancel") { d, _ ->
                d.cancel()
            }
            dialog.show()
        }
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
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllContact()
    }

}