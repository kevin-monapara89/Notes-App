package com.kevin.notesapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kevin.notesapp.Dao.NoteDao
import com.kevin.notesapp.Entity.NotesEntity

@Database(entities = [NotesEntity::class], version = 2)
abstract class RoomDB : RoomDatabase(){

    companion object {
        fun init(context: Context): RoomDB {
            var db = Room.databaseBuilder(context, RoomDB::class.java, "Notes.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

            return db

        }
    }
    abstract fun note() : NoteDao
}