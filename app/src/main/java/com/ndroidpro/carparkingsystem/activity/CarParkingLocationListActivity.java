package com.ndroidpro.carparkingsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ndroidpro.carparkingsystem.Constants;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.adapter.CarParkingLocationAdapter;
import com.ndroidpro.carparkingsystem.listener.ClickListener;
import com.ndroidpro.carparkingsystem.model.CarParkingLocationModel;

public class CarParkingLocationListActivity extends BaseActivity {

    private CarParkingLocationAdapter carParkingLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_parking_location_list);

        FloatingActionButton addCarParkingLocation = (FloatingActionButton) findViewById(R.id.add_car_parking_location);

        addCarParkingLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CarParkingLocationListActivity.this, AddNewParkingLocation.class);
                startActivity(intent);
            }
        });

        if(isUserAdmin()) {
            addCarParkingLocation.setVisibility(View.VISIBLE);
        }else {
            addCarParkingLocation.setVisibility(View.GONE);
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.location_list_recyclerview);
        recyclerView.setLayoutManager(manager);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        Query postsQuery = database.child(Constants.DB_CAR_PARKING_LOCATION_LIST);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<CarParkingLocationModel>()
                .setQuery(postsQuery, CarParkingLocationModel.class)
                .build();

        carParkingLocationAdapter = new CarParkingLocationAdapter(options);

        carParkingLocationAdapter.setOnItemClickListener(new ClickListener() {
            @Override
            public void onItemClick(CarParkingLocationModel carParkingLocationModel, String locationId) {
                if (isUserAdmin()) {
                    carParkingLocationModel.setCarParkingLocationId(locationId);
                    Intent intent = new Intent(CarParkingLocationListActivity.this,
                            AddNewParkingLocation.class);
                    intent.putExtra(Constants.INTENT_EDIT_CAR_PARKING_LOCATION, true);
                    intent.putExtra(Constants.INTENT_EDIT_CAR_PARKING_LOCATION_DATA, carParkingLocationModel);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CarParkingLocationListActivity.this, CarParkingActivity.class);
                    intent.putExtra(Constants.INTENT_EDIT_CAR_PARKING_LOCATION_DATA, carParkingLocationModel);
                    startActivity(intent);
                }
            }
        });

        recyclerView.setAdapter(carParkingLocationAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        carParkingLocationAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        carParkingLocationAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(CarParkingLocationListActivity.this, ProfileActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
