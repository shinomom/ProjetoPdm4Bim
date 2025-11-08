package com.example.projetopdm4bim.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
fun HouseDetailScreen(
    casa: Casa,
    doces: List<Doce>,
    onVoltar: () -> Unit,
    onAdicionarDoce: () -> Unit,
    onExcluirDoce: (Doce) -> Unit
) {
    val totalDoces = doces.sumOf { it.quantidade }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detalhes da Casa",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdicionarDoce,
                containerColor = Color(0xFFE91E63),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, "Adicionar Doce")
            }
        },
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            item {
                // Cabeçalho de informações
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = casa.nome,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4CAF50)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = casa.endereco ?: "Endereço não informado",
                            fontSize = 16.sp,
                            color = Color(0xFF757575)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Estatísticas da casa
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            ColunaEstatistica(
                                valor = casa.nivelSusto.toString(),
                                rotulo = "Susto",
                                cor = Color(0xFFE91E63)
                            )
                            ColunaEstatistica(
                                valor = totalDoces.toString(),
                                rotulo = "Doces",
                                cor = Color(0xFF4CAF50)
                            )
                            ColunaEstatistica(
                                valor = doces.size.toString(),
                                rotulo = "Tipos",
                                cor = Color(0xFF2196F3)
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Doces Coletados",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF4CAF50)
                )
            }

            if (doces.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(56.dp)
                            )
                            Text(
                                "Nenhum doce ainda",
                                color = Color(0xFF4CAF50),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                "Toque no botão + para registrar\nseus primeiros doces",
                                color = Color(0xFF757575),
                                fontSize = 14.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(doces) { doce ->
                    CartaoDoce(
                        doce = doce,
                        onExcluir = { onExcluirDoce(doce) }
                    )
                }
            }
        }
    }
}

@Composable
fun ColunaEstatistica(
    valor: String,
    rotulo: String,
    cor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = valor,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = cor
        )
        Text(
            text = rotulo,
            fontSize = 12.sp,
            color = Color(0xFF757575),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CartaoDoce(
    doce: Doce,
    onExcluir: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = doce.tipo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4CAF50)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Quantidade: ${doce.quantidade}",
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }

            IconButton(
                onClick = onExcluir,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Excluir doce",
                    tint = Color(0xFFE91E63)
                )
            }
        }
    }
}
