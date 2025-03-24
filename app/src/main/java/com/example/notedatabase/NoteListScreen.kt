package com.example.notedatabase

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun NoteListScreen(navController: NavHostController, noteViewModel: NoteViewModel) {
    val notes by noteViewModel.viewStateFlow.collectAsState(emptyList()) // take the flow of notes , default value for list

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(notes) { note ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    ) {
                        Text(text = note.title)
                        Text(text = note.content)
                    }

                    IconButton(onClick = {
                        navController.navigate("noteEdit/${note.id}")
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Note")
                    }

                    IconButton(onClick = {
                        noteViewModel.deleteNoteById(note.id)
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Note")
                    }
                }
            }
        }

        Button(
            onClick = {
                navController.navigate("noteEdit/0") // sends 0 as the note id
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
        ) {
            Text("Add Note")
        }
    }
}
