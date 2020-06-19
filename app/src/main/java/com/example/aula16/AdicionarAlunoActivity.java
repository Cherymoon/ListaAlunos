package com.example.aula16;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;

public class AdicionarAlunoActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText campoNome, campoIdade;
    Spinner spCurso;
    Button btnAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_aluno);

        campoNome = findViewById(R.id.campoNome);
        campoIdade = findViewById(R.id.campoIdade);
        spCurso = findViewById(R.id.spCurso);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        String[] cursos = new String[] {"Ciência da Computação", "Engenharia da Computação", "Direito", "Farmacia", "Medicina"};

        ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cursos);
        spCurso.setAdapter(spAdapter);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAluno();
            }
        });
    }

    private void salvarAluno() {

        String nome = campoNome.getText().toString().trim();
        String idade = campoIdade.getText().toString().trim();

        if(nome.equals("") || idade.equals("")) {
            Toast.makeText(this, "Porfavor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
        }
        else {
            db = openOrCreateDatabase("escola.db", Context.MODE_PRIVATE,null);
            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO ALUNO(NOME, IDADE, CURSO) VALUES (");
            sql.append("'"+nome+"', ");
            sql.append(idade+", '");
            sql.append(spCurso.getSelectedItem().toString()+"'");
            sql.append(");");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(this, "Aluno inserido", Toast.LENGTH_SHORT).show();
            } catch(Exception e) {
                e.printStackTrace();
            }
            db.close();
        }
    }


}
