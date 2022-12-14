package com.example.konnect.login_signup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.konnect.MainActivity;
import com.example.konnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                    contrasenia.setError("Ingrese una contrase??a");
                    contrasenia.requestFocus();
                } else {
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
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Se ha producido un error");
                    builder.setMessage("Usuario o contrase??a incorrectos");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", null);
                    builder.show();
                }
            }
        });
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }
}