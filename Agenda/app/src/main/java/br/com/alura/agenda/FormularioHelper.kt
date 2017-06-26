package br.com.alura.agenda

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.RatingBar
import br.com.alura.agenda.modelo.Aluno

/**
 * Created by moacir on 05/12/16.
 */
class FormularioHelper(val activity: AppCompatActivity) {

    val nome = activity.findViewById(R.id.formulario_nome) as EditText
    val endereco = activity.findViewById(R.id.formulario_endereco) as EditText
    val telefone = activity.findViewById(R.id.formulario_telefone) as EditText
    val site = activity.findViewById(R.id.formulario_site) as EditText
    val nota = activity.findViewById(R.id.formulario_nota) as RatingBar
    var aluno: Aluno? = null

    fun pegaAluno(): Aluno {
        return Aluno(
                id = aluno?.id ?: -1,
                nome = nome.text.toString(),
                endereco = endereco.text.toString(),
                telefone = telefone.text.toString(),
                site = site.text.toString(),
                nota = nota.rating.toDouble())
    }

    fun preencheFormulario(aluno: Aluno) {
        nome.setText(aluno.nome)
        endereco.setText(aluno.endereco)
        telefone.setText(aluno.telefone)
        site.setText(aluno.site)
        nota.rating = aluno.nota.toFloat()
        this.aluno = aluno
    }

}