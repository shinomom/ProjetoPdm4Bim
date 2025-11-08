package com.example.projetopdm4bim.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projetopdm4bim.data.entities.Casa
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCasaScreen(
    onCasaAdicionada: (Casa) -> Unit,
    onCancelar: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var nivelSusto by remember { mutableStateOf(3) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Adicionar Nova Casa",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome da Casa") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = endereco,
            onValueChange = { endereco = it },
            label = { Text("Endereço") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Nível de Susto: $nivelSusto")
        Slider(
            value = nivelSusto.toFloat(),
            onValueChange = { nivelSusto = it.toInt() },
            valueRange = 1f..5f,
            steps = 4,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onCancelar,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = {
                    if (nome.isNotBlank()) {
                        val novaCasa = Casa(
                            id = System.currentTimeMillis(),
                            nome = nome,
                            endereco = endereco,
                            nivelSusto = nivelSusto
                        )
                        onCasaAdicionada(novaCasa)
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = nome.isNotBlank()
            ) {
                Text("Adicionar")
            }
        }
    }
}