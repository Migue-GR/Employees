package com.employees.view.login

import android.util.Patterns.EMAIL_ADDRESS
import com.employees.R
import com.employees.databinding.FragmentLoginBinding
import com.employees.utils.AppSession.shouldUpdateEmployees
import com.employees.utils.AppSession.showGlobalError
import com.employees.utils.BaseFragmentBinding
import com.employees.utils.EmployeesException
import com.employees.utils.ext.navigateSafelyTo
import com.employees.utils.ext.observeWith
import com.employees.utils.setOnSingleClickListener
import com.employees.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragmentBinding<FragmentLoginBinding>() {
    override fun bind() = FragmentLoginBinding.inflate(layoutInflater)

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated() {
        binding.btnLogin.setOnSingleClickListener {
            validateFieldsToLogin()
        }
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
            else -> login(email, password)
        }
    }

    private fun login(
        email: String,
        password: String
    ) = viewModel.login(email, password).observeWith(viewLifecycleOwner, { user ->
        if (user != null) {
            shouldUpdateEmployees = true
            navigateSafelyTo(R.id.action_loginFragment_to_homeFragment)
        }
    }, ::showError)

    private fun showError(error: EmployeesException) {
        showGlobalError(error)
    }
}