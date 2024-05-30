package com.example.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class register extends AppCompatActivity {
    private Button login ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
       login= findViewById(R.id.login_page_btn);
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                           // Create an Intent to start the next Activity (replace NextActivity with your actual class name)
            Intent intent = new Intent(register.this ,login.class);
            // Start the next Activity
            startActivity(intent);

           }
    });
    }

}
