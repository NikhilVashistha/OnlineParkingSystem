package com.ndroidpro.carparkingsystem.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.ndroidpro.carparkingsystem.Constants;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.adapter.AbstractItem;
import com.ndroidpro.carparkingsystem.adapter.CarParkingAdapter;
import com.ndroidpro.carparkingsystem.adapter.CenterItem;
import com.ndroidpro.carparkingsystem.adapter.EdgeItem;
import com.ndroidpro.carparkingsystem.listener.OnParkingSelected;
import com.ndroidpro.carparkingsystem.model.CarParkingLocationModel;
import com.ndroidpro.carparkingsystem.model.CarParkingModel;
import com.ndroidpro.carparkingsystem.model.Slots;

import java.util.ArrayList;
import java.util.List;

public class CarParkingActivity extends BaseActivity implements OnParkingSelected {

    public static final int COLUMNS = 2;
    private AppCompatButton mBtnSeatSelected;

    private RecyclerView recyclerView;
    private CarParkingAdapter adapter;

    private CarParkingModel mCarParkingModel;
    private CarParkingLocationModel mCarParkingLocationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_parking);

        Intent intent = getIntent();
        if(intent != null) {
            mCarParkingLocationModel = intent.getParcelableExtra(Constants.INTENT_CAR_PARKING_LOCATION_DATA);
        }

        mBtnSeatSelected = (AppCompatButton)findViewById(R.id.txt_seat_selected);

        GridLayoutManager manager = new GridLayoutManager(this, COLUMNS);
        recyclerView = (RecyclerView) findViewById(R.id.car_parking_list_recyclerview);
        recyclerView.setLayoutManager(manager);

        mBtnSeatSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCarParkingModel.setUserId(getUserId());
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.INTENT_PAYMENT, mCarParkingModel);
                ActivityUtils.startActivity(bundle, PaymentActivity.class);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        setDataToRecyclerView();
    }

    private void setDataToRecyclerView() {
        List<AbstractItem> items = new ArrayList<>();

        if( mCarParkingLocationModel != null  && ObjectUtils.isNotEmpty(mCarParkingLocationModel.getSlots())) {

            for (int i = 0; i < mCarParkingLocationModel.getSlots().size(); i++){
                Slots slot = mCarParkingLocationModel.getSlots().get(i);
                if ( i%2 == 0 ) {
                    items.add(new EdgeItem(slot));
                } else {
                    items.add(new CenterItem(slot));
                }
            }
        }

        adapter = new CarParkingAdapter(CarParkingActivity.this, items);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onParkingSelected(CarParkingModel carParkingModel) {
        Resources res = getResources();
        String text = res.getQuantityString(R.plurals.price_label, carParkingModel.getHour(),
                carParkingModel.getPrice(), carParkingModel.getHour());
        mBtnSeatSelected.setVisibility(View.VISIBLE);
        mBtnSeatSelected.setText(text);
        mCarParkingModel = carParkingModel;
    }
}
