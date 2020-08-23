package uz.xsoft.restapi.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import uz.xsoft.restapi.R
import uz.xsoft.restapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(binding.toolBarMain)
        binding.lifecycleOwner = this
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupNav(binding, navController)
        title = ""
    }

    private fun setupNav(binding: ActivityMainBinding, nav: NavController) {
        nav.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.verificationFragment -> {
                    showActionBar()
                    showBackButton()
                    hideLogo()
                    showTitle("Верификасия ")
                }
                R.id.splashFragment -> {
                    hideActionBar()
                }
                R.id.enterFragment -> {
                    hideActionBar()
                }
                R.id.homeFragment -> {
                    showActionBar()
                }
                R.id.forgotPasswordFragment -> {
                    showActionBar()
                    showBackButton()
                    hideLogo()
                    showTitle(applicationContext.getString(R.string.textForgotPassword))
                }
                R.id.forgotPasswordPhoneFragment -> {
                    showActionBar()
                    showBackButton()
                    hideLogo()
                    showTitle(applicationContext.getString(R.string.textForgotPassword))
                }
            }
        }
    }

    private fun hideLogo() {
        binding.logo.visibility = View.GONE
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun hideBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun showTitle(s: String) {
        binding.title.text = s
    }

    private fun showActionBar() {
        supportActionBar?.show()
        binding.appBarLayout.visibility = View.VISIBLE
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
        binding.appBarLayout.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}