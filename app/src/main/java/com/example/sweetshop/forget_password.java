package com.example.sweetshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class forget_password extends AppCompatActivity {

    private TextInputEditText emailEdittext;
    private Button btnSend;
    private ProgressBar progressBar;
    private TextView Loginagain;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailEdittext = findViewById(R.id.email);
        btnSend=findViewById(R.id.buttonSend);
        progressBar=findViewById(R.id.progressbar);
        Loginagain=findViewById(R.id.LoginAgainText);

        mAuth=FirebaseAuth.getInstance();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpassword();
            }
        });

        String text="Login again? Click here";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        };

        ss.setSpan(clickableSpan,19,23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Loginagain.setText(ss);
        Loginagain.setMovementMethod(LinkMovementMethod.getInstance());


    }

    public void resetpassword(){
        String email = emailEdittext.getText().toString().trim();

        if (email.isEmpty()) {
            emailEdittext.setError("Please provide email!");
            emailEdittext.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdittext.setError("Provide valid email!");
            emailEdittext.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        closeKeyboard();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Check your email to reset your password!",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Try again!,Something wrong happened",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}