package com.swp.culturehomestay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.Amenity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AmenityFilterAdapter extends RecyclerView.Adapter<AmenityFilterAdapter.MyViewHolder> {
    List<Amenity> amenityList;
    Context context;
    ArrayList<Integer> amenityIdList = new ArrayList<>();
    CheckboxCheckedListener checkboxCheckedListener;

    public AmenityFilterAdapter(List<Amenity> amenityList,ArrayList<Integer> amenityIdList, Context context) {
        this.amenityList = amenityList;
        this.amenityIdList = amenityIdList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filter_list,viewGroup,false);
        return new MyViewHolder(view,checkboxCheckedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        final MyViewHolder holder = myViewHolder;
        Amenity amenity = amenityList.get(i);
        myViewHolder.cbAmenity.setText(amenity.getEnglishName());
        if(amenityIdList!= null && amenityIdList.contains(Integer.valueOf(amenity.getAmenityId()))){
            myViewHolder.cbAmenity.setChecked(true);
        }
        myViewHolder.cbAmenity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxCheckedListener!=null){
                    checkboxCheckedListener.onCheckboxClick(i);
                } else {
                    Log.d("cultureIdList", "onClick: null" );
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return amenityList!=null?amenityList.size():0;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.cbList)
        CheckBox cbAmenity;
        CheckboxCheckedListener checkboxCheckedListener;

        public MyViewHolder(View view, CheckboxCheckedListener checkboxCheckedListener) {
            super(view);
            ButterKnife.bind(this,view);
            this.checkboxCheckedListener = checkboxCheckedListener;
        }

        @Override
        public void onClick(View v) {
            checkboxCheckedListener.onCheckboxClick(getAdapterPosition());
        }
    }
    public interface CheckboxCheckedListener{
        void onCheckboxClick(int position);
    }

    public void setCheckboxCheckedListener(CheckboxCheckedListener checkboxCheckedListener) {
        this.checkboxCheckedListener = checkboxCheckedListener;
    }
}