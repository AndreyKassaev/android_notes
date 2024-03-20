package com.kassaev.notes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Dao
interface INoteDao {

    @Query("SELECT * FROM note WHERE folder_id=:folderId ORDER BY date DESC")
    fun selectAllNotes(folderId: Int): Flow<List<NoteEntity>>

    @Upsert
    suspend fun upsertNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM note WHERE note_id=:noteId")
    suspend fun getNoteById(noteId: Int): NoteEntity?

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity): Int

    @Query("DELETE FROM note WHERE folder_id=1")
    suspend fun deleteAllNotesFromRecycleBin()

    @Insert
    suspend fun insertNote(noteEntity: NoteEntity): Long

}