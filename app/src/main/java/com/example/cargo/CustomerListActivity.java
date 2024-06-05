package com.example.cargo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CustomerListActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colRef = db.collection("Users");

    private ListView customerListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> customerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        FirebaseApp.initializeApp(this);
        FirebaseFirestore.setLoggingEnabled(true);

        customerListView = findViewById(R.id.customer_list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customerList);
        customerListView.setAdapter(adapter);

        if (savedInstanceState == null) {
            fetchCustomers(); // Fetch data only if savedInstanceState is null
        } else {
            // Restore data if savedInstanceState is not null
            customerList = savedInstanceState.getStringArrayList("customerList");
            if (customerList != null) {
                adapter.addAll(customerList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save data to retain across configuration changes
        outState.putStringArrayList("customerList", customerList);
    }

    private void fetchCustomers() {
        colRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            customerList.clear(); // Clear the list to avoid duplicates
                            Log.d("CustomerListActivity", "Documents retrieved: " + task.getResult().size());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String firstName = document.getString("fname");
                                String lastName = document.getString("lname");
                                if (firstName != null && lastName != null) {
                                    String customer = firstName + " " + lastName;
                                    Log.d("CustomerListActivity", "Customer: " + customer);
                                    customerList.add(customer);
                                } else {
                                    Log.w("CustomerListActivity", "Missing field in document " + document.getId());
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.w("CustomerListActivity", "Error getting documents.", task.getException());
                            Toast.makeText(CustomerListActivity.this, "Failed to fetch customers.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
