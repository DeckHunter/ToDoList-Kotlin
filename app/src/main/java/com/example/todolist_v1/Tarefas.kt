package com.example.todolist_v1

import android.provider.BaseColumns

class Tarefas{
    companion object {
        val DB_NAME = "com.example.todolist_v1"
        val DB_VERSION = 1
    }

    class CriarTarefa : BaseColumns {

        companion object {
            val TABELA = "Atividades"
            val TILULO_TAREFA = "titulo"
            val _ID = BaseColumns._ID
        }
    }
}