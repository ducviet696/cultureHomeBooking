package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class ChangeNumberOfRoomActivity extends AppCompatActivity {
    @BindView(R.id.tvroomNum)
    TextView tvroomNum;
    @BindView(R.id.btnMinus)
    ImageView btnMinus;
    @BindView(R.id.btnAdd)
    ImageView btnAdd;
    int minRoom,maxRoom;
    int room;
    String previousActtivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_number_of_room);
        ButterKnife.bind(this);
        getData();
    }

    public void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        minRoom = bundle.getInt("Min");
        maxRoom = bundle.getInt("Max");
        room = bundle.getInt("Room");
        tvroomNum.setText(String.valueOf(room));
        previousActtivity = bundle.getString(Constants.ACTIVITY_NAME);
        setVisiableButton();
    }

    public void setVisiableButton() {
        if (room == minRoom) {
            btnMinus.setEnabled(false);
        } else if (room == maxRoom) {
            btnAdd.setEnabled(false);

        } else {
            btnMinus.setEnabled(true);
            btnAdd.setEnabled(true);
        }
    }

    @OnClick({R.id.btnAdd, R.id.btnMinus, R.id.btnSave})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                room++;
                tvroomNum.setText(String.valueOf(room));
                setVisiableButton();
                break;
            case R.id.btnMinus:
                room--;
                tvroomNum.setText(String.valueOf(room));
                setVisiableButton();
                break;
            case R.id.btnSave:
                if (previousActtivity.equals(Constants.BOOKINGHOMEDETAILACTIVITY)) {
                    Intent intent = new Intent();
                    intent.putExtra("totalGuest", room);
                    setResult(Constants.RESULT_CODE_CHANGE_ROOM, intent);
                    finish();
                    break;
                } else if (previousActtivity.equals(Constants.ADVANCE_SEARCH_ACTIVITY)) {
                    Intent intent = new Intent();
                    intent.putExtra("totalRoom", room);
                    setResult(Constants.RESULT_CODE_CHANGE_ROOM, intent);
                    finish();
                    break;
                }
        }
    }

    @OnClick(R.id.tvClose)
    public void closeClick() {
        finish();
    }
}
