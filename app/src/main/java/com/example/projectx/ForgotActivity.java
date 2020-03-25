package com.example.projectx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity
{
    private EditText email;
    private Button btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        email = (EditText)findViewById(R.id.registeredEmail);
        btn = (Button)findViewById(R.id.resetbtn);
        mAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String emailId = email.getText().toString().trim();
                if(TextUtils.isEmpty(emailId))
                {
                    Toast.makeText(ForgotActivity.this, "Enter your registered mail to reset your password !", Toast.LENGTH_LONG).show();
                }
                else
                {
                    mAuth.sendPasswordResetEmail(emailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ForgotActivity.this, "Password Reset Link sent to your Registered Email", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgotActivity.this,LoginActivity.class));
                            }
                            else
                            {
                                String msg = task.getException().getMessage();
                                Toast.makeText(ForgotActivity.this, "Error Occured : " + msg, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
