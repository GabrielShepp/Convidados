package com.example.convidados.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel

class GuestViewHolder(private val bind: RowGuestBinding) :RecyclerView.ViewHolder (bind.root) {

    fun bind(guest: GuestModel){
        bind.textNameList.text = guest.name

    }
}