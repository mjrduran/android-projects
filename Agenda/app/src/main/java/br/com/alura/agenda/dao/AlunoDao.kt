package br.com.alura.agenda.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.alura.agenda.modelo.Aluno
import java.util.*

/**
 * Created by moacir on 05/12/16.
 */
class AlunoDao(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = """ CREATE TABLE Alunos (
                        id INTEGER PRIMARY KEY,
                        nome TEXT NOT NULL,
                        endereco TEXT,
                        telefone TEXT,
                        site TEXT,
                        nota REAL); """
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS Alunos;"
        db?.execSQL(sql)
        onCreate(db)
    }

    fun insere(aluno: Aluno) {
        val db = writableDatabase

        val contentValues = getContentValues(aluno)

        db.insert("Alunos", null, contentValues)
    }

    private fun getContentValues(aluno: Aluno): ContentValues {
        val contentValues = ContentValues()
        contentValues.put("nome", aluno.nome)
        contentValues.put("endereco", aluno.endereco)
        contentValues.put("telefone", aluno.telefone)
        contentValues.put("site", aluno.site)
        contentValues.put("nota", aluno.nota)
        return contentValues
    }

    fun buscaAlunos(): List<Aluno> {
        val db = readableDatabase
        val sql = "SELECT * FROM Alunos;"
        val cursor = db.rawQuery(sql, null)

        val result = ArrayList<Aluno>()

        while (cursor.moveToNext()) {
            val aluno = Aluno(
                    id = cursor.getLong(cursor.getColumnIndex("id")),
                    nome = cursor.getString(cursor.getColumnIndex("nome")),
                    telefone = cursor.getString(cursor.getColumnIndex("telefone")),
                    endereco = cursor.getString(cursor.getColumnIndex("endereco")),
                    site = cursor.getString(cursor.getColumnIndex("site")),
                    nota = cursor.getDouble(cursor.getColumnIndex("nota"))
            )
            result.add(aluno)
        }
        cursor.close()

        return result
    }

    fun delete(aluno: Aluno) {
        val db = writableDatabase
        val params = arrayOf(aluno.id.toString())

        db.delete("Alunos", "id = ?", params)
    }

    companion object Constants {
        val DATABASE_NAME = "Agenda"
        val VERSION = 1
    }

    fun atualiza(aluno: Aluno) {
        val db = writableDatabase
        val params = arrayOf(aluno.id.toString())
        db.update("Alunos", getContentValues(aluno), "id = ?", params)
    }


}