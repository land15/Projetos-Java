package com.land.cadastrolol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaUsuarioInicial extends AppCompatActivity {

    Button buttonTelaCadastrarChampions, buttonConsultarDados;
    TextView textViewTituloTelaDados;

    FirebaseDatabase database;
    DatabaseReference baseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_usuario_inicial);

        buttonTelaCadastrarChampions = findViewById(R.id.buttonTelaCadastrarChampion);
        buttonConsultarDados = findViewById(R.id.buttonConsultarDados);
        textViewTituloTelaDados = findViewById(R.id.textViewTituloTelaDados);

        database = FirebaseDatabase.getInstance();
        baseRef = database.getReference();

        mudarTitulo(TelaInicial.usuario);

        buttonTelaCadastrarChampions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent novaJanela = new Intent(TelaUsuarioInicial.this,TelaCadastroChampions.class);
                startActivity(novaJanela);
            }
        });

        buttonConsultarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent novaJanela = new Intent(TelaUsuarioInicial.this,TelaConsultaDados.class);
                startActivity(novaJanela);
            }
        });
    }

    private void mudarTitulo(final String nomeUsuario) {

        baseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textViewTituloTelaDados.setText(textViewTituloTelaDados.getText() +" "+ nomeUsuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}