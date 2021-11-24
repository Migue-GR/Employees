package com.employees.view.home

import com.employees.R
import com.employees.databinding.FragmentHomeBinding
import com.employees.model.local.Employee
import com.employees.utils.BaseFragmentBinding
import com.employees.utils.ext.navigateSafelyTo
import com.employees.utils.ext.navigateSafelyWithDirections
import com.employees.utils.ext.showToast
import com.employees.utils.setOnSingleClickListener
import com.employees.view.adapter.EmployeeAdapter
import com.employees.viewmodel.EmployeeViewModel
import com.employees.viewmodel.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>() {
    override fun bind() = FragmentHomeBinding.inflate(layoutInflater)

    private val userViewModel: UserViewModel by viewModel()
    private val employeeViewModel: EmployeeViewModel by viewModel()

    override fun onViewCreated() {
        observeCurrentUser()
        observeEmployees()
        employeeViewModel.getEmployees()
        binding.btnLogout.setOnSingleClickListener { showDialogToLogout() }
    }

    private fun observeCurrentUser() = userViewModel.user.observe(viewLifecycleOwner, { user ->
        if (user == null) {
            navigateSafelyTo(R.id.action_homeFragment_to_loginFragment)
        }
    })

    private fun observeEmployees() =
        employeeViewModel.employees.observe(viewLifecycleOwner, { employees ->
            if (employees == null) {
                showToast("No hay empleados")
            } else {
                binding.rvEmployees.adapter = EmployeeAdapter(employees, ::onEmployeeClicked)
            }
        })

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
            userViewModel.logout()
        }.show()
}