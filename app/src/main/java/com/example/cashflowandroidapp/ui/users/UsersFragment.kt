package com.example.cashflowandroidapp.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cashflowandroidapp.databinding.FragmentUsersBinding

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val userviewmodel: UsersViewModel by viewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val usersViewModel =
            ViewModelProvider(this).get(UsersViewModel::class.java)

        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /*val listaCuentas = listOf(
            Cuenta("BCP HUARAZ", "**** **** **** 3445", "$323.54"),
            Cuenta("INTERBANK LIMA", "**** **** **** 5678", "$1250.20"),
            Cuenta("SCOTIABANK AREQUIPA", "**** **** **** 9876", "$750.00")
        )*/
        userviewmodel.listOfCuentas.observe(viewLifecycleOwner) { listaCuentas ->
            var montoTotal = 0.0

            listaCuentas?.forEach { cuenta ->
                println(cuenta)
                val monto = cuenta.cuenta_monto.toDoubleOrNull() ?: 0.0
                montoTotal += monto
                println(montoTotal)
            }


            binding.balanceAmount.text = String.format("%.2f", montoTotal) + " $"


            binding.recyclerViewCuentas.apply {
                adapter = CuentaAdapter(listaCuentas ?: emptyList())
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        userviewmodel.fetchCuentas(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}