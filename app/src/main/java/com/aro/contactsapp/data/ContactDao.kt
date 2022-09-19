package com.aro.contactsapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aro.contactsapp.data.Contact

@Dao
interface ContactDao {
    // CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact?)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_table WHERE contact_table.name == :cname")
    suspend fun getContact(cname: String): Contact

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)
}
