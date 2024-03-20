package com.kassaev.notes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kassaev.notes.domain.FolderModel

@Entity(
    tableName = "folder"
)
data class FolderEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "folder_id") val folderId: Int,
    val name: String
){
    fun toModel(): FolderModel {
        return FolderModel(
            folderId = this.folderId,
            name = this.name
        )
    }
}
