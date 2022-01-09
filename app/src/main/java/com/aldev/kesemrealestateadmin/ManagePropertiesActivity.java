package com.aldev.kesemrealestateadmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagePropertiesActivity extends AppCompatActivity {

    private RecyclerView property_LST_view;

    public static void getAllProperties(CallBack_Properties callBack_properties) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://realestate-9cb72-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("properties");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Property> properties = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {
                        Property p = child.getValue(Property.class);
                        properties.add(p);
                    } catch (Exception ex) {
                    }

                }
                if (callBack_properties != null) {
                    callBack_properties.dataReady(properties);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_properties);

        findViews();
        initViews();


    }

    private void initViews() {
        CallBack_Properties callBack_properties = properties -> {
            Adapter_Property adapter_property = new Adapter_Property(ManagePropertiesActivity.this, properties);
            // Grid
            property_LST_view.setLayoutManager(new GridLayoutManager(ManagePropertiesActivity.this, 1));
            property_LST_view.setItemAnimator(new DefaultItemAnimator());
            property_LST_view.setAdapter(adapter_property);
        };
        getAllProperties(callBack_properties);
    }


    private void findViews() {
        property_LST_view = findViewById(R.id.property_list);
    }

    public interface CallBack_Properties {
        void dataReady(ArrayList<Property> properties);
    }
}