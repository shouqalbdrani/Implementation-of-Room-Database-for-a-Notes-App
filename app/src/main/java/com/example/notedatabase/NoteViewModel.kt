package com.example.notedatabase

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.lifecycleScope
import com.example.NoteDatabaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// NoteViewModel
class NoteViewModel(context: Context) : ViewModel() {

    private val noteDatabase = NoteDatabaseProvider.getDatabase(context)
    private val noteDao = noteDatabase.noteDao()

    private val _viewStateFlow: MutableStateFlow<List<NoteModel>> = MutableStateFlow(emptyList())
    val viewStateFlow: StateFlow<List<NoteModel>> = _viewStateFlow.asStateFlow()
    // show the notes
    init {
        observeNotes()
    }

    private fun observeNotes() {
        viewModelScope.launch {
            noteDao.getAllNotes().collect { notesList ->
                _viewStateFlow.value = notesList
            }
        }
    }
    fun saveData(title: String, content: String) {
        val noteModelValue = NoteModel(title = title, content = content)
        viewModelScope.launch {
            noteDao.insert(noteModelValue)
            println("Inserted Note: $noteModelValue")
        }
    }

    // add Note
    fun addNote(title: String, content: String) {
        val noteModelValue = NoteModel(title = title, content = content)
        viewModelScope.launch {
            noteDao.insert(noteModelValue)

        }
    }

    // update Note
    fun updateNote(noteId: Long, title: String, content: String) {
        viewModelScope.launch {
            val updatedNote = NoteModel(id = noteId, title = title, content = content)
            noteDao.update(updatedNote)
        }
    }

    suspend fun getNoteById(noteId: Long): NoteModel? {
        return noteDao.getNoteById(noteId)
    }

    fun deleteNoteById(noteId: Long) {
        viewModelScope.launch {
            noteDao.deleteById(noteId)
        }
    }
}

class NoteViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

