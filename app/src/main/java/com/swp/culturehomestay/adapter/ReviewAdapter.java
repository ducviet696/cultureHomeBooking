package com.swp.culturehomestay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.FeedBack;
import com.swp.culturehomestay.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder>  {
    List<FeedBack> feedBackList;
    Context context;

    public ReviewAdapter(List<FeedBack> feedBackList, Context context) {
        this.feedBackList = feedBackList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review,viewGroup,false);
        return new ReviewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder myViewHolder, int i) {
//        final MyViewHolder holder = myViewHolder;
        FeedBack feedBack = feedBackList.get(i);
        myViewHolder.tvDateFeedback.setText(Utils.formatDate(Utils.convertLongToDate(feedBack.getCreatedDate())));
        myViewHolder.tvUserEmailFb.setText(feedBack.getUserEmail());
        myViewHolder.tvStar.setText(String.valueOf(feedBack.getStart()));
        myViewHolder.tvDesReview.setText(feedBack.getComment());

    }

    @Override
    public int getItemCount() {
        return feedBackList!=null?feedBackList.size():0;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserEmailFb)
        TextView tvUserEmailFb;
        @BindView(R.id.tvStar)
        TextView tvStar;
        @BindView(R.id.tvDateFeedback)
        TextView tvDateFeedback;
        @BindView(R.id.tvDesReview)
        TextView tvDesReview;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
