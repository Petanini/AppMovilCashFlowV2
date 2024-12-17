package com.example.cashflowandroidapp.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cashflowandroidapp.data.model.entities.Movement
import com.example.cashflowandroidapp.databinding.FragmentTransactionsBinding
import com.example.cashflowandroidapp.ui.shared.SharedViewModel

class TransactionsFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentTransactionsBinding? = null
    private lateinit var transactionsViewModel: TransactionsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        transactionsViewModel =
            ViewModelProvider(this).get(TransactionsViewModel::class.java)

        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionsViewModel.listOfMovements.observe(viewLifecycleOwner){listaMovimientos ->
            binding.recyclerViewMovements.apply {
                println(listaMovimientos)
                adapter = MovementAdapter(listaMovimientos)
                layoutManager = LinearLayoutManager(requireContext())
            }
            var posAmount = 0.0
            var negAmount = 0.0

            for (item in listaMovimientos) {
                if (item.tipo == 1){
                    posAmount += item.monto
                } else{
                    negAmount += item.monto
                }
            }

            val posAmountFormatted = "+ $posAmount $"
            val negAmountFormatted = "- $negAmount $"

            binding.income.text = posAmountFormatted
            binding.expense.text = negAmountFormatted


        }


        sharedViewModel.idBanco.observe(viewLifecycleOwner) { idBanco ->
            if (idBanco != 0) {
                transactionsViewModel.setIdBanco(idBanco)
                transactionsViewModel.fetchMovements()
            } else {
                transactionsViewModel.fetchMovements()
            }
        }

        sharedViewModel.montoBanco.observe(viewLifecycleOwner){montoBanco ->
            transactionsViewModel.setBancoMonto(montoBanco)
            binding.totalBalance.text = transactionsViewModel.banco_monto.value
        }
        /*transactionsViewModel.id_banco.observe(viewLifecycleOwner) { idBanco ->
            transactionsViewModel.fetchMovements()
            println("ID Banco Observado: $idBanco")
        }*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}