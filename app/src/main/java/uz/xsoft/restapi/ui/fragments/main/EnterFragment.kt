package uz.xsoft.restapi.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.xsoft.restapi.R
import uz.xsoft.restapi.ui.adapters.SignPagerAdapter
import uz.xsoft.restapi.databinding.FragmentEnterBinding
import com.google.android.material.tabs.TabLayoutMediator

class EnterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEnterBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val pages = listOf(
            SignUpFragment(),
            SignInFragment()
        )
        binding.pager.adapter = SignPagerAdapter(pages, requireActivity().supportFragmentManager, lifecycle)
        binding.pager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = resources.getString(R.string.textSigUp)
                }
                1 -> {
                    tab.text = resources.getString(R.string.textSigIn)
                }
            }
        }.attach()
        return binding.root
    }
}