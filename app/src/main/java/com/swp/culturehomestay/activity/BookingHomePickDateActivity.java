package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.timessquare.CalendarPickerView;
import com.swp.culturehomestay.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BookingHomePickDateActivity extends AppCompatActivity {

    @BindView(R.id.calendar)
    CalendarPickerView datePicker;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.tvCheckin)
    TextView txtCheckin;
    @BindView(R.id.tvCheckout)
    TextView txtCheckout;
    @BindView(R.id.tvDaySelected)
    TextView txtMess;
    String dateCheckin, dateCheckout;
    @BindView(R.id.tvBack)
    TextView txtBack;
    List<Date> dateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home);
        ButterKnife.bind(this);
//        ActionBar actionBar = getActionBar();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);

        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);
//                .withSelected;
        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
                int selectedDayNum = datePicker.getSelectedDates().size();
//                Toast.makeText(BookingHomePickDateActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
                if(selectedDayNum==1) {
                    txtCheckin.setText(selectedDate);
                    txtMess.setText(R.string.check_out_mess);
                    txtCheckin.setTextColor(getResources().getColor(R.color.colorBlack));
                    txtCheckout.setTextColor(getResources().getColor(R.color.colorPrimaryButtonActive));
                    txtCheckout.setText("Check-out Date");

                } else {
                    txtCheckout.setText(selectedDate);
                    txtMess.setText(String.valueOf(selectedDayNum + "day(s) selected"));
                }

                btnNext.setEnabled(true);
                btnNext.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButtonActive));
                dateList = datePicker.getSelectedDates();
            }

            @Override
            public void onDateUnselected(Date date) {
                txtCheckin.setTextColor(getResources().getColor(R.color.colorBlack));
                txtCheckout.setTextColor(getResources().getColor(R.color.colorPrimaryButtonActive));
                txtCheckout.setText("Check-out Date");
                txtCheckin.setText("Check-in Date");

            }
        });
    }
    @OnClick(R.id.btnNext)
    public void nextStep() {
        Intent intent = new Intent(BookingHomePickDateActivity.this, BookingHomeDetailActivity.class);
        startActivity(intent);

    }
    @OnClick(R.id.tvBack)
    public void backClick()
    {
        finish();
    }
}
