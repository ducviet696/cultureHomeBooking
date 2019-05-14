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
    List<Date> listDateBooking = new ArrayList<>();
    List<Date> listDateBookingPrevious = new ArrayList<>();
    List<Date> listDateDisable = new ArrayList<>();
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
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        previousActtivity = bundle.getString(Constants.ACTIVITY_NAME);
        listDateBookingPrevious = (List<Date>) bundle.getSerializable(Constants.LIST_DATE_BOOKING);
        if(previousActtivity.equals(Constants.BOOKINGHOMEDETAILACTIVITY)){
            listDateDisable = (List<Date>) bundle.getSerializable(Constants.LIST_DATE_DISABLE);
        }
        if(previousActtivity.equals(Constants.ADVANCE_SEARCH_ACTIVITY) ||previousActtivity.equals(Constants.HOME_FRAGMENT)){
            btnSave.setEnabled(true);
            btnSave.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButtonActive));
        }

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        datePicker.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                try {
                    return isSelectable(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
        .withHighlightedDates(listDateBookingPrevious);
//                .withSelectedDates(listDateBookingPrevious);
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

    private boolean isSelectable(Date date) throws ParseException {
        for (Date date1 : listDateDisable){
            if(date1.equals(date)){
                return  false;
            }
        }
        return true;
    }

    @OnClick(R.id.btnSave)
    public void btnSaveClicked() {
        Log.d("btnSaveClicked", "btnSaveClicked: "+previousActtivity);
        if (previousActtivity.equals(Constants.BOOKINGHOMEDETAILACTIVITY)) {
            onSaveClick();
        } else if (previousActtivity.equals(Constants.HOME_FRAGMENT)) {
            onSaveClick();
        } else if (previousActtivity.equals(Constants.ADVANCE_SEARCH_ACTIVITY)) {
            onSaveClick();
        }

    }

    private void onSaveClick() {
        Intent intent = new Intent();
        intent.putExtra(Constants.LIST_DATE_BOOKING, (Serializable) listDateBooking);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.tvBack)
    public void backClick() {
        finish();
    }

}
