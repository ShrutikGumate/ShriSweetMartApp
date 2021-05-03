package com.example.sweetshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.sweetshop.databinding.ActivityOTPBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    private ActivityOTPBinding binding;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private static final String TAG="MAIN_TAG";
    private FirebaseAuth firebaseauth;
    private ProgressDialog pd;
    ProgressBar progressBar;

    private String fullname,email,phonenumber,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        fullname=intent.getStringExtra("fullname");
        email=intent.getStringExtra("email");
        password=intent.getStringExtra("password");

        binding = ActivityOTPBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.phoneLL.setVisibility(View.VISIBLE);
        binding.codeLL.setVisibility(View.GONE);

        firebaseauth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);


        pd = new ProgressDialog(this);
        pd.setTitle("Please wait...");
        pd.setCanceledOnTouchOutside(false);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInwithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                pd.dismiss();
                Toast.makeText(OTP.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, forceResendingToken);
                Log.d(TAG,"onCodeSent:"+verificationId);
                mVerificationId=verificationId;
                forceResendingToken=token;
                pd.dismiss();

                binding.phoneLL.setVisibility(View.GONE);
                binding.codeLL.setVisibility(View.VISIBLE);

                Toast.makeText(OTP.this,"Verification code sent...",Toast.LENGTH_SHORT).show();

                binding.codesentDescription.setText("Please enter the verification code we sent \nto "+binding.PhoneNumberET.getText().toString().trim());
            }
        };

        binding.sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Phone = "+91" + binding.PhoneNumberET.getText().toString().trim();
                if (TextUtils.isEmpty(Phone)){
                    Toast.makeText(OTP.this,"Please enter phone number...",Toast.LENGTH_SHORT).show();
                }
                else{
                    startPhoneNumberVerification(Phone);
                }

            }
        });

        binding.resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonenumber = binding.PhoneNumberET.getText().toString().trim();
                String Phone = "+91" + phonenumber;
                if (TextUtils.isEmpty(Phone)){
                    Toast.makeText(OTP.this,"Please enter phone number...",Toast.LENGTH_SHORT).show();
                }
                else{
                    ResendVerification(Phone,forceResendingToken);
                }

            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=binding.otpET.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    Toast.makeText(OTP.this,"Please enter otp...",Toast.LENGTH_SHORT).show();
                }
                else{
                    Verifypwithc(mVerificationId,code);
                }

            }
        });

    }
    private void startPhoneNumberVerification(String phone){
        pd.setMessage("Verifying Phone Number");
        pd.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseauth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void ResendVerification(String phone, PhoneAuthProvider.ForceResendingToken token){
        pd.setMessage("Resending code");
        pd.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseauth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(token)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void Verifypwithc(String VerificationId,String code){
        pd.setMessage("Verifying Code");
        pd.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationId,code);
        signInwithPhoneAuthCredential(credential);
    }

    private void signInwithPhoneAuthCredential(PhoneAuthCredential credential) {
        pd.setMessage("Logging In");

        firebaseauth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        pd.dismiss();
                        String phone=firebaseauth.getCurrentUser().getPhoneNumber();
                        Toast.makeText(OTP.this,"Logging In As.."+phone,Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OTP.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

    }
}