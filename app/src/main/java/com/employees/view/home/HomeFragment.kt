package com.employees.view.home

import com.employees.R
import com.employees.databinding.FragmentHomeBinding
import com.employees.model.local.Employee
import com.employees.utils.AppSession.shouldUpdateEmployees
import com.employees.utils.AppSession.showGlobalError
import com.employees.utils.BaseFragmentBinding
import com.employees.utils.EmployeesException
import com.employees.utils.ext.navigateSafelyTo
import com.employees.utils.ext.navigateSafelyWithDirections
import com.employees.utils.ext.observeWith
import com.employees.utils.ext.showToast
import com.employees.utils.setOnSingleClickListener
import com.employees.view.adapter.EmployeeAdapter
import com.employees.viewmodel.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>() {
    override fun bind() = FragmentHomeBinding.inflate(layoutInflater)

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated() {
        getEmployees()
        binding.btnLogout.setOnSingleClickListener { showDialogToLogout() }
    }

    private fun getEmployees() {
        if (shouldUpdateEmployees) {
            shouldUpdateEmployees = false
            viewModel.getEmployees().observeWith(viewLifecycleOwner, { employees ->
                if (employees.isNullOrEmpty()) {
                    showToast(getString(R.string.there_is_no_employees))
                } else {
                    binding.rvEmployees.adapter = EmployeeAdapter(employees, ::onEmployeeClicked)
                }
            }, ::showError)
        }
    }

    private fun onEmployeeClicked(employee: Employee) {
        val directions = HomeFragmentDirections.actionHomeFragmentToDetailFragment(employee)
        navigateSafelyWithDirections(directions)
    }

    private fun showDialogToLogout() = MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.logout)
        .setMessage(R.string.dialog_to_logout_message)
        .setNegativeButton(getString(R.string.cancel)) { _, _ ->
        }
        .setPositiveButton(resources.getString(R.string.yes_im_sure)) { _, _ ->
            logout()
        }.show()

    private fun logout() = viewModel.logout().observeWith(viewLifecycleOwner, {
        if (it == true) {
            navigateSafelyTo(R.id.action_homeFragment_to_loginFragment)
        }
    }, ::showError)

    private fun showError(error: EmployeesException) {
        showGlobalError(error)
    }
}