package com.example.konnect.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.konnect.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private Button btnCreateUser;
    private EditText etUser, etPassword1, etPassword2;

    // Alert dialog
    private AlertDialog.Builder builder;

    // Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        btnCreateUser = (Button) findViewById(R.id.btnRegister);
        etUser = (EditText) findViewById(R.id.registerUsuario);
        etPassword1 = (EditText) findViewById(R.id.registerContrasena);
        etPassword2 = (EditText) findViewById(R.id.registerValidateContrasena);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    public void validateData() {
        // Get data
        String email, password1, password2;
        email = etUser.getText().toString().trim();
        password1 = etPassword1.getText().toString().trim();
        password2 = etPassword2.getText().toString().trim();

        // Validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etUser.setError("Email inválido");
        } else if (email.isEmpty()) {
            etUser.setError("Debe completar este campo");
            etUser.requestFocus();
        } else if (password1.isEmpty()) {
            etPassword1.setError("Debe completar este campo");
            etPassword1.requestFocus();
        } else if (password2.isEmpty()) {
            etPassword2.setError("Debe completar este campo");
            etPassword2.requestFocus();
        } else if (password1.length() < 6) {
            etPassword1.setError("La contraseña debe contener al menos 6 caracteres");
            etPassword1.requestFocus();
        } else if (!password1.equals(password2)) {
            etPassword2.setError("Las contraseñas no coinciden");
            etPassword2.requestFocus();
        } else {
            firebaseSignUp(email, password2);
        }
    }

    public void firebaseSignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Get user info
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String email = firebaseUser.getEmail();

                        Toast.makeText(RegisterActivity.this, "Usuario " + email + " creado correctamente", Toast.LENGTH_SHORT).show();

                        /*builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setTitle("Usuario " + email + " creado correctamente");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", null);
                        builder.show();*/

                        startActivity(new Intent(RegisterActivity.this, RegisterSecondActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "No pudimos crear este usuario.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onRegisterClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
}