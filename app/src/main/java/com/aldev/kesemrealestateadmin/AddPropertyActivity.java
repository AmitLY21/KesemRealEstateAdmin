package com.aldev.kesemrealestateadmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPropertyActivity extends AppCompatActivity {

    private AppCompatButton addProperty;
    private AppCompatButton addPropertyImages;
    private CheckBox mamad;
    private CheckBox balcony;
    private CheckBox storage;
    private CheckBox elevator;
    private TextInputLayout address;
    private TextInputLayout description;
    private TextInputLayout numberOfRooms;
    private TextInputLayout numberOfBathrooms;
    private TextInputLayout numberOfParkingLots;
    private TextInputLayout numberOfFloors;
    private TextInputLayout meterSquare;
    private TextInputLayout propertyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        addProperty.setOnClickListener(view -> {
            if (validInput())
                addPropertyToDB();
            else {
                Toast.makeText(AddPropertyActivity.this, "פרטים חסרים!", Toast.LENGTH_SHORT).show();
            }
        });

        addPropertyImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(AddPropertyActivity.this, UploadPicturesActivity.class);
                if (propertyNumber.getEditText().getText().toString() != null) {
                    switchActivityIntent.putExtra("propertyNumber", propertyNumber.getEditText().getText().toString());
                    startActivity(switchActivityIntent);
                } else {
                    Toast.makeText(AddPropertyActivity.this, "חסר מספר נכס!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validInput() {
        return !TextUtils.isEmpty(description.getEditText().getText().toString()) &&
                !TextUtils.isEmpty(address.getEditText().getText().toString()) &&
                !TextUtils.isEmpty(numberOfRooms.getEditText().getText().toString()) &&
                !TextUtils.isEmpty(numberOfBathrooms.getEditText().getText().toString()) &&
                !TextUtils.isEmpty(numberOfParkingLots.getEditText().getText().toString()) &&
                !TextUtils.isEmpty(propertyNumber.getEditText().getText().toString()) &&
                !TextUtils.isEmpty(meterSquare.getEditText().getText().toString());
    }

    private void addPropertyToDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://realestate-9cb72-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("properties");
        Property p = createProperty();

        if (p != null) {
            myRef.push().setValue(p);
            Toast.makeText(AddPropertyActivity.this, "הנכס התווסף בהצלחה!", Toast.LENGTH_LONG).show();
        }

    }

    private Property createProperty() {
        Property property = new Property();
        property.setAddress(address.getEditText().getText().toString())
                .setDescription(description.getEditText().getText().toString())
                .setNumOfRooms(Integer.parseInt(numberOfRooms.getEditText().getText().toString()))
                .setNumOfBathrooms(Integer.parseInt(numberOfBathrooms.getEditText().getText().toString()))
                .setNumOfParkingSpaces(Integer.parseInt(numberOfParkingLots.getEditText().getText().toString()))
                .setNumOfFloors(Integer.parseInt(numberOfFloors.getEditText().getText().toString()))
                .setSquareFoot(Integer.parseInt(meterSquare.getEditText().getText().toString()))
                .setMamad(mamad.isChecked())
                .setStorage(storage.isChecked())
                .setBalcony(balcony.isChecked())
                .setElevator(elevator.isChecked())
                .setPropertyNumber(propertyNumber.getEditText().getText().toString());

        return property;
    }

    private void findViews() {
        address = findViewById(R.id.form_EDT_address);
        description = findViewById(R.id.form_EDT_description);
        numberOfRooms = findViewById(R.id.form_EDT_roomNumber);
        numberOfBathrooms = findViewById(R.id.form_EDT_bathroomNumber);
        numberOfParkingLots = findViewById(R.id.form_EDT_parkinglotNumber);
        numberOfFloors = findViewById(R.id.form_EDT_floorsNumber);
        meterSquare = findViewById(R.id.form_EDT_squareMeter);
        propertyNumber = findViewById(R.id.form_EDT_propertyNumber);

        mamad = findViewById(R.id.mamad);
        balcony = findViewById(R.id.balcony);
        storage = findViewById(R.id.storage);
        elevator = findViewById(R.id.elevator);

        addProperty = findViewById(R.id.form_BTN_addProperty);
        addPropertyImages = findViewById(R.id.form_BTN_addPropertyImages);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}