package com.example.cargo;

import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;

public class CustomerListActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colRef = db.collection("Users");

    private ListView customerListView;
    private CustomerAdapter adapter;
    private ArrayList<HashMap<String, String>> customerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        FirebaseApp.initializeApp(this);
        FirebaseFirestore.setLoggingEnabled(true);

        customerListView = findViewById(R.id.customer_list_view);
        adapter = new CustomerAdapter(this, customerList);
        customerListView.setAdapter(adapter);

        if (savedInstanceState == null) {
            fetchCustomers(); // Fetch data only if savedInstanceState is null
        } else {
            // Restore data if savedInstanceState is not null
            customerList = (ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("customerList");
            if (customerList != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save data to retain across configuration changes
        outState.putSerializable("customerList", customerList);
    }

    private void fetchCustomers() {
        colRef.whereEqualTo("isAdmin", false).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            customerList.clear(); // Clear the list to avoid duplicates
                            Log.d("CustomerListActivity", "Documents retrieved: " + task.getResult().size());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String firstName = document.getString("firstName");
                                String lastName = document.getString("lastName");
                                String email = document.getString("email");
                                String phone = document.getString("phoneNumber");

                                if (firstName != null && lastName != null) {
                                    HashMap<String, String> customer = new HashMap<>();
                                    customer.put("fullName", firstName + " " + lastName);
                                    customer.put("email", email);
                                    customer.put("phone", phone);
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
