package com.land.cadastrolol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

public class TelaInicial extends AppCompatActivity {

    EditText editTextUsuarioLogin, editTextPasswordSenhaUsuarioLogin;
    Button buttonLogar, buttonRegistrar;

    public static String usuario;
    public static String senha;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        editTextUsuarioLogin = findViewById(R.id.editTextUsuarioLogin);
        editTextPasswordSenhaUsuarioLogin = findViewById(R.id.editTextPasswordSenhaUsuarioLogin);
        buttonLogar = findViewById(R.id.buttonLogar);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);

        buttonRegistrar.setVisibility(View.INVISIBLE);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = editTextUsuarioLogin.getText().toString();
                senha = editTextPasswordSenhaUsuarioLogin.getText().toString();
                logar(usuario,senha);
            }
        });

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar(usuario,senha);
            }
        });
    }

    private void logar(final String nomeUsuario, final String senhaUsuario) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("usuarios").child(nomeUsuario).getValue()==null) {
                    Toast.makeText(TelaInicial.this, "Usuário inexistente " +
                                    "Se deseja registrar como um novo usuário, clique no botão 'Registrar'!",
                            Toast.LENGTH_LONG).show();

                    buttonRegistrar.setVisibility(View.VISIBLE);
                } else {
                    if (snapshot.child("usuarios").child(nomeUsuario).child("senha").getValue().equals(md5(senhaUsuario))) {
                        Intent novaJanela = new Intent(TelaInicial.this,TelaUsuarioInicial.class);
                        startActivity(novaJanela);
                    } else {
                        Toast.makeText(TelaInicial.this, "Senha incorreta!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void registrar(final String nomeUsuario, final String senhaUsuario) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child("usuarios").child(nomeUsuario).child("champions").setValue(0);
                databaseReference.child("usuarios").child(nomeUsuario).child("senha").setValue(md5(senhaUsuario));

                Toast.makeText(TelaInicial.this,"Usuário registrado com sucesso!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private static final String md5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return "";
        }
    }
}