package com.example.notedatabase

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current // because the compose does not have access to context
    val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(context)) // access the Room database

    NavHost(navController, startDestination = "noteList") {
        composable("noteList") {
            NoteListScreen(navController, noteViewModel)
        }

        // Updated navigation to pass noteId as an argument
        composable("noteEdit/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLongOrNull()
            NoteEditScreen(navController, noteViewModel, noteId)
        }
    }
}
