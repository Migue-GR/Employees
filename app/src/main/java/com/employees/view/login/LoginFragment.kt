package com.employees.view.login

import android.util.Patterns
import android.util.Patterns.EMAIL_ADDRESS
import com.employees.R
import com.employees.databinding.FragmentLoginBinding
import com.employees.utils.BaseFragmentBinding
import com.employees.utils.UiEventsManager
import com.employees.utils.UiEventsManager.shouldUpdateEmployees
import com.employees.utils.ext.navigateSafelyTo
import com.employees.utils.setOnSingleClickListener
import com.employees.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragmentBinding<FragmentLoginBinding>() {
    override fun bind() = FragmentLoginBinding.inflate(layoutInflater)

    private val userViewModel: UserViewModel by viewModel()

    override fun onViewCreated() {
        observeCurrentUser()
        binding.btnLogin.setOnSingleClickListener { validateFieldsToLogin() }
    }

    private fun validateFieldsToLogin() {
        val email = binding.inputEmail.editText?.text.toString().trim()
        val password = binding.inputPassword.editText?.text.toString().trim()
        when {
            email.isEmpty() -> {
                binding.inputEmail.error = getString(R.string.email_is_mandatory)
            }
            !EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.inputEmail.error = getString(R.string.wrong_email_format)
            }
            password.isEmpty() -> {
                binding.inputPassword.error = getString(R.string.mandatory_password)
            }
            else -> userViewModel.login(email, password)
        }
    }

    private fun observeCurrentUser() = userViewModel.user.observe(viewLifecycleOwner, { user ->
        if (user != null) {
            shouldUpdateEmployees = true
            navigateSafelyTo(R.id.action_loginFragment_to_homeFragment)
        }
    })
}