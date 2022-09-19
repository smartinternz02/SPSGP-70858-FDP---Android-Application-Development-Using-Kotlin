package com.aro.contactsapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContactViewModelFactory(private val repository: ContactRepository)
    :ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(repository) as T
    }
}