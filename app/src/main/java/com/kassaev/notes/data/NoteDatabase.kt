package com.kassaev.notes.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        NoteEntity::class,
        FolderEntity::class
    ],
)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): INoteDao
}