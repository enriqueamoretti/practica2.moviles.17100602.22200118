package dev.eamoretti.practica2moviles1710060222200118.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.eamoretti.practica2moviles1710060222200118.presentation.liga.ListadoScreen
import dev.eamoretti.practica2moviles1710060222200118.presentation.liga.RegistroLigaScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    // Iniciamos en el registro según el flujo lógico de la práctica
    NavHost(navController = navController, startDestination = "registroLiga") {
        composable("registroLiga") {
            RegistroLigaScreen(navController)
        }
        composable("listaEquipos") {
            ListadoScreen(navController)
        }
    }
}