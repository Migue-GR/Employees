package com.employees.view.detail

import com.employees.databinding.FragmentDetailBinding
import com.employees.model.local.Employee
import com.employees.utils.BaseFragmentBinding
import com.employees.utils.ext.setCircleImageWithGlide

class DetailFragment : BaseFragmentBinding<FragmentDetailBinding>() {
    override fun bind() = FragmentDetailBinding.inflate(layoutInflater)

    private lateinit var employee: Employee

    override fun onViewCreated() {
        employee = DetailFragmentArgs.fromBundle(requireArguments()).employee
        binding.fullName.text = employee.fullName
        binding.description.text = employee.description
        binding.rating.text = employee.rating.toString()
        binding.profilePicture.setCircleImageWithGlide(uri = employee.imageUri)
    }
}