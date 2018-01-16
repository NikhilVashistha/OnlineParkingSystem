package com.ndroidpro.carparkingsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ndroidpro.carparkingsystem.Constants;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.model.CarParkingLocationModel;

public class AddNewParkingLocation extends BaseActivity {

    private EditText etParkingLocationName;
    private EditText etParkingAvailable;
    private AppCompatButton btnAddParking;

    private boolean editDetails;
    private CarParkingLocationModel mCarParkingLocationModel;

    private DatabaseReference mDatabase;
    private String mLocationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_parking_location);

        Intent intent = getIntent();
        if(intent != null) {
            editDetails = intent.getBooleanExtra(Constants.INTENT_EDIT_CAR_PARKING_LOCATION, false);
            mCarParkingLocationModel = intent.getParcelableExtra(Constants.INTENT_EDIT_CAR_PARKING_LOCATION_DATA);
        }

        findViews();

        setDataToEdit();
    }

    private void setDataToEdit() {
        if(mCarParkingLocationModel != null && editDetails) {
            etParkingLocationName.setText(mCarParkingLocationModel.getCarParkingLocationName());
            etParkingAvailable.setText(String.valueOf(mCarParkingLocationModel.getAvailableCarParking()));
        }
    }

    private void findViews() {
        etParkingLocationName = (EditText)findViewById( R.id.et_parking_location_name );
        etParkingAvailable = (EditText)findViewById( R.id.et_parking_available );
        btnAddParking = (AppCompatButton)findViewById( R.id.btn_add_parking );

        btnAddParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDetails();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if( editDetails ) {
            mLocationId = mCarParkingLocationModel.getCarParkingLocationId();
        }else {
            DatabaseReference databaseReference = mDatabase
                    .child(Constants.DB_CAR_PARKING_LOCATION_LIST);
            mLocationId = databaseReference.push().getKey();
        }
    }

    private void saveDetails() {
        String parkingLocationName = etParkingLocationName.getText().toString().trim();
        String parkingAvailable = etParkingAvailable.getText().toString().trim();

        if( TextUtils.isEmpty(parkingLocationName) ){
            Toast.makeText(getApplicationContext(), "Enter Parking Location Name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if( TextUtils.isEmpty(parkingAvailable) ){
            Toast.makeText(getApplicationContext(), "Enter No. of Available Parking!", Toast.LENGTH_SHORT).show();
            return;
        }

        if( !TextUtils.isDigitsOnly(parkingAvailable) ){
            Toast.makeText(getApplicationContext(), "Enter Digits only!", Toast.LENGTH_SHORT).show();
            return;
        }

        if( isInternetNotAvailable() ) {
            return;
        }

        final CarParkingLocationModel carParkingLocationModel = new CarParkingLocationModel();
        carParkingLocationModel.setCarParkingLocationName(parkingLocationName);
        carParkingLocationModel.setAvailableCarParking(Integer.parseInt(parkingAvailable));

        setEditingEnabled(false);

        showProgressDialog();

        mDatabase
                .child(Constants.DB_CAR_PARKING_LOCATION_LIST)
                .child(mLocationId)
                .setValue(carParkingLocationModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            ToastUtils.showLong("Event Details Saved Successfully");
                            setEditingEnabled(true);
                            finish();
                        } else {
                            ToastUtils.showLong("Please Try Again");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        ToastUtils.showLong(e.getMessage());
                        setEditingEnabled(true);
                    }
                });
    }

    private void setEditingEnabled(boolean enabled) {
        etParkingLocationName.setEnabled(enabled);
        etParkingAvailable.setEnabled(enabled);
        if (enabled) {
            btnAddParking.setVisibility(View.VISIBLE);
        } else {
            btnAddParking.setVisibility(View.GONE);
        }
    }
}
