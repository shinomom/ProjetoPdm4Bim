package com.example.projetopdm4bim.data.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.projetopdm4bim.data.entities.Doce

class DoceDao(private val db: SQLiteDatabase) {

    companion object {
        const val TABLE_NAME = "doce"
        const val COLUMN_ID = "id"
        const val COLUMN_CASA_ID = "casa_id"
        const val COLUMN_TIPO = "tipo"
        const val COLUMN_QUANTIDADE = "quantidade"

        const val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CASA_ID INTEGER NOT NULL,
                $COLUMN_TIPO TEXT NOT NULL,
                $COLUMN_QUANTIDADE INTEGER DEFAULT 1,
                FOREIGN KEY($COLUMN_CASA_ID) REFERENCES casa(id) ON DELETE CASCADE
            )
        """
    }

    fun inserir(doce: Doce): Long {
        val values = ContentValues().apply {
            put(COLUMN_CASA_ID, doce.idCasa)
            put(COLUMN_TIPO, doce.tipo)
            put(COLUMN_QUANTIDADE, doce.quantidade)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun atualizar(doce: Doce): Int {
        val values = ContentValues().apply {
            put(COLUMN_CASA_ID, doce.idCasa)
            put(COLUMN_TIPO, doce.tipo)
            put(COLUMN_QUANTIDADE, doce.quantidade)
        }
        return db.update(
            TABLE_NAME,
            values,
            "$COLUMN_ID = ?",
            arrayOf(doce.id.toString())
        )
    }

    fun excluir(id: Long): Int {
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun excluirPorCasa(casaId: Long): Int {
        return db.delete(TABLE_NAME, "$COLUMN_CASA_ID = ?", arrayOf(casaId.toString()))
    }

    fun buscarPorId(id: Long): Doce? {
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        return cursor.use {
            if (it.moveToFirst()) cursorParaDoce(it) else null
        }
    }

    fun buscarPorCasa(casaId: Long): List<Doce> {
        val doces = mutableListOf<Doce>()
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_CASA_ID = ?",
            arrayOf(casaId.toString()),
            null, null, null
        )
        cursor.use {
            while (it.moveToNext()) {
                doces.add(cursorParaDoce(it))
            }
        }
        return doces
    }

    fun buscarTodos(): List<Doce> {
        val doces = mutableListOf<Doce>()
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        cursor.use {
            while (it.moveToNext()) {
                doces.add(cursorParaDoce(it))
            }
        }
        return doces
    }

    private fun cursorParaDoce(cursor: Cursor): Doce {
        return Doce(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            idCasa = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CASA_ID)),
            tipo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO)),
            quantidade = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTIDADE))
        )
    }
}
