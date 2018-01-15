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

import com.ndroidpro.carparkingsystem.Constants;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.adapter.CarParkingLocationAdapter;
import com.ndroidpro.carparkingsystem.listener.ClickListener;
import com.ndroidpro.carparkingsystem.model.CarParkingLocationModel;

import java.util.ArrayList;
import java.util.List;

public class CarParkingLocationListActivity extends BaseActivity {

    private List<CarParkingLocationModel> locationList;

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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.location_list_recyclerview);
        recyclerView.setLayoutManager(manager);

        locationList = new ArrayList<>();
        locationList.add(new CarParkingLocationModel("GNG Mall", 10));
        locationList.add(new CarParkingLocationModel("Gandhi Park", 30));
        CarParkingLocationAdapter adapter = new CarParkingLocationAdapter( locationList );
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(isUserAdmin()) {
                    Intent intent = new Intent(CarParkingLocationListActivity.this, AddNewParkingLocation.class);
                    intent.putExtra(Constants.INTENT_EDIT_CAR_PARKING_LOCATION, true);
                    intent.putExtra(Constants.INTENT_EDIT_CAR_PARKING_LOCATION_DATA, locationList.get(position));
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(CarParkingLocationListActivity.this, CarParkingActivity.class);
                    startActivity(intent);
                }
            }
        });
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
