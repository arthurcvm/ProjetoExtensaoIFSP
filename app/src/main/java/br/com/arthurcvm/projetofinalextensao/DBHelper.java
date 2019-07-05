
//******************************************************

//Instituto Federal de São Paulo - Campus Sertãozinho

//Disciplina......: M4DADM

//Programação de Computadores e Dispositivos Móveis

//Aluno...........: Arthur Cezar Valentim de Melo

//******************************************************


package br.com.arthurcvm.projetofinalextensao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    //declara o nome do banco SQLite
    private static final String DATABASE_NAME = "bancodedados.db";
    //versão do banco no aparelho
    private static final int DATABASE_VERSION = 1;
    //nome da tabela à ser manipulada
    private static final String TABLE_NAME = "pessoas";

    private Context context;
    private SQLiteDatabase db;

    private SQLiteStatement insertStmt;
    //Query de inserção de registro com os devidos campos
    private static final String INSERT = "insert into " + TABLE_NAME + " (nome, cpf, idade, telefone, email) values (?, ?, ?, ?, ?)";

    //Construtor
    public DBHelper(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    //inserção de registro "blindando" a tipagem, para evitar tipos incorretos no registro
    public long insert (String nome, String cpf, String idade, String telefone, String email){
        this.insertStmt.bindString(1, nome);
        this.insertStmt.bindString(2, cpf);
        this.insertStmt.bindString(3, idade);
        this.insertStmt.bindString(4, telefone);
        this.insertStmt.bindString(5, email);

        return this.insertStmt.executeInsert();
    }

    //Query de limpeza da tabela
    public void deleteAll(){
        this.db.delete(TABLE_NAME, null, null);
    }

    //Função de listagem de pessoas
    public List<Pessoa> queryGetAll(){
        //Array de pessoas a ser populado
        List<Pessoa> pessoas = new ArrayList<>();
        try {
            //Query de listagem com todos os campos
            Cursor cursor = this.db.query(TABLE_NAME, new String[] {"nome", "cpf", "idade", "telefone", "email"},
                    null, null, null, null, null, null);

            //conta a quantidade de registros no banco
            int nregistros = cursor.getCount();

            //Se a quantidade for diferente de 0
            if(nregistros != 0){
                //posiciona o cursor no primeiro registro
                cursor.moveToFirst();

                do {
                    //Seta os atributos de "pessoa" no construtor
                    Pessoa pessoa = new Pessoa(cursor.getString(0),
                            cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4));
                    //adiciona a pessoa ao array
                    pessoas.add(pessoa);
                }while (cursor.moveToNext());//Move o cursor pro próximo registro

                //caso o cursor não esteja fechado, fecha-lo
                if(cursor != null && ! cursor.isClosed()){
                    cursor.close();

                    //retorna o array populado
                    return pessoas;
                }
            }
            else{
                return null;
            }
        }catch (Exception err){
            return null;
        }
        return null;
    }

    //classe interna para manipulação do banco
    private static class OpenHelper extends SQLiteOpenHelper{
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //query de criação da tabela caso não exista
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, cpf TEXT, idade TEXT, telefone TEXT, email TEXT);";

            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //query de recriação da tabela
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
        }
    }
}
