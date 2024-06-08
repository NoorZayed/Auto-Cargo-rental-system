package com.example.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class mainpage  extends AppCompatActivity {
    private Button serbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        serbtn = findViewById(R.id.search);

        serbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the next Activity (replace NextActivity with your actual class name)
                Intent intent = new Intent(mainpage.this ,RentRecyclerView.class);
                // Start the next Activity
                startActivity(intent);
            }
        });
}
}
