package com.example.convidados.view.viewholder

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener

class GuestViewHolder(private val bind: RowGuestBinding, private val listener: OnGuestListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(guest: GuestModel) {
        bind.textNameList.text = guest.name

        bind.textNameList.setOnClickListener {
            listener.onClick(guest.id)
        }

        bind.textNameList.setOnLongClickListener {

            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção de convidados")
                .setMessage("Tem certeza que desseja remover?")
                .setPositiveButton(
                    "SIM"
                ) { _, _ -> listener.onDelete(guest.id) }
                .create()
                .show()


            true
        }
    }

}