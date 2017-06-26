package br.com.alura.agenda

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import br.com.alura.agenda.R.id.lista_alunos
import br.com.alura.agenda.dao.AlunoDao
import br.com.alura.agenda.modelo.Aluno
import kotlinx.android.synthetic.main.activity_lista_alunos.*

import javax.crypto.spec.SecretKeySpec

class ListaAlunosActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)

        novo_aluno.setOnClickListener {
            val intent = Intent(ListaAlunosActivity@this, FormularioActivity::class.java)
            startActivity(intent)
        }

        lista_alunos.setOnItemClickListener { lista, item, position, id ->
            val aluno = lista_alunos.getItemAtPosition(position) as Aluno
            Toast.makeText(this, "Clicou em ${aluno.nome}", Toast.LENGTH_SHORT).show()

            val intent = Intent(ListaAlunosActivity@this, FormularioActivity::class.java)
            intent.putExtra("aluno", aluno)
            startActivity(intent)
        }

        registerForContextMenu(lista_alunos)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        val menuItem = menu?.add("Deletar")

        menuItem?.setOnMenuItemClickListener {

            val position = (menuInfo as AdapterView.AdapterContextMenuInfo).position
            val aluno = lista_alunos.getItemAtPosition(position) as Aluno
            val alunoDao = AlunoDao(this)
            alunoDao.delete(aluno)
            alunoDao.close()

            Toast.makeText(this, "Aluno ${aluno.nome} removido!", Toast.LENGTH_LONG).show()

            carregaAlunos()
            return@setOnMenuItemClickListener false
        }
    }

    override fun onResume() {
        super.onResume()
        carregaAlunos()
    }

    fun carregaAlunos(){
        val alunoDao = AlunoDao(this)
        val alunos = alunoDao.buscaAlunos()
        alunoDao.close()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, alunos)
        lista_alunos.adapter = adapter
    }


}