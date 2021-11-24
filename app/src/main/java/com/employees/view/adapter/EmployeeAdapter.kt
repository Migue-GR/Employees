package com.employees.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.employees.databinding.ItemEmployeesBinding
import com.employees.model.local.Employee
import com.employees.utils.setOnSingleClickListener

class EmployeeAdapter(
    private val items: List<Employee>,
    private val onItemClicked: (Employee) -> Unit
) : RecyclerView.Adapter<EmployeeViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder(
            ItemEmployeesBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClicked
        )
    }
}

class EmployeeViewHolder(
    private val binding: ItemEmployeesBinding,
    private val onItemClicked: (Employee) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Employee) {
        binding.tvName.text = item.firstName
        binding.cardItem.setOnSingleClickListener { onItemClicked(item) }
    }
}