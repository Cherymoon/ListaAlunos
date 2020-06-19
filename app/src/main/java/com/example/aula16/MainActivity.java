package com.example.aula16;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

   private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        criarDB();

        Button btnAdicionar = findViewById(R.id.btnAdicionar);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdicionarAlunoActivity.class);
                startActivity(intent);
            }
        });

        Button btnListar = findViewById(R.id.btnListar);
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarAlunoActivity.class);
                startActivity(intent);
            }
        });

    }

    private void criarDB() {
        db = openOrCreateDatabase("escola.db", Context.MODE_PRIVATE, null);

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ALUNO( ");
        sql.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome VARCHAR(100), ");
        sql.append("idade TINYINT(3), ");
        sql.append("curso VARCHAR(40)");
        sql.append(");");

        try{
            db.execSQL(sql.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

}

