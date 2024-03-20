package com.kassaev.notes.domain

import com.kassaev.notes.data.NoteEntity

data class NoteModel(
    val noteId: Int,
    var text: String,
    var date: String,
    var folderId: Int
) {

    constructor(text: String): this(
        noteId = 0,
        text = text,
        date = "",
        folderId = 0
    )

    fun toEntity(): NoteEntity {
        return NoteEntity(
            noteId = this.noteId,
            text = this.text,
            date = System.currentTimeMillis(),
            folderId = this.folderId
        )
    }
}