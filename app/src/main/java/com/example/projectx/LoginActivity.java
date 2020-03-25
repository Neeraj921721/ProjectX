package com.example.projectx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class LoginActivity extends AppCompatActivity {
    EditText email ;
    EditText password ;
    Button btn;
    TextView forgotPassword;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        btn = (Button)findViewById(R.id.loginbtn);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        mFirebaseAuth = FirebaseAuth.getInstance() ;

        mAuthStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null)
                {
                    Toast.makeText(LoginActivity.this, "Logging You in !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            }
        };

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String emailId = email.getText().toString().trim();
                final String pwd = password.getText().toString().trim();

                if(emailId.isEmpty())
                {
                    email.setError("Please Provide an email address !");
                    email.requestFocus();
                }
                else if(pwd.isEmpty())
                {
                    password.setError("Please enter the password !");
                    password.requestFocus();
                }
                else if(emailId.isEmpty() && pwd.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Fields are empty !", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    password.requestFocus();
                }
                else if(!(emailId.isEmpty() && pwd.isEmpty()))
                {
                   mFirebaseAuth.fetchSignInMethodsForEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                       @Override
                       public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean check = !task.getResult().getSignInMethods().isEmpty();
                            if(!check)
                            {
                                Toast.makeText(LoginActivity.this, "You need to signUp first Taking you to the signUp screen !", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                mFirebaseAuth.signInWithEmailAndPassword(emailId,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(!task.isSuccessful())
                                        {
                                            Toast.makeText(LoginActivity.this, "Couldn't Log you In ! Either email or password incorrect !", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(LoginActivity.this, "Logging You In... Please wait..", Toast.LENGTH_SHORT).show();
                                            Intent intoHome = new Intent(LoginActivity.this,HomeActivity.class);
                                            startActivity(intoHome);
                                        }
                                    }
                                });
                            }
                       }
                   });
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Some Error Ocurred !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(intent);
            }
        });
    }
    /*@Override
    protected void onStart()
    {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }*/
}
