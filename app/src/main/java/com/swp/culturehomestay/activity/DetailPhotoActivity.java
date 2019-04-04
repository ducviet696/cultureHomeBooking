package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailPhotoActivity extends AppCompatActivity {

    @BindView(R.id.tvClose)
    TextView tvClose;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    GestureDetector gestureDetector;
    int SWIPE_THRESHOLD =100;
    int SWIPE_VELOCITY_THRESHOLD =100;
    ArrayList<String> listUrlImg;
    int position;
    String urlImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_photo);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        listUrlImg =intent.getStringArrayListExtra("listUrlImg");
        position = intent.getIntExtra("position",0);
        urlImg = Constants.BASE_URLIMG+listUrlImg.get(position);
        Utils.loadImge(this,ivPhoto,urlImg);
        gestureDetector = new GestureDetector(this, new MyGesture());
        ivPhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }
class MyGesture extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Left to right
        if(e2.getX() - e1.getX() >SWIPE_THRESHOLD && Math.abs(velocityX) >SWIPE_VELOCITY_THRESHOLD) {
            position--;
            if(position <0) {
                position =listUrlImg.size() -1;
            }
            urlImg = Constants.BASE_URLIMG+listUrlImg.get(position);
            Utils.loadImge(DetailPhotoActivity.this ,ivPhoto,urlImg);
        }

        // Right to left
        if(e1.getX() - e2.getX() >SWIPE_THRESHOLD && Math.abs(velocityX) >SWIPE_VELOCITY_THRESHOLD) {
            position++;
            if(position > listUrlImg.size() -1) {
                position = 0;
            }
            urlImg = Constants.BASE_URLIMG+listUrlImg.get(position);
            Utils.loadImge(DetailPhotoActivity.this ,ivPhoto,urlImg);
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
    @OnClick(R.id.tvClose)
    public void onClickBack()
    {
        finish();
    }
}
