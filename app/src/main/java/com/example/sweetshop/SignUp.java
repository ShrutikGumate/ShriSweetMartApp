package com.example.sweetshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetshop.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextFullname,textInputEditTextpassword,textInputEditTextEmail,textInputEditTextPhoneNumber;
    Button buttonSignUp;

    TextView textViewLogin;
    ProgressBar progressBar;
    //FloatingActionButton fb,google;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        textInputEditTextFullname=findViewById(R.id.fullname);
        textInputEditTextEmail=findViewById(R.id.email);
        textInputEditTextpassword=findViewById(R.id.password);
        textInputEditTextPhoneNumber=findViewById(R.id.phonenumber);


        buttonSignUp=findViewById(R.id.buttonSignUp);


        textViewLogin=findViewById(R.id.loginText);
        progressBar=findViewById(R.id.progressbar);
        //fb = findViewById(R.id.fab_fb);
        //google = findViewById(R.id.fab_google);



        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname,password,email,phonenumber;

                fullname = textInputEditTextFullname.getText().toString().trim();
                email = textInputEditTextEmail.getText().toString().trim();
                phonenumber = textInputEditTextPhoneNumber.getText().toString().trim();
                password = textInputEditTextpassword.getText().toString().trim();



                if(fullname.isEmpty()){
                    textInputEditTextFullname.setError("Please provide fullname!");
                    textInputEditTextFullname.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    textInputEditTextEmail.setError("Please provide email!");
                    textInputEditTextEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    textInputEditTextEmail.setError("Provide valid email!");
                    textInputEditTextEmail.requestFocus();
                    return;
                }
                if(phonenumber.isEmpty()){
                    textInputEditTextPhoneNumber.setError("Please provide Phone number!");
                    textInputEditTextPhoneNumber.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    textInputEditTextpassword.setError("Please provide password!");
                    textInputEditTextpassword.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user = new User(fullname,email,phonenumber,password);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(SignUp.this,"Registered successfully!",Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(SignUp.this,"Failed to register , Try again!",Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(SignUp.this,"Failed to register , Try again!",Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

                /*
                Intent intent = new Intent(getApplicationContext(),OTP.class);
                intent.putExtra("fullname",fullname);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                startActivity(intent);

                 */
            }
        });

        String text="Already have an account? Login";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        };

        ss.setSpan(clickableSpan,25,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewLogin.setText(ss);
        textViewLogin.setMovementMethod(LinkMovementMethod.getInstance());


    }
}
