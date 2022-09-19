package com.aro.contactsapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "number")
    var number: String? = null

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id  : Int = 0
}