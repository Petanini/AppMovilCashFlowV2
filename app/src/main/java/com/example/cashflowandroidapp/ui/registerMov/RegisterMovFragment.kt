package com.example.cashflowandroidapp.ui.registerMov

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.cashflowandroidapp.data.model.entities.Categoria
import com.example.cashflowandroidapp.data.model.entities.CntBancosResponse
import com.example.cashflowandroidapp.data.model.entities.Movement
import com.example.cashflowandroidapp.data.model.entities.MovementInsert
import com.example.cashflowandroidapp.data.model.entities.Naturaleza
import com.example.cashflowandroidapp.databinding.FragmentRegistermovBinding
import com.example.cashflowandroidapp.ui.shared.SharedViewModel
import com.example.cashflowandroidapp.ui.users.UsersViewModel

class RegisterMovFragment : Fragment() {

    private var _binding: FragmentRegistermovBinding? = null
    private lateinit var dateselector: TextView
    private lateinit var spinnerCuenta: Spinner

    private lateinit var spinnerCategoria: Spinner

    private lateinit var btnGuardar: Button

    private lateinit var radiosOpt: RadioGroup

    private lateinit var txtMonto: TextView
    private lateinit var fieldConceptos: EditText

    lateinit var registerMovViewModel: RegisterMovViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var uvw : UsersViewModel

    private var idCuenta: Int = 0
    private var idCategoria: Int = 0


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        registerMovViewModel =
            ViewModelProvider(this).get(RegisterMovViewModel::class.java)

        _binding = FragmentRegistermovBinding.inflate(inflater, container, false)
        val root: View = binding.root

        spinnerCuenta = binding.spCuenta

        spinnerCategoria = binding.spCat

        btnGuardar = binding.btnGuardar
        radiosOpt = binding.groupOptions

        txtMonto = binding.txtMonto
        fieldConceptos = binding.editTextTextMultiLine

        uvw = UsersViewModel()
        registerMovViewModel.fetchCat()

        uvw.fetchCuentas(1)



        registerMovViewModel.listOfCategoria.observe(viewLifecycleOwner) { categoriaList ->
            val categoriaAdapter = categoriaList?.let {
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    it.map { it.egre_cate_opc }
                )
            }
            if (categoriaAdapter != null) {
                categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spinnerCategoria.adapter = categoriaAdapter

            spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val categoriaSeleccionada = categoriaList?.get(position)
                    val categoriaId = categoriaSeleccionada?.id_egre_cate
                    if (categoriaId != null){
                        idCategoria = categoriaId
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }



        uvw.listOfCuentas.observe(viewLifecycleOwner){ CuentasList ->

            val cuentasAdapter = CuentasList?.let {
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    it.map { it.egre_banc_nomb_alte }
                )
            }
            if(cuentasAdapter != null){
                cuentasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spinnerCuenta.adapter = cuentasAdapter

            spinnerCuenta.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    val cuentaSeleccionada = CuentasList?.get(position)
                    val accountId = cuentaSeleccionada?.id_egre_banc
                    if (accountId != null) {
                        idCuenta = accountId
                    }

                    println("ID seleccionado: $idCuenta")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateselector = binding.txtDate
        dateselector.setOnClickListener { showDatePickerDialog() }

        registerMovViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                registerMovViewModel.errorMessage.postValue(null)
            }
        }

        sharedViewModel.idBanco.observe(viewLifecycleOwner) { idBanco ->
            if (idBanco != 0) {
                uvw.listOfCuentas.observe(viewLifecycleOwner) { cuentasList ->

                    val position = cuentasList?.indexOfFirst { it.id_egre_banc == idBanco } ?: -1
                    if (position != -1) {
                        spinnerCuenta.setSelection(position)
                    } else {
                        Toast.makeText(requireContext(), "Cuenta no encontrada en el listado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



        btnGuardar.setOnClickListener {
            if (validarCampos()) {
                val selectedRadioButton = view?.findViewById<RadioButton>(radiosOpt.checkedRadioButtonId)
                val selectedTag = selectedRadioButton?.tag as? String

                if (selectedTag != null) {
                    val movimiento = MovementInsert(
                        fieldConceptos.text.toString(),
                        idCuenta,
                        dateselector.text.toString(),
                        txtMonto.text.toString().toDouble(),
                        selectedTag.toInt(),
                        idCategoria
                    )

                    println("Movimiento a enviar: $movimiento")
                    registerMovViewModel.addMovement(movimiento)

                    registerMovViewModel.movementRegistered.observe(viewLifecycleOwner) { result ->
                        if (result != null) {
                            Toast.makeText(requireContext(), "Movimiento guardado con éxito", Toast.LENGTH_SHORT).show()
                            fieldConceptos.text.clear()
                            dateselector.text = ""
                            txtMonto.text = ""
                            radiosOpt.clearCheck()
                        } else {
                            Toast.makeText(requireContext(), "Error al guardar el movimiento", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Por favor, selecciona una opción válida", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(childFragmentManager, "datePicker")
    }
    fun onDateSelected(day: Int, month: Int, year: Int) {
        dateselector.text = "$day/${month + 1}/$year"
    }

    fun validarCampos(): Boolean{
        var aproved = true
         if (txtMonto.text.isEmpty()){
            aproved = false
            return aproved
        } else if (dateselector.text.isEmpty()){
            aproved = false
            return aproved
        } else if (fieldConceptos.text.isEmpty()){
            aproved = false
            return aproved
        } else if(idCuenta == 0){
            aproved = false
            return aproved
        } else if (idCategoria == 0 || idCategoria == -1){
            aproved = false
             return aproved
        }
        return aproved
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}