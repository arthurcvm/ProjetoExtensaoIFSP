
//******************************************************

//Instituto Federal de São Paulo - Campus Sertãozinho

//Disciplina......: M4DADM

//Programação de Computadores e Dispositivos Móveis

//Aluno...........: Arthur Cezar Valentim de Melo

//******************************************************

package br.com.arthurcvm.projetofinalextensao;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class SecondActivity extends AppCompatActivity {

    //Declara os componentes da interface
    EditText edtNome;
    EditText edtCpf;
    EditText edtIdade;
    EditText edtTel;
    EditText edtEmail;

    Button btInserir;
    Button btListar;
    Button btVoltar;

    //Declara o banco
    private DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //instancia o banco
        this.db = new DBHelper(this);

        //Instancia os componentes da interface
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtCpf = (EditText) findViewById(R.id.edtCpf);
        edtIdade = (EditText) findViewById(R.id.edtIdade);
        edtTel = (EditText) findViewById(R.id.edtTel);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        //instancia os listeners dos botões com suas devidas funções
        btInserir = (Button) findViewById(R.id.btInserir);
        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserirDado();
            }
        });

        btListar = (Button) findViewById(R.id.btListar);
        btListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarRegistros();
            }
        });

        btVoltar = (Button) findViewById(R.id.btVoltar);
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarParaPrimeiraTela();
            }
        });
    }

    //inserção de dados a partir da interface
    private void inserirDado(){
        //Verifica se os campos estão preenchidos
        if(edtNome.getText().length()>0 &&
                edtCpf.getText().length()>0 &&
                edtIdade.getText().length()>0 &&
                edtTel.getText().length()>0 &&
                edtEmail.getText().length()>0){
            //Se estiverem, insere os dados no banco
            db.insert(edtNome.getText().toString(), edtCpf.getText().toString(),
                    edtIdade.getText().toString(), edtTel.getText().toString(),
                    edtEmail.getText().toString());

            //mensagem de retorno de sucesso
            Toast.makeText(this, "Registro adicionado com sucesso!", Toast.LENGTH_LONG).show();

            //limpa os campos
            edtNome.setText("");
            edtCpf.setText("");
            edtIdade.setText("");
            edtTel.setText("");
            edtEmail.setText("");
        }
        else{
            //mensagem de retorno de insucesso
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();

            //limpa os campos
            edtNome.setText("");
            edtCpf.setText("");
            edtIdade.setText("");
            edtTel.setText("");
            edtEmail.setText("");
        }
    }

    //listagem de registros de pessoas
    private void listarRegistros(){
        //um array de pessoas coletadas do banco
        List<Pessoa> pessoas = db.queryGetAll();
        //verifica se ele é null, ou seja, não tem registros
        if(pessoas == null){

            //Mensagem de array vazio
            Toast.makeText(this, "Não há registros cadastrados", Toast.LENGTH_SHORT).show();
            return; //sai da função (semeslhante ao "break"
        }
        //Percorre o array para printar os registros de pessoas
        for (int i=0; i<pessoas.size(); i++){
            //Pega uma pessoa
            Pessoa pessoa = (Pessoa) pessoas.get(i);

            //Cria um dialog com os dados da pessoa
            AlertDialog.Builder adb = new AlertDialog.Builder(SecondActivity.this);
            adb.setTitle("Registro" + i);
            adb.setMessage("Nome: "+ pessoa.getNome()+"\n"+
                    "CPF: "+ pessoa.getCpf()+"\n"+
                    "Idade: "+ pessoa.getIdade()+"\n"+
                    "Telefone: "+ pessoa.getTelefone()+"\n"+
                    "Email: "+ pessoa.getEmail());
            //Cria um botão no dialog para fechar
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            //Exibe o dialog
            adb.show();
        }
    }

    //retorna a primeira actvity da mesma forma que veio
    private void voltarParaPrimeiraTela(){
        Intent intent = new Intent();
        intent.setClass(SecondActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
