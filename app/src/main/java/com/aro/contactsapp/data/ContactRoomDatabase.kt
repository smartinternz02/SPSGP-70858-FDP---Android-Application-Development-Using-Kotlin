package com.aro.contactsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Contact::class], version = 2)
abstract class ContactRoomDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var instance: ContactRoomDatabase? = null
        private val LOCK = Any()

        private val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE contact_table")
                database.execSQL("CREATE TABLE IF NOT EXISTS contact_table( name TEXT, number TEXT UNIQUE, id INT PRIMARY KEY)")
            }
        }


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }

        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ContactRoomDatabase::class.java,
                "ContactsDatabase.db"
            )
                .addMigrations(migration_1_2)
                .addCallback(sRoomDatabaseCallback).build()

        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
/*
                val contactDao = instance!!.contactDao()
                CoroutineScope(Dispatchers.IO).launch {
                    contactDao.deleteAll()
                    contactDao.insert(Contact("James Bond", "687496198"))
                    contactDao.insert(Contact("Jeremy", "9578631489"))
                    contactDao.insert(Contact("David", "8647531616"))
                    contactDao.insert(Contact("Jason", "7356654135"))
                    contactDao.insert(Contact("Greg", "91645"))
                    contactDao.insert(Contact("James", "213986"))
                    contactDao.insert(Contact("Greg", "098765"))
                    contactDao.insert(Contact("Helena", "40679765"))
                    contactDao.insert(Contact("Carimo", "768345"))
                    contactDao.insert(Contact("Silo", "34425"))
                    contactDao.insert(Contact("Santos", "6665"))
                    contactDao.insert(Contact("Litos", "53455"))
                    contactDao.insert(Contact("Karate", "96534"))
                    contactDao.insert(Contact("Guerra", "158258"))
                    contactDao.insert(Contact("Gema", "78130"))
                }
*/
            }
        }
    }
}
