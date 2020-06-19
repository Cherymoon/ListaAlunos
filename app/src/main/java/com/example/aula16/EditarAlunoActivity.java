package com.example.aula16;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditarAlunoActivity extends AppCompatActivity {

    EditText campoNome, campoIdade;
    Spinner spCurso;
    Button btnEditar, btnExcluir;
    SQLiteDatabase db;
    String idAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_aluno);

        Intent intent = getIntent();

        idAluno = intent.getStringExtra("id").toString();
        campoNome = findViewById(R.id.campoNome);
        campoIdade = findViewById(R.id.campoIdade);
        spCurso = findViewById(R.id.spCurso);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);

        campoNome.setText(intent.getStringExtra("nome"));
        campoIdade.setText(intent.getStringExtra("idade"));
        String cursoAluno = intent.getStringExtra("curso");

        String[] cursos = new String[] {"Ciência da Computação", "Engenharia da Computação", "Direito", "Farmacia", "Medicina"};

        ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cursos);
        spCurso.setAdapter(spAdapter);

        int aux = 0;
        for(String c : cursos) {
            if(c.equals(cursoAluno)) {
                break;
            }
            aux++;
        }

        spCurso.setSelection(aux);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirAluno(v);
            }
        });

    }

    public void editarAluno(View v) {
        db = openOrCreateDatabase("escola.db", Context.MODE_PRIVATE, null);

        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE ALUNO SET ");
        sql.append("nome = '"+campoNome.getText().toString()+"', ");
        sql.append("idade = "+campoIdade.getText().toString()+", ");
        sql.append("curso = '"+spCurso.getSelectedItem().toString()+"' where _id = "+idAluno+";");

        try {
            db.execSQL(sql.toString());
            Toast.makeText(this, "Aluno editado.", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), ListarAlunoActivity.class);
        startActivity(intent);
    }

    public void excluirAluno(View v) {
        db = openOrCreateDatabase("escola.db", Context.MODE_PRIVATE, null);

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM aluno ");
        sql.append("where _id = "+idAluno+";");

        try {
            db.execSQL(sql.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), ListarAlunoActivity.class);
        startActivity(intent);
    }

}
