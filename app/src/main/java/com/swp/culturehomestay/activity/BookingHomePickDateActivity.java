package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.timessquare.CalendarPickerView;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.DateBooked;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingHomePickDateActivity extends AppCompatActivity {

    private static final String TAG = BookingHomePickDateActivity.class.getSimpleName();
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
    String homestaysID;
    List<Date> listDateBooking = new ArrayList<>();
    List<Date> listDateDisable = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        homestaysID = intent.getStringExtra(Constants.HOMESTAY_ID);
        listDateDisable = (List<Date>) intent.getSerializableExtra(Constants.LIST_DATE_DISABLE);
//        getDateBooked(homestaysID);
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        datePicker.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                return isSelectable(date);
            }
        });
        datePicker.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);

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

                btnNext.setEnabled(true);
                btnNext.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButtonActive));
                listDateBooking = datePicker.getSelectedDates();

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

    @OnClick(R.id.btnNext)
    public void nextStep() {
        Intent intent = new Intent(BookingHomePickDateActivity.this, BookingHomeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.LIST_DATE_BOOKING, (Serializable) listDateBooking);
        bundle.putSerializable(Constants.LIST_DATE_DISABLE, (Serializable) listDateDisable);
        bundle.putString(Constants.HOMESTAY_ID, homestaysID);
        intent.putExtra("Bundle", bundle);
        startActivity(intent);
    }

    @OnClick(R.id.tvBack)
    public void backClick() {
        finish();
    }

    public void getDateBooked(String homestaysID) {
        Call<DateBooked> call = Utils.getAPI().getDateBooked(homestaysID, 1);
        call.enqueue(new Callback<DateBooked>() {
            @Override
            public void onResponse(Call<DateBooked> call, Response<DateBooked> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode().equals(Constants.CODE_OK)) {
                        List<Integer> listDateBooked = response.body().getListDateBooked();

                        for (int dayOfYear : listDateBooked) {
                            try {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
                                listDateDisable.add(new SimpleDateFormat("dd/MM/yyyy").parse(Utils.formatDate(calendar.getTime())));
                                Log.d("date1", "calendar: " + Utils.formatDate(calendar.getTime()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
//                        try {
//                            listDateDisable.add(new SimpleDateFormat("dd/MM/yyyy").parse("2/05/2019"));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
                    } else {
                        Log.d("date1", "onResponse: " + response.body().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<DateBooked> call, Throwable t) {
                Log.d("date1", "onFailure: " + t.getMessage());
            }
        });
    }

    private boolean isSelectable(Date date) {
        if (listDateDisable.contains(date)) {
            return false;
        }
        return true;
    }

}
