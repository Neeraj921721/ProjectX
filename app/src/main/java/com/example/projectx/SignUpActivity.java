package com.example.projectx;

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

public class SignUpActivity extends AppCompatActivity {
    EditText fullName;
    EditText email;
    EditText password1;
    EditText password2;
    Button signUpBtn;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        fullName = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password1 = (EditText)findViewById(R.id.password1);
        password2 = (EditText)findViewById(R.id.password2);
        signUpBtn = (Button)findViewById(R.id.signUpbtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name = fullName.getText().toString().trim();
                String emailId = email.getText().toString().trim();
                String pwd1 = password1.getText().toString().trim();
                String pwd2 = password2.getText().toString().trim();

                if(name.isEmpty())
                {
                    fullName.setError("Please Enter your Full Name !");
                    fullName.requestFocus();
                }
                else if(emailId.isEmpty())
                {
                    email.setError("Please Enter your Email Address !");
                    email.requestFocus();
                }
                else if(pwd1.isEmpty())
                {
                    password1.setError("Please Enter your new Password !");
                    password1.requestFocus();
                }
                else if(pwd2.isEmpty())
                {
                    password2.setError("Please Re-Type your Password !");
                    password2.requestFocus();
                }
                else if(name.isEmpty() && emailId.isEmpty() && pwd1.isEmpty() && pwd2.isEmpty())
                {
                    fullName.setError("Please Enter your Full Name !");
                    fullName.requestFocus();
                    email.setError("Please Enter your Email Address !");
                    email.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Fields are empty !", Toast.LENGTH_SHORT).show();
                }
                else if(!(name.isEmpty() && emailId.isEmpty() && pwd1.isEmpty() && pwd2.isEmpty()))
                {
                    if(!(pwd2.equals(pwd1)) )
                    {
                        password2.setError("Please re-type the password correctly !");
                        password2.requestFocus();
                    }
                    else
                    {
                        mFirebaseAuth.createUserWithEmailAndPassword(emailId, pwd2).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful())
                                {
                                    Toast.makeText(SignUpActivity.this, "SignUp Error Ocurred !", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}