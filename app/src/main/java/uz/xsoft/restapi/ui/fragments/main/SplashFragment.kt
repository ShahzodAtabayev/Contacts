package uz.xsoft.restapi.ui.fragments.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.xsoft.restapi.data.local.storage.LocalStorage
import uz.xsoft.restapi.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSplashBinding.inflate(inflater)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (LocalStorage.instance.isLogin) {
                this.findNavController()
                    .navigate(SplashFragmentDirections.actionSplashFragmentToHomeActivity())
                requireActivity().finish()
            } else {
                this.findNavController()
                    .navigate(SplashFragmentDirections.actionSplashFragmentToEnterFragment())
            }
        }, 2000)
        return binding.root
    }
}