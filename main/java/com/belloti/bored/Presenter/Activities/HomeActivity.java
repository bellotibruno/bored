package com.belloti.bored.Presenter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.belloti.bored.Data.api.RequestBored;
import com.belloti.bored.Data.api.RequestType;
import com.belloti.bored.Data.model.Boredapi;
import com.belloti.bored.Data.model.Typeapi;
import com.belloti.bored.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private Button buttonTarefas;
    private Button selecionar;
    private TextView textType;
    private TextView textActivity;
    private Retrofit retrofit;
    private TextView textFiltro;

    private CheckBox checkEducation, checkRecreational, checkSocial,checkCharity,checkRelaxation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botaoRecuperar = findViewById(R.id.buttonRecuperar);
        buttonTarefas = findViewById(R.id.buttonTarefas);
        selecionar = findViewById(R.id.selecionar);
        textType = findViewById(R.id.textType);
        textActivity = findViewById(R.id.textActivity);

        //Checkbox
        checkEducation = findViewById(R.id.checkEducation);
        checkRecreational = findViewById(R.id.checkRecreational);

        textFiltro = findViewById(R.id.textFiltro);


        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.boredapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResponseBoredapi();

            }
        });
        selecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openType();
            }
        });
        buttonTarefas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonTarefas();
            }
        });

    }



    public void checkbox() {

        String texto = "";

        if (checkEducation.isChecked()) {
            texto = "education";
        }

        if (checkRecreational.isChecked()) {
            texto = texto + "recreational";
        }

        textFiltro.setText("");
    }

    public void filtrar(View view) {
        checkbox();
        onResponseType();
    }

    private void buttonTarefas() {
        startActivity(new Intent(this, com.belloti.bored.Presenter.Activities.TarefasActivity.class));
    }

    private void openType() {
        String activity = textActivity.getText().toString();
        Intent intent = new Intent(this, com.belloti.bored.Presenter.Activities.AdicionarTarefaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("activity", activity);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void onResponseBoredapi() {
        RequestBored requestBored = retrofit.create(RequestBored.class);
        Call<Boredapi> call = requestBored.recuperarBored();

        call.enqueue(new Callback<Boredapi>() {
            @Override
            public void onResponse(Call<Boredapi> call, Response<Boredapi> response) {
                if (response.isSuccessful()) {
                    Boredapi boredapi = response.body();
                    textType.setText(boredapi.getType());
                    textActivity.setText(boredapi.getActivity());
                }
            }

            @Override
            public void onFailure(Call<Boredapi> call, Throwable t) {

            }
        });
    }

    private void onResponseType() {
        if (checkEducation.isChecked()) {
            String texto = checkEducation.getText().toString();
            RequestType requestType = retrofit.create(RequestType.class);
            Call<Typeapi> call = requestType.recuperarType(texto);
            call.enqueue(new Callback<Typeapi>() {
                @Override
                public void onResponse(Call<Typeapi> call, Response<Typeapi> response) {
                    if (response.isSuccessful()) {
                        Typeapi typeapi = response.body();
                        textType.setText(typeapi.getType());
                        textActivity.setText(typeapi.getActivity());
                    }
                }

                @Override
                public void onFailure(Call<Typeapi> call, Throwable t) {

                }
            });
        } if (checkRecreational.isChecked()){
            String texto = checkRecreational.getText().toString();
            RequestType requestType = retrofit.create(RequestType.class);
            Call<Typeapi> call = requestType.recuperarType(texto);
            call.enqueue(new Callback<Typeapi>() {
                @Override
                public void onResponse(Call<Typeapi> call, Response<Typeapi> response) {
                    if (response.isSuccessful()) {
                        Typeapi typeapi = response.body();
                        textType.setText(typeapi.getType());
                        textActivity.setText(typeapi.getActivity());
                    }
                }

                @Override
                public void onFailure(Call<Typeapi> call, Throwable t) {

                }
            });
        }
    }
}