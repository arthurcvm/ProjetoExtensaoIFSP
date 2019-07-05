
//******************************************************

//Instituto Federal de São Paulo - Campus Sertãozinho

//Disciplina......: M4DADM

//Programação de Computadores e Dispositivos Móveis

//Aluno...........: Arthur Cezar Valentim de Melo

//******************************************************


package br.com.arthurcvm.projetofinalextensao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Declara o botão para a tela do form
    Button btSegundaTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instancia o botão de acordo com a tela em XML
        btSegundaTela = (Button) findViewById(R.id.btSegundaTela);

        //Seta um listener no botão para chamar uma função ao apertar-lo
        btSegundaTela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaSegundaTela();
            }
        });
    }

    //Função chamada pelo botão que carrega uma intent para a outra actvity
    private void chamaSegundaTela(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SecondActivity.class);
        startActivity(intent);
        finish();
    }
}
