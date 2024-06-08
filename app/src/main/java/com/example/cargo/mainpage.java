package com.example.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class mainpage  extends AppCompatActivity {
    private Button serbtn;

    public EditText location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        serbtn = findViewById(R.id.search);
        location = findViewById(R.id.edt_pickupLocation);

        serbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if location EditText is empty
                String pickupLocation = location.getText().toString().trim();
                if(!TextUtils.isEmpty(pickupLocation)) {
                    // Location is provided, so fetch cars not rented and matching the provided location
                    Intent intent = new Intent(mainpage.this, RentRecyclerView.class);
                    intent.putExtra("location", pickupLocation);
                    startActivity(intent);
                } else {
                    // Location is not provided, so fetch all cars not rented
                    Intent intent = new Intent(mainpage.this, RentRecyclerView.class);
                    startActivity(intent);
                }
            }
        });

    }
}
