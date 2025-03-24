package com.example

import android.content.Context
import androidx.room.*
import com.example.notedatabase.NoteDao
import com.example.notedatabase.NoteModel

    @Database(entities = [NoteModel::class], version = 1 , exportSchema = false)
    abstract class NoteDatabaseProvider : RoomDatabase() {
        abstract fun noteDao(): NoteDao

        companion object {
            @Volatile
            private var INSTANCE: NoteDatabaseProvider? = null

            fun getDatabase(context: Context): NoteDatabaseProvider {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabaseProvider::class.java,
                        "note_database"
                    ).build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }

