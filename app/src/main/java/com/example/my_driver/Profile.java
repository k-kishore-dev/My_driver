package com.example.my_driver;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    private TextView licenseNoTextView, experienceTextView;
    private ImageView licenseImageView;
    public Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize the views
        licenseNoTextView = findViewById(R.id.linense_no);
        experienceTextView = findViewById(R.id.experience_yrs);
        licenseImageView = findViewById(R.id.my_license);
        logout=findViewById(R.id.logout);


        // retrieve and display data
        displayProfileDetails();
    }


    private void displayProfileDetails() {
        Database_image db = new Database_image(this);

        // Query to retrieve data (assuming you want the latest entry)
        String query = "SELECT LICENSE_NO, EXPIRIENCE, LICENSE FROM DRIVERS_DETAILS ORDER BY ID DESC LIMIT 1";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        logout.setOnClickListener(view -> {
            Intent intent =new Intent(getApplicationContext(),Driver_login.class);
            startActivity(intent);
            finish();
        });

        if (cursor != null && cursor.moveToFirst()) {
            try {
                String licenseNo = cursor.getString(cursor.getColumnIndex("LICENSE_NO"));
                int experience = cursor.getInt(cursor.getColumnIndex("EXPIRIENCE"));
                byte[] licenseImageBytes = cursor.getBlob(cursor.getColumnIndex("LICENSE"));

                // display the data
                licenseNoTextView.setText("License No: " + licenseNo);
                experienceTextView.setText("Experience: " + experience + " years");

                Bitmap licenseBitmap = BitmapFactory.decodeByteArray(licenseImageBytes, 0, licenseImageBytes.length);
                licenseImageView.setImageBitmap(licenseBitmap);

            } catch (Exception e) {
                Toast.makeText(this, "Failed to load profile details", Toast.LENGTH_SHORT).show();
            } finally {
                cursor.close();
            }
        } else {
            Toast.makeText(this, "No profile data found", Toast.LENGTH_SHORT).show();
        }
    }

}
