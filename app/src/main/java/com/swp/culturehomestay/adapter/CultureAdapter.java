package com.swp.culturehomestay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.HomestayCulture;
import com.swp.culturehomestay.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CultureAdapter extends RecyclerView.Adapter<CultureAdapter.MyViewHolder> {
    List<HomestayCulture> homestayCultureList;
    Context context;
    CheckboxCheckedListener checkboxCheckedListener;
    ArrayList<Integer> cultureIdList = new ArrayList<>();

    public CultureAdapter(List<HomestayCulture> homestayCultureList, Context context, ArrayList<Integer> cultureIdList) {
        this.homestayCultureList = homestayCultureList;
        this.context = context;
        this.cultureIdList = cultureIdList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_culture,viewGroup,false);
        return new MyViewHolder(view,checkboxCheckedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        final MyViewHolder holder = myViewHolder;
        HomestayCulture homestayCulture = homestayCultureList.get(i);
        myViewHolder.tvDesCul.setText(homestayCulture.getDiscription());
        myViewHolder.tvNameCul.setText(homestayCulture.getCultureService().getEnglisgName());
        if(cultureIdList!= null && cultureIdList.contains(Integer.valueOf(homestayCultureList.get(i).getCultureServiceId()))){
            myViewHolder.cbCulture.setChecked(true);
        }
        myViewHolder.tvPriceCul.setText(Utils.formatPrice(homestayCulture.getPrice()));
        myViewHolder.cbCulture.setOnClickListener(new View.OnClickListener() {
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
        return homestayCultureList!=null?homestayCultureList.size():0;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tvNameCul)
        TextView tvNameCul;
        @BindView(R.id.tvPriceCul)
        TextView tvPriceCul;
        @BindView(R.id.tvDesCul)
        TextView tvDesCul;
        @BindView(R.id.cbCulture)
        CheckBox cbCulture;
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
