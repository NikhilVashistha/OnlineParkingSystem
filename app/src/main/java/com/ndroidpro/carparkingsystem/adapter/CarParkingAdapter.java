package com.ndroidpro.carparkingsystem.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.listener.OnParkingSelected;

import java.util.ArrayList;
import java.util.List;

public class CarParkingAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {

    private OnParkingSelected mOnParkingSelected;

    private static class EdgeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgAvailableParking;
        private final ImageView imgParkingSelected;
        private final TextView tvCarParkingId;


        public EdgeViewHolder(View itemView) {
            super(itemView);
            imgAvailableParking = (ImageView) itemView.findViewById(R.id.img_seat);
            imgParkingSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);
            tvCarParkingId = (TextView) itemView.findViewById(R.id.tv_car_parking_id);
        }

    }

    private static class CenterViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgAvailableParking;
        private final ImageView imgParkingSelected;
        private final TextView tvCarParkingId;

        public CenterViewHolder(View itemView) {
            super(itemView);
            imgAvailableParking = (ImageView) itemView.findViewById(R.id.img_seat);
            imgParkingSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);
            tvCarParkingId = (TextView) itemView.findViewById(R.id.tv_car_parking_id);
        }

    }

    private static class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }

    }

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private List<AbstractItem> mItems;
    private ArrayList<Integer> colorList;

    public CarParkingAdapter(Context context, List<AbstractItem> items) {
        mOnParkingSelected = (OnParkingSelected) context;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
        colorList = new ArrayList<>();
        colorList.add(R.color.car_blue);
        colorList.add(R.color.car_red);
        colorList.add(R.color.car_yellow);
        colorList.add(R.color.car_green);
        colorList.add(R.color.car_pink);
        colorList.add(R.color.car_black);
        colorList.add(R.color.car_blue);
        colorList.add(R.color.car_red);
        colorList.add(R.color.car_yellow);
        colorList.add(R.color.car_green);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AbstractItem.TYPE_CENTER) {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_car_parking_right, parent, false);
            return new CenterViewHolder(itemView);
        } else if (viewType == AbstractItem.TYPE_EDGE) {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_car_parking, parent, false);
            return new EdgeViewHolder(itemView);
        } else {
            View itemView = new View(mContext);
            return new EmptyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        int type = mItems.get(position).getType();
        int showCarParkingId = position + 1;
        if (type == AbstractItem.TYPE_CENTER) {

            final CenterItem item = (CenterItem) mItems.get(position);
            final CenterViewHolder holder = (CenterViewHolder) viewHolder;

            holder.imgAvailableParking.setRotation(-90);
            holder.imgAvailableParking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPriceDialog(holder.imgAvailableParking.getContext(), position);
                }
            });

            holder.imgAvailableParking.setVisibility(isSelected(position) ? View.GONE : View.VISIBLE);

            holder.imgParkingSelected.setRotation(-90);
            holder.imgParkingSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.GONE);

            holder.imgParkingSelected.setColorFilter(
                    holder.imgParkingSelected.getContext().getResources()
                            .getColor(getColors(position, true)));

            holder.tvCarParkingId.setText(String.valueOf(showCarParkingId));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(dpTopx(10), dpTopx(-3), 0, 0);


            holder.tvCarParkingId.setLayoutParams(params);

        } else if (type == AbstractItem.TYPE_EDGE) {

            final EdgeItem item = (EdgeItem) mItems.get(position);
            final EdgeViewHolder holder = (EdgeViewHolder) viewHolder;

            holder.imgAvailableParking.setRotation(90);
            holder.imgAvailableParking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showPriceDialog(holder.imgAvailableParking.getContext(), position);
                }
            });

            holder.imgAvailableParking.setVisibility(isSelected(position) ? View.GONE : View.VISIBLE);

            holder.imgParkingSelected.setRotation(90);
            holder.imgParkingSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.GONE);

            holder.imgParkingSelected.setColorFilter(
                    holder.imgParkingSelected.getContext().getResources()
                            .getColor(getColors(position, false)));

            holder.tvCarParkingId.setText(String.valueOf(showCarParkingId));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(0, dpTopx(-3), dpTopx(10), 0);


            holder.tvCarParkingId.setLayoutParams(params);

        }
    }

    private int dpTopx(int dpValue) {
        Resources r = mContext.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                r.getDisplayMetrics());
    }

    private int getColors(int position, boolean rightSide){
        if(rightSide){
            position -= 2;
        }
        int colorIndex = position / 3;
        return colorList.get(colorIndex);
    }

    private void showPriceDialog(Context context, final int position) {

        final CharSequence[] items = { "1 hr Rs 10", "2 hrs Rs 15",
                "5 hrs Rs 30", "10 hrs Rs 40"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Parking Prices");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                int price = 10;
                int hour = 1;
                switch (item) {

                    case 0:
                        hour = 1;
                        price = 10;
                        break;

                    case 1:
                        hour = 2;
                        price = 15;
                        break;

                    case 2:
                        hour = 5;
                        price = 30;
                        break;

                    case 3:
                        hour = 10;
                        price = 40;
                        break;
                }

                toggleSelection(position);
                mOnParkingSelected.onParkingSelected(price, hour);
            }
        });
        builder.show();
    }

}
