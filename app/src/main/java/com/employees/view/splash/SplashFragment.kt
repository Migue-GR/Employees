package com.employees.view.splash

import com.employees.R
import com.employees.databinding.FragmentSplashBinding
import com.employees.utils.AppSession.showGlobalError
import com.employees.utils.BaseFragmentBinding
import com.employees.utils.EmployeesException
import com.employees.utils.ext.navigateSafelyTo
import com.employees.utils.ext.observeWith
import com.employees.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragmentBinding<FragmentSplashBinding>() {
    override fun bind() = FragmentSplashBinding.inflate(layoutInflater)

    private val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated() {
        getCurrentUser()
    }

    private fun getCurrentUser() = viewModel.getCurrentUser().observeWith(viewLifecycleOwner, {
        if (it == null) {
            navigateSafelyTo(R.id.action_splashFragment_to_loginFragment)
        } else {
            navigateSafelyTo(R.id.action_splashFragment_to_homeFragment)
        }
    }, ::showError)

    private fun showError(error: EmployeesException) {
        showGlobalError(error)
    }
}