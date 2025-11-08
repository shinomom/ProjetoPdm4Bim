package com.example.projetopdm4bim.data.entities

data class Doce(
    val id: Long = 0,
    val idCasa: Long,
    val tipo: String,
    val quantidade: Int = 1
)
