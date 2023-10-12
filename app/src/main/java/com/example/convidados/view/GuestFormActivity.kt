package com.example.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity() ,View.OnClickListener{

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel

    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding  = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[GuestFormViewModel::class.java]

        binding.buttonSave.setOnClickListener(this)
        binding.radioPresent.isChecked = true


        observe()
        loadDate()

    }

    private fun observe() {
        viewModel.guests.observe(this) {
            binding.editName.setText(it.name)
            if(it.presence){
                binding.radioPresent.isChecked = true
            }else{
                binding.radioAbsent.isChecked = true
            }
        }


        viewModel.saveGuests.observe(this){
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            if(it != ""){
                finish()
            }
        }
    }

    private fun loadDate() {
        val bundle =  intent.extras
        if(bundle!=  null){
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }

    override fun onClick(button: View) {
        if (button.id == R.id.button_save){
            val name = binding.editName.text.toString()
            val presence = binding.radioPresent.isChecked

            val model = GuestModel(guestId,name, presence)
            viewModel.save(model)

        }
    }
}