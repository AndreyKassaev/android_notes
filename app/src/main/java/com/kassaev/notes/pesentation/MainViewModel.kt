package com.kassaev.notes.pesentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.notes.data.NoteRepository
import com.kassaev.notes.domain.NoteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NoteRepository): ViewModel() {

    private val _noteList = MutableStateFlow(emptyList<NoteModel>())
    private val _noteRecycleBinList = MutableStateFlow(emptyList<NoteModel>())
    val noteList = _noteList.asStateFlow()
    val noteRecycleBinList = _noteRecycleBinList.asStateFlow()

    //refactoring start
    var openAlertDialogFAB by  mutableStateOf(false)
    var openAlertDialogLongClick by  mutableStateOf(false)
    var exitWithSave by mutableStateOf(true)
    var noteIdToDelete: Int? = null
    private var currentNote: NoteModel? = null
    var textFieldValue by mutableStateOf("")
    var selectedDrawerItem by mutableIntStateOf(0)

    suspend fun getNoteById(noteId: Int): NoteModel? =
        viewModelScope.async {
            currentNote = repository.getNoteById(noteId = noteId)?.toModel()
            currentNote
        }.await()

    fun createOrUpdateNote(){
        //Is TextField not blank?
        if (textFieldValue.isBlank()){
            if (currentNote != null){
                deleteNote()
            }
            return
        }

        //Update existing note
        if (currentNote != null){
            //Is text changed for existing note?
            if(currentNote!!.text != textFieldValue){
                currentNote?.text = textFieldValue
                viewModelScope.launch{
                    repository.createOrUpdateNote(currentNote!!.toEntity())
                }
            }
        }else{ //Or create new
            viewModelScope.launch{
                currentNote = repository.createNote(
                    NoteModel(text = textFieldValue).toEntity()
                )
            }
        }
    }

    fun moveNoteToRecycleBin(){
        textFieldValue.isNotBlank().also {
            //Update existing note
            currentNote?.let {
                viewModelScope.launch{
                    repository.createOrUpdateNote(currentNote!!.copy(
                        folderId = 1
                    ).toEntity())
                }
            }
        }
    }

    fun moveNoteToRecycleBinFomDialog(noteId: Int){
        //Update existing note
        viewModelScope.launch{
            repository.createOrUpdateNote(getNoteById(noteId)!!.copy(
                folderId = 1
            ).toEntity())
        }
    }

    //refactoring end

    init {
        setNoteList()
        setNoteRecycleBinList()
    }
    fun setNoteList(){
        viewModelScope.launch{
            repository.getAllNotes(0).flowOn(Dispatchers.IO).collect{ noteListFromDB ->
                _noteList.update { noteListInVM ->
                    noteListFromDB.map {noteEntity ->
                        noteEntity.toModel()
                    }
                }
            }
        }
    }
    fun setNoteRecycleBinList(){
        viewModelScope.launch{
            repository.getAllNotes(1).flowOn(Dispatchers.IO).collect{ noteListFromDB ->
                _noteRecycleBinList.update { noteListInVM ->
                    noteListFromDB.map {noteEntity ->
                        noteEntity.toModel()
                    }
                }
            }
        }
    }
    fun restoreNote(){
        currentNote?.let {
            viewModelScope.launch{
                repository.createOrUpdateNote(currentNote!!.copy(
                    folderId = 0
                ).toEntity())
            }
        }
    }

    fun deleteAllNotesFromRecycleBin(){
        viewModelScope.launch {
            repository.deleteAllNotesFromRecycleBin()
        }
    }

    fun deleteNote(){
        viewModelScope.launch {
            currentNote?.noteId?.let {noteId ->
                repository.getNoteById(noteId)?.let { noteEntity ->
                    repository.deleteNote(noteEntity)
                }
            }
        }
    }

    fun deleteNote(noteId: Int){
        viewModelScope.launch {
            repository.deleteNote(repository.getNoteById(noteId)!!)
        }
    }

    fun closeDialogLongClick(){
        openAlertDialogLongClick = false
    }

    fun openDialogLongClick() {
        openAlertDialogLongClick = true
    }

    fun closeDialogFAB(){
        openAlertDialogFAB = false
    }

    fun openDialogFAB() {
        openAlertDialogFAB = true
    }

    fun setNoteToDelete(noteId: Int){
        noteIdToDelete = noteId
    }
}