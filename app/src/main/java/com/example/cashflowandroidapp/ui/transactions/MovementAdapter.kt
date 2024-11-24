package com.example.cashflowandroidapp.ui.transactions
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashflowandroidapp.R
import com.example.cashflowandroidapp.data.model.entities.Movement

class MovementAdapter(private val movements: List<Movement>) :
    RecyclerView.Adapter<MovementAdapter.MovementViewHolder>() {

    // ViewHolder para el RecyclerView
    inner class MovementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movement, parent, false)
        return MovementViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovementViewHolder, position: Int) {

        val movement = movements[position]
        holder.tvTitle.text = movement.concepto
        holder.tvDate.text = movement.fecha

        //  aquei se Configura el monto y se cambia el color  si es ingreso o gasto
        holder.tvAmount.text = "${if (movement.tipo == 1) "+" else "-"} ${String.format("%.2f",movement.monto)} $"
        holder.tvAmount.setTextColor(
            holder.itemView.context.getColor(if (movement.tipo == 1) R.color.green else R.color.red)
        )
    }

    override fun getItemCount(): Int = movements.size
}
