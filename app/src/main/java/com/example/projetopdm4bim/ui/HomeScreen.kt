package com.example.projetopdm4bim.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetopdm4bim.data.entities.Casa
import com.example.projetopdm4bim.data.entities.Doce


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    casas: List<Casa>,
    doces: List<Doce>,
    onAdicionarCasa: () -> Unit,
    onCasaClicada: (Casa) -> Unit,
    onExcluirCasa: (Casa) -> Unit
) {
    val totalDoces = doces.sumOf { it.quantidade }
    val totalCasas = casas.size

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Coleção de Doces",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF4CAF50)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdicionarCasa,
                containerColor = Color(0xFFE91E63),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, "Adicionar Casa")
            }
        },
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Cartões de Estatísticas - CORRIGIDO
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Primeiro cartão
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                ) {
                    CartaoEstatistica(
                        titulo = "Casas Visitadas",
                        valor = totalCasas.toString(),
                        icone = Icons.Default.Home,
                        cor = Color(0xFF4CAF50)
                    )
                }

                // Segundo cartão
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp)
                ) {
                    CartaoEstatistica(
                        titulo = "Doces Coletados",
                        valor = totalDoces.toString(),
                        icone = Icons.Default.Star,
                        cor = Color(0xFFE91E63)
                    )
                }
            }

            // Lista de Casas
            Text(
                text = "Minhas Paradas",
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4CAF50)
            )

            if (casas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(72.dp)
                        )
                        Text(
                            "Comece sua jornada!",
                            color = Color(0xFF4CAF50),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            "Toque no botão rosa para adicionar\nsua primeira casa",
                            color = Color(0xFF757575),
                            fontSize = 14.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(casas) { casa ->
                        CartaoCasa(
                            casa = casa,
                            doces = doces.filter { it.idCasa == casa.id },
                            onClick = { onCasaClicada(casa) },
                            onExcluir = { onExcluirCasa(casa) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartaoEstatistica(
    titulo: String,
    valor: String,
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    cor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icone,
                contentDescription = null,
                tint = cor,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                valor,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = cor
            )
            Text(
                titulo,
                fontSize = 12.sp,
                color = Color(0xFF757575),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartaoCasa(
    casa: Casa,
    doces: List<Doce>,
    onClick: () -> Unit,
    onExcluir: () -> Unit
) {
    val totalDocesCasa = doces.sumOf { it.quantidade }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = casa.nome,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = casa.endereco ?: "Sem endereço",
                        fontSize = 14.sp,
                        color = Color(0xFF757575)
                    )
                }

                // Indicador de susto
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Nível de susto",
                        fontSize = 12.sp,
                        color = Color(0xFFE91E63)
                    )
                    Text(
                        text = "${casa.nivelSusto}/5",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE91E63)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Barra de informações
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🎁 $totalDocesCasa doces",
                    fontSize = 14.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Medium
                )

                IconButton(
                    onClick = onExcluir,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Excluir casa",
                        tint = Color(0xFFE91E63)
                    )
                }
            }
        }
    }
}
