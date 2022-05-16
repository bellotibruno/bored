package com.belloti.bored.Presenter.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.belloti.bored.Data.model.Tarefa;
import com.belloti.bored.Domain.TarefaDAO;
import com.belloti.bored.R;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;
    private TextView textStatus;
    private ImageView back;


    private CheckBox checkAndamento, checkRealizada, checkDesistencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        editTarefa = findViewById(R.id.textTarefa);


        Bundle bundle = getIntent().getExtras();
        String activity = bundle.getString("activity");
        editTarefa.setText(activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Recuperar tarefa, caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if (tarefaAtual != null) {
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }


        //Checkbox
        checkAndamento      = findViewById(R.id.checkAndamento);
        checkRealizada     = findViewById(R.id.checkRealizada);
        checkDesistencia   = findViewById(R.id.checkDesistencia);
        textStatus  = findViewById(R.id.textStatus);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));

    }


    public void checkbox(){

        String texto = "";

        if( checkAndamento.isChecked() ){

            texto = "Em Andamento - ";
        }

        if( checkRealizada.isChecked() ){
            texto = texto + "Realizada - ";
        }

        if( checkDesistencia.isChecked() ){
            texto = texto + "Desistência - ";
        }

        textStatus.setText( texto );

    }

    public void status(View view) {

        checkbox();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemSalvar:
                //Executa açao para o item salvar

                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                 //edicao
                if (tarefaAtual != null) {
                if (textStatus != null) {

                    String nomeTarefa = editTarefa.getText().toString();
                    String statusTarefa = textStatus.getText().toString();
                    if (!nomeTarefa.isEmpty()) {
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        if (!statusTarefa.isEmpty()) {
                            tarefa.setTextStatus(statusTarefa);

                            //atualizar no banco de dados
                            if (tarefaDAO.atualizar(tarefa)) {
                                finish();
                                Toast.makeText(getApplicationContext(),
                                        "Sucesso ao atualizar tarefa!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Erro ao atualizar tarefa!",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }

                } else {//salvar

                    String nomeTarefa = editTarefa.getText().toString();
                    String statusTarefa = textStatus.getText().toString();
                    Tarefa tarefa = new Tarefa();

                    if (!nomeTarefa.isEmpty()) {
                        tarefa.setNomeTarefa(nomeTarefa);

                        if (!nomeTarefa.isEmpty()) {
                            tarefa.setTextStatus(statusTarefa);

                            if (tarefaDAO.salvar(tarefa)) {
                                finish();
                                Toast.makeText(getApplicationContext(),
                                        "Sucesso ao salvar tarefa!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Erro ao salvar tarefa!",
                                        Toast.LENGTH_SHORT).show();
                            }


                        }

                    }
                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }
}