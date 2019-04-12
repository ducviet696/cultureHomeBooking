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

public class ChangeNumberOfGuestActivity extends AppCompatActivity {

    @BindView(R.id.tvGuestNum)
    TextView txtGuestNum;
    @BindView(R.id.btnMinus)
    ImageView btnMinus;
    @BindView(R.id.btnAdd)
    ImageView btnAdd;
    int minGuest, maxGuest;
    int guest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_number_of_guest);
        ButterKnife.bind(this);
        getData();
    }
    public void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        minGuest = bundle.getInt("Min");
        maxGuest = bundle.getInt("Max");
        guest = bundle.getInt("Guest");
        txtGuestNum.setText(String.valueOf(guest));
        setVisiableButton();
    }
    public void setVisiableButton(){
        if(guest == minGuest){
            btnMinus.setEnabled(false);
            btnAdd.setEnabled(true);
        } else if(guest == maxGuest) {
            btnAdd.setEnabled(false);
            btnMinus.setEnabled(true);
        }
    }
    @OnClick({R.id.btnAdd,R.id.btnMinus,R.id.btnSave})
    public void onClickView(View view)
    {
        switch (view.getId())  {
            case R.id.btnAdd:
                guest++;
                setVisiableButton();
                txtGuestNum.setText(String.valueOf(guest));
            break;
            case R.id.btnMinus:
                guest--;
                setVisiableButton();
                txtGuestNum.setText(String.valueOf(guest));
                break;
            case R.id.btnSave:
                Intent intent = new Intent();
                intent.putExtra("totalGuest",guest);
                setResult(Constants.RESULT_CODE_CHANGE_GUEST,intent);
                finish();
                break;
        }
    }

    @OnClick(R.id.tvClose)
    public void closeClick() {
        finish();
    }
}
