package com.ndroidpro.carparkingsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ndroidpro.carparkingsystem.adapter.CarParkingLocationAdapter;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.listener.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class CarParkingLocationListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_parking_location_list);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.location_list_recyclerview);
        recyclerView.setLayoutManager(manager);

        List<String> locationList = new ArrayList<>();
        locationList.add("GNG Mall");
        locationList.add("Gandhi Park");
        CarParkingLocationAdapter adapter = new CarParkingLocationAdapter( locationList );
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(CarParkingLocationListActivity.this, CarParkingActivity.class);
                startActivity(intent);
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
