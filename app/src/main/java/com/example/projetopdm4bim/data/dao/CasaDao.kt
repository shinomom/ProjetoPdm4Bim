package com.example.projetopdm4bim.data.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.projetopdm4bim.data.entities.Casa

class CasaDao(private val db: SQLiteDatabase) {

    companion object {
        const val TABLE_NAME = "casa"
        const val COLUMN_ID = "id"
        const val COLUMN_NOME = "nome"
        const val COLUMN_ENDERECO = "endereco"
        const val COLUMN_NIVEL_SUSTO = "nivel_susto"
        const val COLUMN_VISITADA_EM = "visitada_em"

        const val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOME TEXT NOT NULL,
                $COLUMN_ENDERECO TEXT,
                $COLUMN_NIVEL_SUSTO INTEGER CHECK($COLUMN_NIVEL_SUSTO BETWEEN 0 AND 5),
                $COLUMN_VISITADA_EM INTEGER
            )
        """
    }

    fun inserir(casa: Casa): Long {
        val values = ContentValues().apply {
            put(COLUMN_NOME, casa.nome)
            put(COLUMN_ENDERECO, casa.endereco)
            put(COLUMN_NIVEL_SUSTO, casa.nivelSusto)
            put(COLUMN_VISITADA_EM, casa.visitadaEm)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun atualizar(casa: Casa): Int {
        val values = ContentValues().apply {
            put(COLUMN_NOME, casa.nome)
            put(COLUMN_ENDERECO, casa.endereco)
            put(COLUMN_NIVEL_SUSTO, casa.nivelSusto)
            put(COLUMN_VISITADA_EM, casa.visitadaEm)
        }
        return db.update(
            TABLE_NAME,
            values,
            "$COLUMN_ID = ?",
            arrayOf(casa.id.toString())
        )
    }

    fun excluir(id: Long): Int {
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun buscarPorId(id: Long): Casa? {
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        return cursor.use {
            if (it.moveToFirst()) cursorParaCasa(it) else null
        }
    }

    fun buscarTodos(): List<Casa> {
        val casas = mutableListOf<Casa>()
        val cursor = db.query(
            TABLE_NAME,
            null, null, null, null, null,
            "$COLUMN_VISITADA_EM DESC"
        )
        cursor.use {
            while (it.moveToNext()) {
                casas.add(cursorParaCasa(it))
            }
        }
        return casas
    }

    private fun cursorParaCasa(cursor: Cursor): Casa {
        return Casa(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)),
            endereco = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENDERECO)),
            nivelSusto = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NIVEL_SUSTO)),
            visitadaEm = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_VISITADA_EM))
        )
    }
}