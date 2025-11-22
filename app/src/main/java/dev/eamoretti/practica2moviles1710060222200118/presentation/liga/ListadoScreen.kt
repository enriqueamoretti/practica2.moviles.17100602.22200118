package dev.eamoretti.practica2moviles1710060222200118.presentation.liga

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import dev.eamoretti.practica2moviles1710060222200118.data.Team

@Composable
fun ListadoScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    var teams by remember { mutableStateOf<List<Team>>(emptyList()) }

    // Escuchar cambios en tiempo real
    LaunchedEffect(Unit) {
        db.collection("teams").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                teams = snapshot.documents.map { doc ->
                    Team(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        foundationYear = doc.getString("foundationYear") ?: "",
                        titleCount = doc.getString("titleCount") ?: "",
                        imageUrl = doc.getString("imageUrl") ?: ""
                    )
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Equipos", fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))

        LazyColumn(modifier = Modifier.weight(1f).padding(vertical = 16.dp)) {
            items(teams) { team ->
                TeamItem(team)
            }
        }

        Button(
            onClick = { navController.navigate("registroLiga") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nuevo Registro")
        }
    }
}

@Composable
fun TeamItem(team: Team) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = team.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = team.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = team.foundationYear, fontSize = 14.sp)
            }
            Text(text = team.titleCount, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}