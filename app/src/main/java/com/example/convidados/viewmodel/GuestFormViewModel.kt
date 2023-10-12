package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository

class GuestFormViewModel(application: Application) :AndroidViewModel(application){

    private val repository = GuestRepository.getInstance(application)

    private val guestsModel = MutableLiveData<GuestModel>()
    val guests : LiveData<GuestModel> = guestsModel

    private val _saveGuest = MutableLiveData<String>()
    val saveGuests : LiveData<String> = _saveGuest


    fun save(guest :GuestModel){
        if(guest.id== 0){
            if(repository.insert(guest)) {
                _saveGuest.value = "Inserção com sucesso"
            }else{
                _saveGuest.value =  "Falha"
            }
        }else{
            if(repository.update(guest)) {
                _saveGuest.value = "Atualização com sucesso"
            }
            else{
                _saveGuest.value =  "Falha"
            }
        }
    }

    fun get(id: Int){
       guestsModel.value = repository.get(id)
    }
}