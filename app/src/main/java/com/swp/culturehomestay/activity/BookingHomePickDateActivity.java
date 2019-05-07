package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.DateBooked;
import com.swp.culturehomestay.models.PriceGet;
import com.swp.culturehomestay.models.PricePost;
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
    //Room
    @BindView(R.id.tvroomNum)
    TextView tvroomNum;
    @BindView(R.id.btnMinus)
    ImageView btnMinus;
    @BindView(R.id.btnAdd)
    ImageView btnAdd;
    int minRoom = 1;
    int maxRoom;
    int room = 1;
    @BindView(R.id.layout_pickRoom)
    LinearLayout layout_pickRoom;

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
    @BindView(R.id.tvBack)
    TextView txtBack;
    String homestaysID, roomType;
    ArrayList<Integer> cultureIdList = new ArrayList<>();
    List<Date> listDateBooking = new ArrayList<>();
    List<Date> listDateDisable = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home);
        ButterKnife.bind(this);
        getData();
        getDateBooked(homestaysID, room);
        showDatePicker();
    }


    private void showDatePicker() {
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        datePicker.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                return !listDateDisable.contains(date);
            }

        });
        datePicker.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);
        Log.d("getDateBooked", "showDatePicker: "+listDateDisable);
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
                resetDatePicker();
            }
        });
    }

    private void resetDatePicker() {
        btnNext.setEnabled(false);
        btnNext.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButtonInactive));
        txtCheckin.setTextColor(getResources().getColor(R.color.colorBlack));
        txtCheckout.setTextColor(getResources().getColor(R.color.colorPrimaryButtonActive));
        txtCheckout.setText(getResources().getText(R.string.check_out_date));
        txtCheckin.setText(getResources().getText(R.string.check_in_date));
    }

    @OnClick(R.id.btnNext)
    public void nextStep() {
        onNextClick();
    }

    private void onNextClick() {
        Intent intent = new Intent(BookingHomePickDateActivity.this, BookingHomeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.LIST_DATE_BOOKING, (Serializable) listDateBooking);
        bundle.putSerializable(Constants.LIST_DATE_DISABLE, (Serializable) listDateDisable);
        PricePost pricePost = new PricePost(cultureIdList,homestaysID,room,1,listDateBooking.get(0).getTime(),listDateBooking.get(listDateBooking.size()-1).getTime());
        Log.d(TAG, "onNextClick: "+pricePost);
        Call<PriceGet> call = Utils.getAPI().getPrice(pricePost,Constants.LANG);
        call.enqueue(new Callback<PriceGet>() {
            @Override
            public void onResponse(Call<PriceGet> call, Response<PriceGet> response) {
                if(response.isSuccessful() && response.body()!= null){
                    if(response.body().getCode().equals(Constants.CODE_OK)){
                        bundle.putIntegerArrayList(Constants.LIST_CULTURE_SELECTED,cultureIdList);
                        bundle.putString(Constants.HOMESTAY_ID, homestaysID);
                        bundle.putInt(Constants.ROOM, room);
                        intent.putExtra(Constants.BUNDLE, bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookingHomePickDateActivity.this, "Date booking isn't available. Please choose other date or other homestay!", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<PriceGet> call, Throwable t) {
                Toast.makeText(BookingHomePickDateActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    @OnClick(R.id.tvBack)
    public void backClick() {
        finish();
    }

    public void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        roomType = bundle.getString("roomType");
        cultureIdList = bundle.getIntegerArrayList(Constants.LIST_CULTURE_SELECTED);
        homestaysID = bundle.getString(Constants.HOMESTAY_ID);;
        maxRoom = bundle.getInt("Max");
        if(roomType.equals(Constants.ROOMTYPE_ENTIRE)){
            layout_pickRoom.setVisibility(View.GONE);
        }
        tvroomNum.setText(String.valueOf(room));
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

    @OnClick({R.id.btnAdd, R.id.btnMinus})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                getDateBooked(homestaysID,++room);
                tvroomNum.setText(String.valueOf(room));
                setVisiableButton();
                resetDatePicker();
                break;
            case R.id.btnMinus:
                getDateBooked(homestaysID, --room);
                tvroomNum.setText(String.valueOf(room));
                setVisiableButton();
                resetDatePicker();
                break;
        }
    }
    public void getDateBooked(String homestaysID, int room) {
        Call<DateBooked> call = Utils.getAPI().getDateBooked(homestaysID, room);
        call.enqueue(new Callback<DateBooked>() {
            @Override
            public void onResponse(Call<DateBooked> call, Response<DateBooked> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode().equals(Constants.CODE_OK)) {
                        listDateDisable.clear();
                        List<Integer> listDateBooked = response.body().getListDateBooked();
                        Log.d("listDateDisable", "getDateBooked: "+listDateBooked+ "homestaysID: "+homestaysID
                        +", room: "+room);
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
                        showDatePicker();
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

}
