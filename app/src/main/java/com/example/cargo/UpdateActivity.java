package com.example.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    private EditText oldValueEditText, newValueEditText;
    private TextView title ;
    private TextView updatekey;
    private Button updateButton;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        db = FirebaseFirestore.getInstance();

        // Initialize views
        title =  findViewById(R.id.fieldNameTextView);
        oldValueEditText = findViewById(R.id.oldValueEditText);
        newValueEditText = findViewById(R.id.newValueEditText);
        updateButton = findViewById(R.id.updateButton);
        Intent intent = getIntent();
        String  title1 = intent.getStringExtra("field" );
        title.setText("Change "+title1);



        // Set click listener for the update button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the old and new values from EditTexts
                String oldValue = oldValueEditText.getText().toString().trim();
                String newValue = newValueEditText.getText().toString().trim();

                oldValueEditText.setText("");
                newValueEditText.setText("");
                Updatedata(oldValue, newValue,title1);
            }
        });
    }

    public void Updatedata(String oldValue, String newValue,String title){
        Log.d("TAG", "33333333");
        oldValueEditText.setText(title);
        Map<String, Object> userDetail = new HashMap();
        userDetail.put(title,newValue);
        db.collection("Users")
                .whereEqualTo(title, oldValue)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("TAG", "1111111111");

                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("Users")
                                    .document(documentID)
                                    .update(userDetail)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(UpdateActivity.this, "successfully Updated", Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UpdateActivity.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        } else {
                            Toast.makeText(UpdateActivity.this, "Faild ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }






    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("oldValue", oldValueEditText.getText().toString());
        outState.putString("newValue", newValueEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreInstanceState(savedInstanceState);
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        oldValueEditText.setText(savedInstanceState.getString("oldValue"));
        newValueEditText.setText(savedInstanceState.getString("newValue"));
        }

    protected void onPause() {
        super.onPause();
        saveInstanceState();
    }

    private void saveInstanceState() {
        // Saving instance state to handle configuration changes
        Bundle outState = new Bundle();
        outState.putString("oldValue", oldValueEditText.getText().toString());
        outState.putString("newValue", newValueEditText.getText().toString());
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
        oldValueEditText.setText("");
        newValueEditText.setText("");

    }
}

