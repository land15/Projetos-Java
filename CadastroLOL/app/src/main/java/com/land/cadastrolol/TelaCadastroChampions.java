package com.land.cadastrolol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TelaCadastroChampions extends AppCompatActivity {

    EditText editTextNomeChampion;
    CheckBox checkBoxMid, checkBoxTop, checkBoxSuport, checkBoxJungle, checkBoxAdc;
    Spinner spinnerTier;
    Button buttonCadastrarChampion, buttonVoltarInicial;

    FirebaseDatabase database;
    DatabaseReference baseRef;

    public static String nomeChampion;
    public static ArrayList rotas;
    public static String tierChampior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_champions);

        editTextNomeChampion = findViewById(R.id.editTextNomeChampion);
        checkBoxAdc = findViewById(R.id.checkBoxAdc);
        checkBoxJungle = findViewById(R.id.checkBoxJungle);
        checkBoxMid = findViewById(R.id.checkBoxMid);
        checkBoxSuport = findViewById(R.id.checkBoxSuport);
        checkBoxTop = findViewById(R.id.checkBoxTop);
        spinnerTier = findViewById(R.id.spinnerTier);
        buttonCadastrarChampion = findViewById(R.id.buttonCadastrarChampion);
        buttonVoltarInicial = findViewById(R.id.buttonVoltarInicial);

        database = FirebaseDatabase.getInstance();
        baseRef = database.getReference();

        spinnerTier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    buttonCadastrarChampion.setVisibility(View.INVISIBLE);
                } else {
                    buttonCadastrarChampion.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonCadastrarChampion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    nomeChampion = editTextNomeChampion.getText().toString();

                    ArrayList<String> adiconarChampion = new ArrayList<>();

                    if (checkBoxAdc.isChecked()) {
                        adiconarChampion.add(checkBoxAdc.getText().toString());
                    }

                    if (checkBoxJungle.isChecked()) {
                        adiconarChampion.add(checkBoxJungle.getText().toString());
                    }

                    if (checkBoxMid.isChecked()) {
                        adiconarChampion.add(checkBoxMid.getText().toString());
                    }

                    if (checkBoxSuport.isChecked()) {
                        adiconarChampion.add(checkBoxSuport.getText().toString());
                    }

                    if (checkBoxTop.isChecked()) {
                        adiconarChampion.add(checkBoxTop.getText().toString());
                    }

                    rotas = adiconarChampion;

                    tierChampior = spinnerTier.getSelectedItem().toString();

                    cadastrarChampions(TelaInicial.usuario, nomeChampion, rotas, tierChampior);


                } catch (Exception e) {
                    Toast.makeText(TelaCadastroChampions.this, "Por favor, preencha todos os campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonVoltarInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent novaJanela = new Intent(TelaCadastroChampions.this, TelaUsuarioInicial.class);
                startActivity(novaJanela);
            }
        });

    }

    private void cadastrarChampions(final String nomeUsuario, final String nomeChampion, final ArrayList rotaChampion, final String tierChampion) {
        baseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                baseRef.child("usuarios").child(nomeUsuario).child("champions").child(nomeChampion).child("rota").setValue(rotaChampion);
                baseRef.child("usuarios").child(nomeUsuario).child("champions").child(nomeChampion).child("tier").setValue(tierChampion);

                Toast.makeText(TelaCadastroChampions.this,"Champion "+ nomeChampion +" cadastrado com sucesso",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}