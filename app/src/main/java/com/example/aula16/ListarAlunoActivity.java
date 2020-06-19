package com.example.aula16;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ListarAlunoActivity extends AppCompatActivity {

    private ListView listaAlunos;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_aluno);

        listaAlunos = findViewById(R.id.listaAlunos);

        listarAlunos();

        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditarAlunoActivity.class);

                View v = listaAlunos.getChildAt(position);

                String itemId = ((TextView) v.findViewById(R.id.idAluno)).getText().toString();
                String itemNome = ((TextView) v.findViewById(R.id.nomeAluno)).getText().toString();
                String itemIdade = ((TextView) v.findViewById(R.id.idadeAluno)).getText().toString();
                String itemCurso = ((TextView) v.findViewById(R.id.cursoAluno)).getText().toString();

                intent.putExtra("id", itemId);
                intent.putExtra("nome",itemNome);
                intent.putExtra("idade", itemIdade);
                intent.putExtra("curso", itemCurso);

                startActivity(intent);
                return false;
            }
        });


    }

    private void listarAlunos() {
        db = openOrCreateDatabase("escola.db", Context.MODE_PRIVATE, null);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM aluno");

        Cursor dados = db.rawQuery(sql.toString(), null);
        String[] from = new String[] {"_id","nome","idade","curso"};
        int[] to = new int[] {R.id.idAluno, R.id.nomeAluno, R.id.idadeAluno, R.id.cursoAluno};

        SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.dados, dados, from, to, 0);
        listaAlunos.setAdapter(scAdapter);

        db.close();
    }

}
