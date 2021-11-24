package com.employees.view.splash

import com.employees.R
import com.employees.databinding.FragmentSplashBinding
import com.employees.utils.BaseFragmentBinding
import com.employees.utils.ext.navigateSafelyTo
import com.employees.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragmentBinding<FragmentSplashBinding>() {
    override fun bind() = FragmentSplashBinding.inflate(layoutInflater)

    private val userViewModel: UserViewModel by viewModel()

    override fun onViewCreated() {
        observeCurrentUser()
        userViewModel.getCurrentUser()
    }

    private fun observeCurrentUser() = userViewModel.user.observe(viewLifecycleOwner, { user ->
        if (user == null) {
            navigateSafelyTo(R.id.action_splashFragment_to_loginFragment)
        } else {
            navigateSafelyTo(R.id.action_splashFragment_to_homeFragment)
        }
    })
}