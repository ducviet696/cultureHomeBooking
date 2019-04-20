package com.swp.culturehomestay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.HomestayCulture;
import com.swp.culturehomestay.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CultureAdapter extends RecyclerView.Adapter<CultureAdapter.MyViewHolder> {
    List<HomestayCulture> homestayCultureList;
    Context context;
    int res;

    public CultureAdapter(List<HomestayCulture> homestayCultureList, Context context, int res) {
        this.homestayCultureList = homestayCultureList;
        this.context = context;
        this.res = res;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_culture,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        final MyViewHolder holder = myViewHolder;
        HomestayCulture homestayCulture = homestayCultureList.get(i);
        myViewHolder.tvDesCul.setText(homestayCulture.getDiscription());
        myViewHolder.tvNameCul.setText(homestayCulture.getCultureService().getEnglisgName());
        myViewHolder.tvPriceCul.setText(Utils.formatPrice(homestayCulture.getPrice()));

    }

    @Override
    public int getItemCount() {
        return homestayCultureList!=null?homestayCultureList.size():0;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNameCul)
        TextView tvNameCul;
        @BindView(R.id.tvPriceCul)
        TextView tvPriceCul;
        @BindView(R.id.tvDesCul)
        TextView tvDesCul;

        public MyViewHolder(View view) {
           super(view);
            ButterKnife.bind(this,view);
        }
    }
}
