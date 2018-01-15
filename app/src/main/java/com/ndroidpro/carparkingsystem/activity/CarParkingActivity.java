package com.ndroidpro.carparkingsystem.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.adapter.AbstractItem;
import com.ndroidpro.carparkingsystem.adapter.CarParkingAdapter;
import com.ndroidpro.carparkingsystem.adapter.CenterItem;
import com.ndroidpro.carparkingsystem.adapter.EdgeItem;
import com.ndroidpro.carparkingsystem.listener.OnParkingSelected;
import com.ndroidpro.carparkingsystem.model.CarParkingModel;
import com.ndroidpro.carparkingsystem.service.ScheduleClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CarParkingActivity extends BaseActivity implements OnParkingSelected {

    public static final int COLUMNS = 2;
    private AppCompatButton mBtnSeatSelected;
    private int parkingReservationTime = 0;

    // This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;
    private RecyclerView recyclerView;
    private CarParkingAdapter adapter;

    private NotificationManager mNM;
    // Unique id to identify the notification.
    private static final int NOTIFICATION = 1234;
    private CarParkingModel mCarParkingModel;

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
                showNotification();
                // Will give the current date time calendar
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.SECOND, parkingReservationTime);
                // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
                scheduleClient.setAlarmForNotification(calendar);
            }
        });

        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel Car Parking",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNM.createNotificationChannel(channel);
        }
    }

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showNotification() {
        // This is the 'title' of the notification
        CharSequence title = "Car Parking Booked!!";
        String tickerText = "Parking Booked";

        // This is the icon to use on the notification
        int icon = R.drawable.sports_car_green;

        Bitmap iconBitmap = BitmapFactory.decodeResource(this.getResources(),
                icon);
        // This is the scrolling text of the notification
        CharSequence text = "Hey!!! Your car parking has been booked.";

        // What time to show on the notification
        long time = System.currentTimeMillis();

        // The PendingIntent to launch our activity if the user selects this notification
        Intent intent = new Intent(this, CarParkingLocationListActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setLargeIcon(iconBitmap)
                .setColor(Color.argb(0, 45, 135, 198))
                .setContentTitle(title)
                .setContentText(text)
                .setTicker(tickerText)
                .setShowWhen(true)
                .setWhen(time)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();

        // Send the notification to the system.
        mNM.notify(NOTIFICATION, notification);
    }

    private void setDataToRecyclerView() {
        List<AbstractItem> items = new ArrayList<>();

        CarParkingModel carParkingModel = new CarParkingModel();

        for ( int i=0; i<30; i++ ) {
            carParkingModel.setLabel(String.valueOf(i));
            if ( i%2 == 0 ) {
                items.add(new EdgeItem(carParkingModel));
            } else {
                items.add(new CenterItem(carParkingModel));
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
    public void onParkingSelected(CarParkingModel carParkingModel) {
        Resources res = getResources();
        String text = res.getQuantityString(R.plurals.price_label, carParkingModel.getHour(),
                carParkingModel.getPrice(), carParkingModel.getHour());
        mBtnSeatSelected.setVisibility(View.VISIBLE);
        mBtnSeatSelected.setText(text);
        parkingReservationTime = carParkingModel.getHour();
        mCarParkingModel = carParkingModel;
    }
}
