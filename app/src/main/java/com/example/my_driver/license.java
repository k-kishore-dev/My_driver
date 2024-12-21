package com.example.my_driver;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class license extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText licenseNoEditText, experienceEditText;
    private ImageView licenseImageView;
    private TextView addImageTextView;
    private Button submitButton;

    private Bitmap selectedImageBitmap; // To store the selected image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        // Initialize views
        licenseNoEditText = findViewById(R.id.license_no);
        experienceEditText = findViewById(R.id.experience);
        licenseImageView = findViewById(R.id.Licence_img);
        addImageTextView = findViewById(R.id.textView);
        submitButton = findViewById(R.id.submit);

        licenseImageView.setOnClickListener(view -> openImagePicker());

        addImageTextView.setOnClickListener(view -> openImagePicker());

        submitButton.setOnClickListener(view -> handleSubmit());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                licenseImageView.setImageBitmap(selectedImageBitmap); // Display the selected image
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleSubmit() {
        String licenseNo = licenseNoEditText.getText().toString().trim();
        String experienceText = experienceEditText.getText().toString().trim();

        if (licenseNo.isEmpty() || experienceText.isEmpty() || selectedImageBitmap == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int experience = Integer.parseInt(experienceText);

            // convert the image to a byte array
            byte[] imageBytes = convertBitmapToByteArray(selectedImageBitmap);

            // Insert into the database
            Database_image db = new Database_image(this);
            boolean isInserted = db.insertdata(licenseNo, experience, imageBytes);

            if (isInserted) {
                Toast.makeText(this, "Data Submitted Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data Submission Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Experience must be a number", Toast.LENGTH_SHORT).show();
        }
        Intent intent=new Intent(license.this, Profile.class);
        startActivity(intent);
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
