package com.land.cadastrolol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaConsultaDados extends AppCompatActivity {

    TextView textView4;

    FirebaseDatabase database;
    DatabaseReference baseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_consulta_dados);

        database = FirebaseDatabase.getInstance();
        baseRef = database.getReference();

        textView4 = findViewById(R.id.textView4);

        String nomeUsuario = TelaInicial.usuario;

        String nomeChampion = TelaCadastroChampions.nomeChampion;

        baseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               textView4.setText(snapshot.child("usuarios").child(nomeUsuario).child("champions").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}