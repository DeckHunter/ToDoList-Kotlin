package com.example.todolist_v1

import android.content.ContentValues
import android.content.DialogInterface
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.todolist_v1.db.DB_Lista

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var db_lista: DB_Lista
    private lateinit var lista_itens: ListView
    private var adpter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lista_itens = findViewById(R.id.list_todo)

        db_lista = DB_Lista(this)
        updateUI()
    }

    fun deleteTask(view: View) {
        val parent = view.getParent() as View
        val taskTextView = parent.findViewById<TextView>(R.id.item_titulo)
        val task = taskTextView.text.toString()
        val db = db_lista.writableDatabase
        db.delete(Tarefas.CriarTarefa.TABELA,
            Tarefas.CriarTarefa.TILULO_TAREFA + " = ?",
            arrayOf(task))
        db.close()
        updateUI()
    }

    private fun updateUI() {
        val taskList = ArrayList<String>()
        val db = db_lista.readableDatabase
        val cursor = db.query(Tarefas.CriarTarefa.TABELA,
            arrayOf(Tarefas.CriarTarefa._ID, Tarefas.CriarTarefa.TILULO_TAREFA), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val idx = cursor.getColumnIndex(Tarefas.CriarTarefa.TILULO_TAREFA)
            taskList.add(cursor.getString(idx))
        }

        if (adpter == null) {
            adpter = ArrayAdapter(this,
                R.layout.item_todo_list,
                R.id.item_titulo,
                taskList)
            lista_itens.adapter = adpter
        } else {
            adpter?.clear()
            adpter?.addAll(taskList)
            adpter?.notifyDataSetChanged()
        }

        cursor.close()
        db.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.adicionar_item -> {
                val taskEditText = EditText(this)
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Adiconar Um Novo Item")
                    .setView(taskEditText)
                    .setPositiveButton("Adiconar", DialogInterface.OnClickListener { dialog, which ->
                        val task = taskEditText.text.toString()
                        val db = db_lista.getWritableDatabase()
                        val values = ContentValues()
                        values.put(Tarefas.CriarTarefa.TILULO_TAREFA, task)
                        db.insertWithOnConflict(Tarefas.CriarTarefa.TABELA,
                            null,
                            values,
                            SQLiteDatabase.CONFLICT_REPLACE)
                        db.close()
                        updateUI()
                    })
                    .setNegativeButton("Cancelar", null)
                    .create()
                dialog.show()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}
