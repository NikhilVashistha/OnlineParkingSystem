package com.ndroidpro.carparkingsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ndroidpro.carparkingsystem.Constants;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.model.CarParkingLocationModel;

public class AddNewParkingLocation extends BaseActivity {

    private EditText etParkingLocationName;
    private EditText etParkingAvailable;
    private AppCompatButton btnAddParking;

    private boolean editDetails;
    private CarParkingLocationModel mCarParkingLocationModel;

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

    private void saveDetails() {
        String parkingLocationName = etParkingLocationName.getText().toString();
        String parkingAvailable = etParkingAvailable.getText().toString();

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
    }
}
