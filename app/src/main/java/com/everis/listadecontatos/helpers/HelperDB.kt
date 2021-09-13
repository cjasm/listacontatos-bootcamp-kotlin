package com.everis.listadecontatos.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO

class HelperDB(
    context: Context?
) : SQLiteOpenHelper(context, NOME_DO_BANCO, null, VERSAO_ATUAL) {

    companion object {
        private const val NOME_DO_BANCO = "contato.db"
        private const val VERSAO_ATUAL = 1
    }

    private val TABLE_NAME = "contato"
    private val COLUMN_ID = "id"
    private val COLUMN_NOME = "nome"
    private val COLUMN_TELEFONE = "telefone"
    private val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    private val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_ID INTEGER NOT NULL, " +
            "$COLUMN_NOME TEXT NOT NULL, " +
            "$COLUMN_TELEFONE TEXT NOT NULL, " +
            "PRIMARY KEY($COLUMN_ID AUTOINCREMENT)" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion){
            db?.execSQL(DROP_TABLE)
        }
    }

    fun buscarContatos(busca:String): List<ContatosVO>{
//        salvarContato(ContatosVO(0, "Claudio", "1234-1234"))
//        salvarContato(ContatosVO(0, "Eyshila", "1234-1234"))
//        salvarContato(ContatosVO(0, "Jussara", "1234-1234"))
//        salvarContato(ContatosVO(0, "Carol", "1234-1234"))
        val db = readableDatabase ?: return mutableListOf()
        var lista = mutableListOf<ContatosVO>()
        val sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NOME LIKE '%$busca%'"
        var cursor = db.rawQuery(sql, arrayOf())
        if( cursor == null ){
            db.close()
            return mutableListOf()
        }
        while (cursor.moveToNext()){
            val contato = ContatosVO(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONE))
            )
            lista.add(contato)
        }
        db.close()
        return lista
    }

    fun salvarContato(contato:ContatosVO){
        val db = writableDatabase ?: return
//        Opcao 1
//        val sql = "INSERT INTO $TABLE_NAME ($COLUMN_NOME, $COLUMN_TELEFONE) VALUES ($contato.nome, $contato.telefone)"
//        db.execSQL(sql)
//        Opcao 2
        val sql = "INSERT INTO $TABLE_NAME ($COLUMN_NOME, $COLUMN_TELEFONE) VALUES (?, ?)"
        val array = arrayOf(contato.nome, contato.telefone)
        db.execSQL(sql, array)
//        Opcao 3
//        var content = ContentValues()
//        content.put(COLUMN_NOME, contato.nome)
//        content.put(COLUMN_TELEFONE, contato.telefone)
//        db.insert(TABLE_NAME, null, content)
//        db.close()
    }

}