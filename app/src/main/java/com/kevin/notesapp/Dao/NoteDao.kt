package com.kevin.notesapp.Dao

import androidx.room.*
import com.kevin.notesapp.Entity.NotesEntity

@Dao
interface NoteDao {

    @Insert
    fun addNote(noteDao: NotesEntity)

    @Query("SELECT * FROM notes")
    fun getNotes() : List<NotesEntity>

    @Update
    fun updateNote(notesEntity: NotesEntity)

    @Delete
    fun deleteNote(notesEntity: NotesEntity)

//    @Query("DELETE FROM notes")
//    fun DeleteNote(notesEntity: NotesEntity)
}