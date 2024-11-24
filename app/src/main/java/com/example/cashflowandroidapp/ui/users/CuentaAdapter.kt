package com.example.cashflowandroidapp.ui.users
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashflowandroidapp.R
import com.example.cashflowandroidapp.data.model.entities.CntBancosResponse
import retrofit2.Response

class CuentaAdapter(private val cuentas: List<CntBancosResponse>) : RecyclerView.Adapter<CuentaAdapter.CuentaViewHolder>() {


    inner class CuentaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreBanco: TextView = itemView.findViewById(R.id.bankName)
        val numeroCuenta: TextView = itemView.findViewById(R.id.bankNumber)
        val balanceCuenta: TextView = itemView.findViewById(R.id.bankBalance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuentaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_cuenta_card, parent, false)
        return CuentaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuentaViewHolder, position: Int) {
        val cuenta = cuentas.get(position)
        if (cuenta != null) {
            holder.nombreBanco.text = cuenta.egre_banc_nomb_alte
            holder.numeroCuenta.text = "Cuenta N: " + cuenta.egre_banc_num_cuen
            holder.balanceCuenta.text = cuenta.cuenta_monto + " $"
        }


    }

    override fun getItemCount() = cuentas.size
}
