package com.aro.contactsapp.data

class ContactRepository(private val db: ContactRoomDatabase) {

    suspend fun insert(item:Contact) = db.contactDao().insert(item)
    suspend fun delete(item:Contact) = db.contactDao().delete(item)

    fun allContacts() = db.contactDao().getAllContacts()

}
