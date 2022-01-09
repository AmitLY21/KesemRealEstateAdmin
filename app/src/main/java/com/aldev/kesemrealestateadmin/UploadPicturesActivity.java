package com.aldev.kesemrealestateadmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class UploadPicturesActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private final ArrayList<Uri> ImageList = new ArrayList<>();
    private AppCompatButton mButtonChooseImage;
    private AppCompatButton mButtonUpload;
    private Uri ImageUri;
    private String propertyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pictures);

        findViews();
        propertyNumber = getIntent().getStringExtra("propertyNumber");

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });

        mButtonChooseImage.setOnClickListener(v -> openFileChooser());
    }

    private void uploadFile() {
        if (ImageList != null) {
            StorageReference ImageFolder = FirebaseStorage.getInstance("gs://realestate-9cb72.appspot.com/").getReference().child("ImageFolder");
            for (int uploadCount = 0; uploadCount < ImageList.size(); uploadCount++) {
                Uri currentImg = ImageList.get(uploadCount);
                StorageReference ImageName = ImageFolder.child("ImagesOfPropertyNumber-" + propertyNumber).child(String.valueOf(uploadCount));

                ImageName.putFile(currentImg).addOnSuccessListener(taskSnapshot ->
                        ImageName.getDownloadUrl().addOnSuccessListener(uri -> {
                            String url = String.valueOf(uri);
                            StoreLink(url);
                        }));
            }
            Toast.makeText(this, "התמונות הועלו בהצלחה!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void StoreLink(String url) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://realestate-9cb72-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Property-" + propertyNumber);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("imgLink", url);
        databaseReference.push().setValue(hashMap);
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {


                if (data.getClipData() != null) {

                    int countClipData = data.getClipData().getItemCount();
                    int currentImageSlect = 0;

                    while (currentImageSlect < countClipData) {

                        ImageUri = data.getClipData().getItemAt(currentImageSlect).getUri();
                        ImageList.add(ImageUri);
                        currentImageSlect = currentImageSlect + 1;
                    }

                } else {
                    Toast.makeText(this, "Please Select Multiple Images", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void findViews() {
        mButtonChooseImage = findViewById(R.id.btn_choose_img);
        mButtonUpload = findViewById(R.id.btn_upload_img);
    }
}