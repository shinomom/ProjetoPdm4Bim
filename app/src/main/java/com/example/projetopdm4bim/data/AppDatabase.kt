package com.example.projetopdm4bim.data

import android.content.Context
import com.example.projetopdm4bim.data.dao.TrickOrTreatDatabaseHelper
import com.example.projetopdm4bim.data.entities.Casa
import com.example.projetopdm4bim.data.entities.Doce

class AppDatabase(private val context: Context) {

    private val dbHelper = TrickOrTreatDatabaseHelper(context)

    // Operações para Casa
    fun inserirCasa(casa: Casa): Long {
        return dbHelper.getCasaDao().inserir(casa)
    }

    fun atualizarCasa(casa: Casa): Int {
        return dbHelper.getCasaDao().atualizar(casa)
    }

    fun excluirCasa(id: Long): Int {
        // Exclui também os doces associados à casa
        dbHelper.getDoceDao().excluirPorCasa(id)
        return dbHelper.getCasaDao().excluir(id)
    }

    fun buscarCasaPorId(id: Long): Casa? {
        return dbHelper.getCasaDao().buscarPorId(id)
    }

    fun buscarTodasCasas(): List<Casa> {
        return dbHelper.getCasaDao().buscarTodos()
    }

    // Operações para Doce
    fun inserirDoce(doce: Doce): Long {
        return dbHelper.getDoceDao().inserir(doce)
    }

    fun atualizarDoce(doce: Doce): Int {
        return dbHelper.getDoceDao().atualizar(doce)
    }

    fun excluirDoce(id: Long): Int {
        return dbHelper.getDoceDao().excluir(id)
    }

    fun buscarDocePorId(id: Long): Doce? {
        return dbHelper.getDoceDao().buscarPorId(id)
    }

    fun buscarDocesPorCasa(casaId: Long): List<Doce> {
        return dbHelper.getDoceDao().buscarPorCasa(casaId)
    }

    fun buscarTodosDoces(): List<Doce> {
        return dbHelper.getDoceDao().buscarTodos()
    }

    fun fechar() {
        dbHelper.close()
    }
}