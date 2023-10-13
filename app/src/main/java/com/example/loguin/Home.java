package com.example.loguin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    private TextView tvTitulo;
    private DatabaseReference mRef;
    private Usuario user;
    private FirebaseAuth mAuth;
    private FirebaseUser fbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvTitulo = findViewById(R.id.tvTitulo);
        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(fbUser.getUid());
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                user = snapshot.getValue(Usuario.class);
                String nombre = user.getNombre();
                tvTitulo.setText("usuario " + nombre);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}