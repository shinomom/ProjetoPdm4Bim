package com.example.projetopdm4bim

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.projetopdm4bim.data.entities.Casa
import com.example.projetopdm4bim.data.entities.Doce
import com.example.projetopdm4bim.ui.AddCasaScreen
import com.example.projetopdm4bim.ui.HomeScreen
import com.example.projetopdm4bim.ui.theme.ProjetoPDM4BimTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjetoPDM4BimTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current

                    // Estado para as listas
                    var casas by remember { mutableStateOf(emptyList<Casa>()) }
                    val doces = remember { emptyList<Doce>() }

                    // Estado para controlar o formulário
                    var mostrarFormulario by remember { mutableStateOf(false) }

                    // Tela principal
                    HomeScreen(
                        casas = casas,
                        doces = doces,
                        onAdicionarCasa = {
                            mostrarFormulario = true
                        },
                        onCasaClicada = { casa ->
                            Toast.makeText(context, "📱 Detalhes da: ${casa.nome}", Toast.LENGTH_SHORT).show()
                        },
                        onExcluirCasa = { casa ->
                            casas = casas.filter { it.id != casa.id }
                            Toast.makeText(context, "🗑️ ${casa.nome} excluída!", Toast.LENGTH_SHORT).show()
                        }
                    )

                    // Dialog do formulário de adicionar casa
                    if (mostrarFormulario) {
                        AlertDialog(
                            onDismissRequest = { mostrarFormulario = false },
                            content = {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    shape = MaterialTheme.shapes.extraLarge
                                ) {
                                    AddCasaScreen(
                                        onCasaAdicionada = { novaCasa ->
                                            casas = casas + novaCasa
                                            mostrarFormulario = false
                                            Toast.makeText(
                                                context,
                                                "🏠 ${novaCasa.nome} adicionada!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        onCancelar = {
                                            mostrarFormulario = false
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
