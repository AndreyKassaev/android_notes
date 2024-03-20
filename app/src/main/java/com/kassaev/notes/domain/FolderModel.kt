package com.kassaev.notes.domain

import com.kassaev.notes.data.FolderEntity

data class FolderModel(
    val folderId: Int,
    val name: String
) {

    fun toEntity(): FolderEntity{
        return FolderEntity(
            folderId = this.folderId,
            name = this.name
        )
    }
}