package com.example.notedatabase

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun NoteEditScreen(
    navController: NavHostController,
    noteViewModel: NoteViewModel,
    noteId: Long?
) {

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(noteId) { // fetch and display note
        noteId?.let { // checks if not null
            val note = noteViewModel.getNoteById(it)?.run{ // if the noteId not null
                title = title // for update
                content = content
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(35.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (title.isNotEmpty() && content.isNotEmpty()) {
                if (noteId != null) {
                    noteViewModel.updateNote(noteId, title = title, content = content)
                } else {
                    noteViewModel.addNote(title = title, content = content)
                }
                noteViewModel.saveData(title = title, content = content)
                navController.popBackStack()  // Navigate back immediately after saving
            } else {
                println("Fill all the fields")
            }
        }) {
            Text("Save Note")
        }
    }
}


