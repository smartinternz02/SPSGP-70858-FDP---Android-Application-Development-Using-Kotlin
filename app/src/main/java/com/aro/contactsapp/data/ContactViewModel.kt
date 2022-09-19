package com.aro.contactsapp.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    fun insert(item: Contact) = CoroutineScope(Dispatchers.Default).launch {
        repository.insert(item)
    }

    fun delete(item: Contact) = CoroutineScope(Dispatchers.Default).launch {
        repository.delete(item)
    }

    fun allContacts() = repository.allContacts()
}
