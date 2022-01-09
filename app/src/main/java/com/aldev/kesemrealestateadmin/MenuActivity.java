package com.aldev.kesemrealestateadmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MenuActivity extends AppCompatActivity {

    private AppCompatButton BTN_addPropertyPage;
    private AppCompatButton BTN_managePropertiesPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViews();

        BTN_addPropertyPage.setOnClickListener(view -> {
            Intent i = new Intent(MenuActivity.this, AddPropertyActivity.class);

            startActivity(i);
        });

        BTN_managePropertiesPage.setOnClickListener(view -> {
            Intent i = new Intent(MenuActivity.this, ManagePropertiesActivity.class);

            startActivity(i);
        });
    }

    private void findViews() {
        BTN_addPropertyPage = findViewById(R.id.BTN_add_property);
        BTN_managePropertiesPage = findViewById(R.id.BTN_manage_properties);

    }
}