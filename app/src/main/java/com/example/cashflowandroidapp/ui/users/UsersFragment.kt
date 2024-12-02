package com.example.cashflowandroidapp.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cashflowandroidapp.data.interfaces.RecyclerViewEvent
import com.example.cashflowandroidapp.data.model.entities.CntBancosResponse
import com.example.cashflowandroidapp.databinding.FragmentUsersBinding

class UsersFragment : Fragment(), RecyclerViewEvent {

    private var _binding: FragmentUsersBinding? = null
    private val userviewmodel: UsersViewModel by viewModels()
    private var miListener: RecyclerViewEvent = this
    private var miListaCuentas: List<CntBancosResponse>? = null

    private var idBank : Int = 0


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
            miListaCuentas = listaCuentas
            var montoTotal = 0.0

            listaCuentas?.forEach { cuenta ->
                println(cuenta)
                val monto = cuenta.cuenta_monto.toDoubleOrNull() ?: 0.0
                montoTotal += monto
                println(montoTotal)
            }


            binding.balanceAmount.text = String.format("%.2f", montoTotal) + " $"


            binding.recyclerViewCuentas.apply {
                adapter = CuentaAdapter(listaCuentas ?: emptyList(), miListener)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        userviewmodel.fetchCuentas(1)

        _binding?.viewMovementButton?.setOnClickListener(View.OnClickListener {
            if(idBank == 0){
                Toast.makeText(requireContext(), "Debes seleccionar un banco", Toast.LENGTH_LONG).show()
            } else{

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(Position: Int) {
        val cuenta = miListaCuentas?.get(Position)

        if (cuenta != null) {
            if(_binding != null){
                _binding!!.txtAccName.text = cuenta.egre_banc_nomb_alte + " :"
                _binding!!.txtAccName.textSize = 20f
                _binding!!.balanceAmount.text = cuenta.cuenta_monto + " $"
                idBank = cuenta.id_egre_banc
            }
        }
    }
}