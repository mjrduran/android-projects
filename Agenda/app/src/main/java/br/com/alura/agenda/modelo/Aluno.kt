package br.com.alura.agenda.modelo

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by moacir on 05/12/16.
 */
data class Aluno(val id: Long = -1,
                 val nome: String,
                 val endereco: String,
                 val telefone: String,
                 val site: String,
                 val nota: Double) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Aluno> = object : Parcelable.Creator<Aluno> {
            override fun createFromParcel(source: Parcel): Aluno = Aluno(source)
            override fun newArray(size: Int): Array<Aluno?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source?.readLong(), source.readString(), source.readString(), source.readString(), source.readString(), source.readDouble())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(nome)
        dest?.writeString(endereco)
        dest?.writeString(telefone)
        dest?.writeString(site)
        dest?.writeDouble(nota)
    }

    override fun toString(): String {
        return nome
    }
}
