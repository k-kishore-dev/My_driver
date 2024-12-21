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

public class Driver_signup extends AppCompatActivity {
    TextView ahaa;
    EditText drivername,password,phone,mail;
    Button signup;
    Database_driver databasedriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_signup);

        ahaa=findViewById(R.id.ahaa);
        phone=findViewById(R.id.dri_phone);
        mail=findViewById(R.id.dri_mail);
        drivername=findViewById(R.id.dri_name);
        password=findViewById(R.id.dri_password);
        databasedriver=new Database_driver(this);
        signup=findViewById(R.id.signup_btn);

        ahaa.setOnClickListener(view -> {
            Intent intent =new Intent(getApplicationContext(),Driver_login.class);
            startActivity(intent);
            finish();
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String driver=drivername.getText().toString().trim();
                String pass=password.getText().toString().trim();
                String d_mail=mail.getText().toString().trim();
                String d_phone=phone.getText().toString().trim();
                if(driver.isEmpty() ||  pass.isEmpty() || d_phone.isEmpty() || d_mail.isEmpty() ){
                    Toast.makeText(Driver_signup.this,"Please enter all requirements",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(databasedriver.insertdata(driver,pass,d_phone,d_mail)){
                        Toast.makeText(Driver_signup.this,"Registration sucessful",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Driver_signup.this, welcome.class);
                        intent.putExtra("DRIVERNAME",driver);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(Driver_signup.this,"Username already exist",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}