package com.kassaev.notes.data

import com.kassaev.notes.domain.NoteModel
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val noteDao: INoteDao
) {

    suspend fun createNote(noteEntity: NoteEntity) =
        noteDao.getNoteById(noteDao.insertNote(noteEntity).toInt())?.toModel()


    fun getAllNotes(folderId: Int): Flow<List<NoteEntity>> =
        noteDao.selectAllNotes(folderId)

    suspend fun createOrUpdateNote(noteEntity: NoteEntity) =
        noteDao.upsertNote(noteEntity)


    suspend fun getNoteById(noteId: Int): NoteEntity? =
        noteDao.getNoteById(noteId)

    suspend fun deleteNote(noteEntity: NoteEntity) =
        noteDao.deleteNote(noteEntity)


    suspend fun deleteAllNotesFromRecycleBin() =
        noteDao.deleteAllNotesFromRecycleBin()


}