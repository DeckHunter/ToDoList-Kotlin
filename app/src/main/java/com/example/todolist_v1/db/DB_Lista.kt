package com.example.todolist_v1.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolist_v1.Tarefas

class DB_Lista (context: Context) : SQLiteOpenHelper(context,
    Tarefas.DB_NAME, null,
    Tarefas.DB_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE " + Tarefas.CriarTarefa.TABELA + " ( " +
                Tarefas.CriarTarefa._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Tarefas.CriarTarefa.TILULO_TAREFA + " TEXT NOT NULL);"

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + Tarefas.CriarTarefa.TABELA)
        onCreate(db)
    }
}