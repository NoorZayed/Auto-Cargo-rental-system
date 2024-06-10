
package com.example.cargo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox rememberBox;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button registration_page_btn;
    private TextView forgotPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.uemail_txt);
        passwordEditText = findViewById(R.id.pw_txt);
        rememberBox = findViewById(R.id.rememberBox);
        loginButton = findViewById(R.id.booking);
        registration_page_btn = findViewById(R.id.register_btn);
        forgotPasswordTextView = findViewById(R.id.forgot_password_txt);

        loadPreferences();

        if (savedInstanceState != null) {
            emailEditText.setText(savedInstanceState.getString("email"));
            passwordEditText.setText(savedInstanceState.getString("password"));
            rememberBox.setChecked(savedInstanceState.getBoolean("rememberMe"));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        registration_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPasswordDialog();
            }
        });
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");
        boolean isRemembered = sharedPreferences.getBoolean("rememberMe", false);

        emailEditText.setText(savedEmail);
        passwordEditText.setText(savedPassword);
        rememberBox.setChecked(isRemembered);
    }

    private void savePreferences(String email, String password, boolean rememberMe) {
        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putBoolean("rememberMe", rememberMe);
        editor.apply();
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required.");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required.");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (rememberBox.isChecked()) {
                            savePreferences(email, password, true);
                        } else {
                            savePreferences("", "", false);
                        }
                        checkAdmin(authResult.getUser().getEmail(), password);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkAdmin(String email, String password) {
        db.collection("Users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Boolean isAdmin = documentSnapshot.getBoolean("isAdmin");
                                if (isAdmin != null && isAdmin) {
                                    // Redirect to admin page
                                    startActivity(new Intent(login.this, adminpage.class));
                                } else {
                                    // Redirect to welcome page
                                    startActivity(new Intent(login.this, welcome.class));
                                }
                                updatePasswordInFirestore(email, password); // Update the password in Firestore
                                finish();
                                return; // Exit loop after finding a matching document
                            }
                        } else {
                            Toast.makeText(login.this, "No user found with this email", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Error checking user status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showForgotPasswordDialog() {
        final EditText emailInput = new EditText(this);
        emailInput.setHint("Enter your email");

        new AlertDialog.Builder(this)
                .setTitle("Forgot Password")
                .setView(emailInput)
                .setPositiveButton("Send Reset Link", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = emailInput.getText().toString().trim();
                        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(login.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        sendPasswordResetEmail(email);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void sendPasswordResetEmail(final String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(login.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                        // Return to the login page
                        startActivity(new Intent(login.this, login.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Failed to send password reset email: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updatePasswordInFirestore(String email, String password) {
        db.collection("Users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                documentSnapshot.getReference().update("password", password)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //  Toast.makeText(login.this, "Password updated in Firestore", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //  Toast.makeText(login.this, "Error updating password in Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(login.this, "No user found with this email", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Error checking email: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email", emailEditText.getText().toString());
        outState.putString("password", passwordEditText.getText().toString());
        outState.putBoolean("rememberMe", rememberBox.isChecked());
    }
}
