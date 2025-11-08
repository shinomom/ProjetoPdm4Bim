package com.example.projetopdm4bim.data.entities

data class Casa(
    val id: Long = 0,
    val nome: String,
    val endereco: String?,
    val nivelSusto: Int, // 0-5
    val visitadaEm: Long = System.currentTimeMillis()
)