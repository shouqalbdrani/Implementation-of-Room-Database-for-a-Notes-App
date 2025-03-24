package com.example

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notedatabase.Note
import com.example.notedatabase.NoteDao

class NoteDatabaseProvider {

    @Database(entities = [Note::class], version = 1 , exportSchema = false)
    abstract class NoteDatabase : RoomDatabase() {
        abstract fun noteDao(): NoteDao

        companion object {
            @Volatile
            private var INSTANCE: NoteDatabase? = null

            fun getDatabase(context: Context): NoteDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "note_database"
                    ).build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }
}
