package com.example.projetopdm4bim.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun HouseCard(
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