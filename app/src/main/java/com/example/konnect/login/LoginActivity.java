package com.example.konnect.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.konnect.MainActivity;
import com.example.konnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText usuario, contrasenia;
    private AlertDialog.Builder builder;

    // Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Making notification bar transparent
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_login);

        changeStatusBarColor();

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        btn_login = (Button) findViewById(R.id.btnLogin);
        usuario = (EditText) findViewById(R.id.loginUsuario);
        contrasenia = (EditText) findViewById(R.id.loginContrasena);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = usuario.getText().toString().trim();
                String passUser = contrasenia.getText().toString().trim();

                if (emailUser.isEmpty()) {
                    usuario.setError("Ingrese un usuario");
                    usuario.requestFocus();
                } else if (passUser.isEmpty()) {
                    contrasenia.setError("Ingrese una contraseña");
                    contrasenia.requestFocus();
                } else {
                    Toast.makeText(LoginActivity.this, "Enviando a otra ventana", Toast.LENGTH_SHORT).show();
                    loginUser(emailUser, passUser);
                }
            }
        });
    }

    private void loginUser(String emailUser, String passUser) {
        mAuth.signInWithEmailAndPassword(emailUser,passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    Toast.makeText(LoginActivity.this, "correcto", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Se ha producido un error");
                    builder.setMessage("Intentalo más tarde");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", null);
                    builder.show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Error de autenticación");
                builder.setMessage("Usuario o contraseña incorrectos");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", null);
                builder.show();
            }
        });
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }
}