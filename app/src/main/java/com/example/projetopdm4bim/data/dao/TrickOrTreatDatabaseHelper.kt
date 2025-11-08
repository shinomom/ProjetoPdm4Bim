package com.example.projetopdm4bim.data.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TrickOrTreatDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "trick_or_treat.db"
        const val DATABASE_VERSION = 1
    }

    private val casaDao = CasaDao(writableDatabase)
    private val doceDao = DoceDao(writableDatabase)

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CasaDao.CREATE_TABLE)
        db.execSQL(DoceDao.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DoceDao.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${CasaDao.TABLE_NAME}")
        onCreate(db)
    }

    fun getCasaDao(): CasaDao = casaDao
    fun getDoceDao(): DoceDao = doceDao
}