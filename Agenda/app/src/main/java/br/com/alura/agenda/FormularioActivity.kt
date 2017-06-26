package br.com.alura.agenda

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.alura.agenda.dao.AlunoDao
import br.com.alura.agenda.modelo.Aluno
import kotlinx.android.synthetic.main.activity_formulario.*

class FormularioActivity : AppCompatActivity() {

    var helper: FormularioHelper? = null
    var aluno: Aluno? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        helper = FormularioHelper(this)
        aluno = intent.getParcelableExtra<Aluno>("aluno")
        aluno?.let {
            helper?.preencheFormulario(it)
        }
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_formulario, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_formulario_ok -> {
                aluno = helper?.pegaAluno()
                val alunoDao = AlunoDao(this)

                aluno?.let {
                    if (it.id == -1L){
                        alunoDao.insere(it)
                        alunoDao.close()
                        Toast.makeText(this, "Aluno ${it.nome} salvo!", Toast.LENGTH_LONG).show()
                    } else {
                        alunoDao.atualiza(it)
                        alunoDao.close()
                        Toast.makeText(this, "Aluno ${it.nome} atualizado!", Toast.LENGTH_LONG).show()
                    }
                }
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
