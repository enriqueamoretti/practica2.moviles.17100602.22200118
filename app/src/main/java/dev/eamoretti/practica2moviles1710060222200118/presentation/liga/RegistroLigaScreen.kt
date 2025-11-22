package dev.eamoretti.practica2moviles1710060222200118.presentation.liga

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroLigaScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var titles by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    val context = LocalContext.current
    // Instancia básica de Firestore
    val db = FirebaseFirestore.getInstance()

    fun saveTeam() {
        if (name.isEmpty() || year.isEmpty()) {
            Toast.makeText(context, "Completa los datos", Toast.LENGTH_SHORT).show()
            return
        }

        val teamData = hashMapOf(
            "name" to name,
            "foundationYear" to year,
            "titleCount" to titles,
            "imageUrl" to imageUrl
        )

        db.collection("teams")
            .add(teamData)
            .addOnSuccessListener {
                Toast.makeText(context, "Equipo guardado", Toast.LENGTH_SHORT).show()
                // Navegar a la lista y borrar el registro de la pila (opcional)
                navController.navigate("listaEquipos") {
                    popUpTo("registroLiga") { inclusive = true }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro Liga 1", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 20.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre del equipo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Año de fundación") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = titles, onValueChange = { titles = it }, label = { Text("Número de títulos ganados") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("URL de la imagen del equipo") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { saveTeam() }, modifier = Modifier.weight(1f)) {
                Text("Registrar Equipo")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    navController.navigate("listaEquipos") {
                        popUpTo("registroLiga") { inclusive = true }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Ver Listado")
            }
        }
    }
}