package com.example.loguin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private DatabaseReference mRef;
    private FirebaseUser fbUser;

    private EditText etCorreoR, etContraseniaR, etNombreR;
    private Button btnRegistrar;

    private String nombre, correo, contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("Usuarios");

        etCorreoR = (EditText) findViewById(R.id.etCorreoR);
        etContraseniaR = (EditText) findViewById(R.id.etContraseniaR);
        etNombreR = (EditText) findViewById(R.id.etNombreR);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = etNombreR.getText().toString().trim();
                correo = etCorreoR.getText().toString().trim();
                contrasenia = etContraseniaR.getText().toString().trim();

                Usuario u = new Usuario(nombre, correo);
                mAuth.createUserWithEmailAndPassword(correo, contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            fbUser= mAuth.getCurrentUser();
                            String uid = fbUser.getUid();
                            mRef.child(uid).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent i = new Intent(MainActivity2.this, MainActivity.class);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(MainActivity2.this, "Te Pillé", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(MainActivity2.this, "No se ha podido registrar, intentelo más tarde", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }
}