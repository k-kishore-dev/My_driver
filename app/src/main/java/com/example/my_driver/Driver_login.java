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

public class Driver_login extends AppCompatActivity {
    TextView textView;
    EditText drivername,password;
    Button login;
    Database_driver databasedriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_login);

        textView=findViewById(R.id.not_having_acc);
        drivername=findViewById(R.id.dri_name);
        password=findViewById(R.id.dri_password);
        login=findViewById(R.id.login_btn);
        databasedriver=new Database_driver(this);

        textView.setOnClickListener(view -> {
            Intent intent =new Intent(getApplicationContext(),Driver_signup.class);
            startActivity(intent);
            finish();
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String driver=drivername.getText().toString().trim();
                String pass=password.getText().toString().trim();
                if(driver.isEmpty() ||  pass.isEmpty()){
                    Toast.makeText(Driver_login.this,"Please enter username and password",Toast.LENGTH_SHORT).show();
                }
                else {
                    String loggedIn=databasedriver.checklogin(driver,pass);
                    if(loggedIn != null){
                        Intent intent=new Intent(Driver_login.this,Profile.class);
                        intent.putExtra("DRIVERNAME",loggedIn);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(Driver_login.this,"invalid login",Toast.LENGTH_SHORT).show();
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