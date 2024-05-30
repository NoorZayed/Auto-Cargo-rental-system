package com.example.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;

//public class login extends AppCompatActivity {
//    EditText email_txt;
//    EditText password_txt;
//    Button login_btn;
//    Button registration_page_btn;
//
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        mAuth = FirebaseAuth.getInstance();
//        setupViews();
//    }
//
//    private void setupViews() {
//        email_txt = findViewById(R.id.email_txt);
//        password_txt = findViewById(R.id.pw_txt);
//        login_btn = findViewById(R.id.login_page_btn);
//        registration_page_btn = findViewById(R.id.register_btn);
//
//        login_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkInformation()) {
//                    signInUser(email_txt.getText().toString(), password_txt.getText().toString());
//                }
//            }
//        });
//
//        registration_page_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(login.this, register.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void signInUser(String email, String password) {
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success
//                            Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                            // Navigate to another activity if needed
//                            // Intent intent = new Intent(login.this, NextActivity.class);
//                            // startActivity(intent);
//                            // finish();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//    boolean isEmail(EditText text) {
//        CharSequence email_txt = text.getText().toString();
//        return (!TextUtils.isEmpty(email_txt) && Patterns.EMAIL_ADDRESS.matcher(email_txt).matches());
//    }
//
//    private boolean checkInformation() {
//        if (email_txt.getText().toString().isEmpty()) {
//            email_txt.setError("Email is Required");
//            return false;
//        } else if (!isEmail(email_txt)) {
//            email_txt.setError("Please Enter a Valid Email!");
//            return false;
//        }
//
//        if (password_txt.getText().toString().isEmpty() || password_txt.length() < 6) {
//            password_txt.setError("Please Enter a Password With at Least 6 Characters");
//            return false;
//        }
//
//        return true;
//    }
//}


//
public class login extends AppCompatActivity {
    EditText email_txt;
    EditText password_txt;
    Button login_btn;
    Button registration_page_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupViews();
    }

    private void setupViews() {
        email_txt = findViewById(R.id.uemail_txt);
        password_txt = findViewById(R.id.pw_txt);
        login_btn = findViewById(R.id.booking);
        registration_page_btn = findViewById(R.id.register_btn);

        // Set OnClickListener for login button
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInformation()) {
                    if (email_txt.getText().toString().equals("test@gmail.com") &&
                            password_txt.getText().toString().trim().equals("test123")) {
                        Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login.this, welcome.class);
                        startActivity(intent);
                        finish();
                    }if (email_txt.getText().toString().trim().equals("admin@gmail.com") &&
                            password_txt.getText().toString().equals("admin123")) {
                        Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login.this, adminpage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//        login_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkInformation()) {
//                    String email = email_txt.getText().toString();
//                    String password = password_txt.getText().toString();
//
//                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        // Authentication successful
//                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                                        String uid = user.getUid();
//
//                                        // Check if the user is an admin
//                                        if (email.equals("test@gmail.com") && password.equals("test123")) {
//                                            // Open admin page
//                                            Intent intent = new Intent(login.this, adminpage.class);
//                                            startActivity(intent);
//                                            finish(); // Close login activity
//                                        } else {
//                                            // Normal user login successful
//                                            Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(login.this, welcome.class);
//                                            startActivity(intent);
//                                            finish(); // Close login activity
//                                        }
//                                    } else {
//                                        // Authentication failed
//                                        Exception exception = task.getException();
//                                        Log.e("FirebaseAuth", "signInWithEmailAndPassword failed: " + exception.getMessage(), exception);
//
//                                        // Handle login failure
//                                        Toast.makeText(login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//
//                }
//            }
//        });


        // Set OnClickListener for registration button
        registration_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
    }


    boolean isEmail(EditText text) {
        CharSequence email_txt = text.getText().toString();
        return (!TextUtils.isEmpty(email_txt) && Patterns.EMAIL_ADDRESS.matcher(email_txt).matches());
    }

    private boolean checkInformation() {
        if (email_txt.getText().toString().isEmpty()) {
            email_txt.setError("Email is Required");
            return false;
        } else if (!isEmail(email_txt)) {
            email_txt.setError("Please Enter a Valid Email!");
            return false;
        }

        if (password_txt.getText().toString().isEmpty() || password_txt.length() < 6) {
            password_txt.setError("Please Enter a Password With at Least 6 Characters");
            return false;
        }

        return true;
    }
}
