package com.ndroidpro.carparkingsystem;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CarParkingActivity extends AppCompatActivity implements OnParkingSelected {

    public static final int COLUMNS = 3;
    private TextView txtSeatSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSeatSelected = (TextView)findViewById(R.id.txt_seat_selected);

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

        GridLayoutManager manager = new GridLayoutManager(this, COLUMNS);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lst_items);
        recyclerView.setLayoutManager(manager);

        CarParkingAdapter adapter = new CarParkingAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onParkingSelected(int price, int hour) {
        Resources res = getResources();
        String text = res.getQuantityString(R.plurals.price_label, hour, price, hour);
        txtSeatSelected.setVisibility(View.VISIBLE);
        txtSeatSelected.setText(text);
    }
}
