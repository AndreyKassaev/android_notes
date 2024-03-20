package com.kassaev.notes.di

import android.content.Context
import androidx.room.Room
import com.kassaev.notes.data.INoteDao
import com.kassaev.notes.data.NoteDatabase
import com.kassaev.notes.data.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: INoteDao): NoteRepository = NoteRepository(noteDao)

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase): INoteDao = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room
            .databaseBuilder(
                context = context,
                NoteDatabase::class.java,
                name = "note_db.db"
            )
            .createFromAsset("database/note_db.db")
            .build()
    }
}