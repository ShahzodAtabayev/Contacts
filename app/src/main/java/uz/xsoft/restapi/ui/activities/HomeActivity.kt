package uz.xsoft.restapi.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import uz.xsoft.restapi.R
import uz.xsoft.restapi.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val navController = findNavController(R.id.home_fragment)
        setSupportActionBar(binding.toolBar)
        setupNav(binding)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        title = ""
    }

    private fun setupNav(binding: ActivityHomeBinding) {
        val navController = findNavController(R.id.home_fragment)
        binding.bottomNavigation
            .setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    showLogo()
                    hideBackButton()
                    showBottomNav()
                    showTitle("")
                }
                R.id.basketFragment -> {
                    hideBackButton()
                    showBottomNav()
                    hideLogo()
                    showTitle(applicationContext.getString(R.string.basket))
                }
                R.id.profileFragment -> {
                    hideBackButton()
                    showBottomNav()
                    hideLogo()
                    showTitle(applicationContext.getString(R.string.profile))
                }
                R.id.addContactFragment -> {
                    hideLogo()
                    showTitle(applicationContext.getString(R.string.addContact))
                    showBackButton()
                    hideBottomNav()
                }
                R.id.editProfileFragment -> {
                    hideLogo()
                    showTitle(applicationContext.getString(R.string.editProfile))
                    showBackButton()
                    hideBottomNav()
                }
                R.id.settingsFragment -> {
                    hideLogo()
                    showTitle(applicationContext.getString(R.string.settings))
                    showBackButton()
                    hideBottomNav()
                }
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigation.visibility = View.GONE
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun hideBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun hideLogo() {
        binding.imageLogo.visibility = View.GONE
    }

    private fun showTitle(s: String) {
        binding.title.text = s
    }

    private fun showLogo() {
        binding.imageLogo.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.home_fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}