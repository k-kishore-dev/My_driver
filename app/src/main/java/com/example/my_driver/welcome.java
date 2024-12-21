package com.example.my_driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class welcome extends AppCompatActivity {
    TextView username;
    Button next;
    Database_driver dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        username=findViewById(R.id.welcome_message);
        next=findViewById(R.id.next);
        dd=new Database_driver(this);
        String user=getIntent().getStringExtra("DRIVERNAME");

        username.setText("Welcome:"+user);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(welcome.this, license.class);
                startActivity(intent);
                finish();

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.welcome_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}