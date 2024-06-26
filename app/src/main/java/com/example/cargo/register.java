
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference colRef;
    Button login;

    EditText firstname_txt;
    EditText lastname_txt;
    EditText email_txt;
    EditText number_txt;
    EditText password;

    Button registration_btn;
    Button login_page_btn;
    boolean isAllChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        login = findViewById(R.id.login_page_btn);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        colRef = db.collection("Users");

        setupViews();
        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }
    }

    private void setupViews() {
        firstname_txt = findViewById(R.id.rfirstname_txt);
        lastname_txt = findViewById(R.id.rlastname_txt);
        email_txt = findViewById(R.id.remail_txt);
        password = findViewById(R.id.rpassword);
        number_txt = findViewById(R.id.rnumber_txt);

        registration_btn = findViewById(R.id.registers_btn);
        login_page_btn = findViewById(R.id.login_page_btn);

        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllChecked = checkInformation();

                if (isAllChecked) {
                    checkIfEmailExists();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email_txt = text.getText().toString();
        return (!TextUtils.isEmpty(email_txt) && Patterns.EMAIL_ADDRESS.matcher(email_txt).matches());
    }

    private boolean checkInformation() {
        if (firstname_txt.getText().toString().isEmpty()) {
            firstname_txt.setError("First Name is Required");
            return false;
        }

        if (lastname_txt.getText().toString().isEmpty()) {
            lastname_txt.setError("Last Name is Required");
            return false;
        }

        if (email_txt.getText().toString().isEmpty()) {
            email_txt.setError("Email is Required");
            return false;
        } else if (!isEmail(email_txt)) {
            email_txt.setError("Please Enter a Valid Email!");
            return false;
        }

        if (number_txt.getText().toString().isEmpty()) {
            number_txt.setError("Phone Number is Required");
            return false;
        }

        if (password.getText().toString().isEmpty() || password.length() < 6) {
            password.setError("Please Enter a Password With at Least 6 Characters");
            return false;
        }

        return true;
    }

    private void checkIfEmailExists() {
        String email = email_txt.getText().toString().trim().toLowerCase();

        colRef.whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (!querySnapshot.isEmpty()) {
                        Toast.makeText(register.this, "Email is already in use.", Toast.LENGTH_SHORT).show();
                    } else {
                        registerUser();
                    }
                } else {
                    Toast.makeText(register.this, "Failed to check email: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser() {
        String email = email_txt.getText().toString().trim().toLowerCase();
        String passwordS = password.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    writeToDatabase();
                } else {
                    Toast.makeText(register.this, "Registration failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void writeToDatabase() {
        String fname = firstname_txt.getText().toString().trim();
        String lname = lastname_txt.getText().toString().trim();
        String email = email_txt.getText().toString().trim().toLowerCase();
        String pass = password.getText().toString().trim();
        String phone = number_txt.getText().toString().trim();

        Users users = new Users(fname, lname, email, phone, pass);

        colRef.add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(register.this, "Data added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(register.this, login.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Error333", e.toString());
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("firstname", firstname_txt.getText().toString());
        outState.putString("lastname", lastname_txt.getText().toString());
        outState.putString("email", email_txt.getText().toString());
        outState.putString("phone", number_txt.getText().toString());
        outState.putString("password", password.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreInstanceState(savedInstanceState);
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        firstname_txt.setText(savedInstanceState.getString("firstname"));
        lastname_txt.setText(savedInstanceState.getString("lastname"));
        email_txt.setText(savedInstanceState.getString("email"));
        number_txt.setText(savedInstanceState.getString("phone"));
        password.setText(savedInstanceState.getString("password"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveInstanceState();
    }

    private void saveInstanceState() {
        // Saving instance state to handle configuration changes
        Bundle outState = new Bundle();
        outState.putString("firstname", firstname_txt.getText().toString());
        outState.putString("lastname", lastname_txt.getText().toString());
        outState.putString("email", email_txt.getText().toString());
        outState.putString("phone", number_txt.getText().toString());
        outState.putString("password", password.getText().toString());
        onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveInstanceState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clear data if the activity is destroyed
        clearFields();
    }

    private void clearFields() {
        firstname_txt.setText("");
        lastname_txt.setText("");
        email_txt.setText("");
        number_txt.setText("");
        password.setText("");
    }
}
