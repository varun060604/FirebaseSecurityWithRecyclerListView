package com.varun.firebasesecuritywithlistview;

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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordPage extends AppCompatActivity {

    EditText ForgotEmail;
    Button Submit;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        ForgotEmail = findViewById(R.id.forgotEmail);
        Submit = findViewById(R.id.Submit);

        auth = FirebaseAuth.getInstance();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }
    private void resetPassword() {
        String email = ForgotEmail.getText().toString();

        if (email.isEmpty()) {
            ForgotEmail.setError("Email is Required");
            ForgotEmail.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordPage.this, "Check Email for Reset Link", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPasswordPage.this, LoginPage.class));
                }
                else {
                    Toast.makeText(ForgotPasswordPage.this, "Mail sending failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}