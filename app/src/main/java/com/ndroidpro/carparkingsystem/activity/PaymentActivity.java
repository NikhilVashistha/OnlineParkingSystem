package com.ndroidpro.carparkingsystem.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ndroidpro.carparkingsystem.Constants;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.model.CarParkingModel;
import com.ndroidpro.carparkingsystem.service.ScheduleClient;

import java.util.Calendar;
import java.util.Date;

public class PaymentActivity extends BaseActivity {

    // This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;

    private NotificationManager mNM;
    // Unique id to identify the notification.
    private static final int NOTIFICATION = 1234;
    private CarParkingModel mCarParkingModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        if(intent != null) {
            mCarParkingModel = intent.getParcelableExtra(Constants.INTENT_PAYMENT);
        }

        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(PaymentActivity.this);
        scheduleClient.doBindService();

        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel Car Parking",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNM.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.payment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_paid) {
            if(mCarParkingModel != null) {
                showCarParkingBookedNotification();

                scheduleCarParkingExpiringNotification();

                sendNotificationUsingFcm();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendNotificationUsingFcm() {
        mCarParkingModel.setToken(FirebaseInstanceId.getInstance().getToken());
        showProgressDialog();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase
                .child(Constants.DB_NOTIFICATION_REQUESTS)
                .push()
                .setValue(mCarParkingModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            ToastUtils.showLong("Request taken to book parking.");
                            ActivityUtils.finishOtherActivities(CarParkingLocationListActivity.class, true);
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
                    }
                });
    }

    @Override
    public void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }

    private void scheduleCarParkingExpiringNotification() {
        // Will give the current date time calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, mCarParkingModel.getHour());
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(calendar);
    }

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showCarParkingBookedNotification() {
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
}
