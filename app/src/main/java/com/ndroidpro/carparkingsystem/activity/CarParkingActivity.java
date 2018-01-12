package com.ndroidpro.carparkingsystem.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ndroidpro.carparkingsystem.adapter.AbstractItem;
import com.ndroidpro.carparkingsystem.adapter.CarParkingAdapter;
import com.ndroidpro.carparkingsystem.adapter.CenterItem;
import com.ndroidpro.carparkingsystem.adapter.EdgeItem;
import com.ndroidpro.carparkingsystem.adapter.EmptyItem;
import com.ndroidpro.carparkingsystem.listener.OnParkingSelected;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.service.ScheduleClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CarParkingActivity extends BaseActivity implements OnParkingSelected {

    public static final int COLUMNS = 3;
    private AppCompatButton mBtnSeatSelected;
    private int parkingReservationTime = 0;

    // This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;
    private RecyclerView recyclerView;
    private CarParkingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_parking);

        mBtnSeatSelected = (AppCompatButton)findViewById(R.id.txt_seat_selected);

        GridLayoutManager manager = new GridLayoutManager(this, COLUMNS);
        recyclerView = (RecyclerView) findViewById(R.id.car_parking_list_recyclerview);
        recyclerView.setLayoutManager(manager);

        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

        mBtnSeatSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Will give the current date time calendar
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.SECOND, parkingReservationTime);
                // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
                scheduleClient.setAlarmForNotification(calendar);
            }
        });
    }

    private void setDataToRecyclerView() {
        List<AbstractItem> items = new ArrayList<>();
        for (int i=0; i<30; i++) {

            if (i%COLUMNS==0 || i%COLUMNS==4) {
                items.add(new EdgeItem(String.valueOf(i)));
            } else if (i%COLUMNS==1 || i%COLUMNS==3) {
                items.add(new EmptyItem(String.valueOf(i)));
            } else {
                items.add(new CenterItem(String.valueOf(i)));
            }
        }

        adapter = new CarParkingAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        setDataToRecyclerView();
    }

    @Override
    public void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }

    @Override
    public void onParkingSelected(int price, int hour) {
        Resources res = getResources();
        String text = res.getQuantityString(R.plurals.price_label, hour, price, hour);
        mBtnSeatSelected.setVisibility(View.VISIBLE);
        mBtnSeatSelected.setText(text);
        parkingReservationTime = hour;
    }
}
