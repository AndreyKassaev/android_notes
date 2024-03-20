package com.kassaev.notes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.kassaev.notes.domain.NoteModel
import com.kassaev.notes.Util

@Entity(
    tableName = "note",
    foreignKeys = [
        ForeignKey(
            entity = FolderEntity::class,
            parentColumns = arrayOf("folder_id"),
            childColumns = arrayOf("folder_id")
        )
    ]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "note_id") val noteId: Int,
    var text: String,
    val date: Long,
    @ColumnInfo(name = "folder_id") val folderId: Int
){

    fun toModel(): NoteModel {
        return NoteModel(
            noteId = this.noteId,
            text = this.text,
            date = Util.toLocalDateTime(this.date),
            folderId = this.folderId
        )
    }
}