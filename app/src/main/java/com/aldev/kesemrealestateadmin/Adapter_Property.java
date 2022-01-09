package com.aldev.kesemrealestateadmin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Map;

public class Adapter_Property extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Activity activity;
    private final ArrayList<Property> properties;

    public Adapter_Property(Activity activity, ArrayList<Property> properties) {
        this.activity = activity;
        this.properties = properties;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_card_view, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PropertyViewHolder propertyViewHolder = (PropertyViewHolder) holder;
        Property property = properties.get(position);

        propertyViewHolder.property_LBL_title.setText(property.getAddress());
        propertyViewHolder.property_LBL_description.setText(property.getDescription());
        propertyViewHolder.propertyNumber.setText("מספר נכס: " + property.getPropertyNumber());

        propertyViewHolder.removeProperty.setOnClickListener(view -> {
            deletePropertyFromDB(view, property);
            deletePropertyPhotosFromDB(property);
        });

    }

    // TODO: 09/01/2022 need to use this function! 
    private void deletePropertyLinksFromDB(String pNumber) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://realestate-9cb72-default-rtdb.europe-west1.firebasedatabase.app");
        database.getReference("Property-" + pNumber).removeValue();
    }

    private void deletePropertyPhotosFromDB(Property property) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://realestate-9cb72-default-rtdb.europe-west1.firebasedatabase.app");
        FirebaseStorage photoRef = FirebaseStorage.getInstance("gs://realestate-9cb72.appspot.com/");
        database.getReference("Property-" + property.getPropertyNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    Map map = (Map) child.getValue();
                    photoRef.getReferenceFromUrl((String) map.get("imgLink")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void deletePropertyFromDB(View view, Property property) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://realestate-9cb72-default-rtdb.europe-west1.firebasedatabase.app");
        database.getReference("properties").orderByChild("propertyNumber").equalTo(property.getPropertyNumber()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            child.getRef().removeValue();
                            Toast.makeText(view.getContext(), "הנכס הוסר בהצלחה!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    private Property getItem(int position) {
        return properties.get(position);
    }

    private class PropertyViewHolder extends RecyclerView.ViewHolder {

        public MaterialTextView property_LBL_title;
        public MaterialTextView property_LBL_description;
        public AppCompatTextView propertyNumber;
        public AppCompatButton removeProperty;

        public PropertyViewHolder(View itemView) {
            super(itemView);
            property_LBL_title = itemView.findViewById(R.id.property_LBL_title);
            property_LBL_description = itemView.findViewById(R.id.property_LBL_address);
            removeProperty = itemView.findViewById(R.id.property_BTN_remove);
            propertyNumber = itemView.findViewById(R.id.propertyNumber);

        }
    }
}
