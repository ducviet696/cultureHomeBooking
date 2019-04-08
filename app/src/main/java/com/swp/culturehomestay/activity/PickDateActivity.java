package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.timessquare.CalendarPickerView;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.utils.Constants;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PickDateActivity extends AppCompatActivity {

    @BindView(R.id.calendar)
    CalendarPickerView datePicker;
    @BindView(R.id.tvCheckin)
    TextView txtCheckin;
    @BindView(R.id.tvCheckout)
    TextView txtCheckout;
    @BindView(R.id.tvDaySelected)
    TextView txtMess;
    String dateCheckin, dateCheckout;
    @BindView(R.id.tvBack)
    TextView txtBack;
//    List<Date> dateList = new ArrayList<>();
    String homestaysID;
    String previousActtivity;
    @BindView(R.id.btnSave)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Bundle");
        previousActtivity = bundle.getString(Constants.ACTIVITY_NAME);
//        Constants.dateList = (List<Date>) bundle.getSerializable("listDate");
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
        .withHighlightedDates(Constants.dateList);
//                .withSelectedDates(Constants.dateList);
        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                String selectedDate = DateFormat.getDateInstance().format(date);
                int selectedDayNum = datePicker.getSelectedDates().size();
                if (selectedDayNum == 1) {
                    txtCheckin.setText(selectedDate);
                    txtMess.setText(R.string.check_out_mess);
                    txtCheckin.setTextColor(getResources().getColor(R.color.colorBlack));
                    txtCheckout.setTextColor(getResources().getColor(R.color.colorPrimaryButtonActive));
                    txtCheckout.setText(getResources().getText(R.string.check_out_date));

                } else {
                    txtCheckout.setText(selectedDate);
                    txtMess.setText(String.valueOf(selectedDayNum + "day(s) selected"));
                }

                btnSave.setEnabled(true);
                btnSave.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButtonActive));
                Constants.dateList = datePicker.getSelectedDates();

            }

            @Override
            public void onDateUnselected(Date date) {
                txtCheckin.setTextColor(getResources().getColor(R.color.colorBlack));
                txtCheckout.setTextColor(getResources().getColor(R.color.colorPrimaryButtonActive));
                txtCheckout.setText(getResources().getText(R.string.check_out_date));
                txtCheckin.setText(getResources().getText(R.string.check_in_date));

            }
        });
    }

    @OnClick(R.id.btnSave)
    public void btnSaveClicked() {
        Log.d("btnSaveClicked", "btnSaveClicked: "+previousActtivity);
        if (previousActtivity.equals(Constants.BOOKINGHOMEDETAILACTIVITY)) {
            Intent intent = new Intent();
//            intent.putExtra("listDate1", (Serializable) dateList);
            setResult(RESULT_OK,intent);
            finish();
        }

    }

    @OnClick(R.id.tvBack)
    public void backClick() {
        finish();
    }
}
