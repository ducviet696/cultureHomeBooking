package com.swp.culturehomestay.fragments.main;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.swp.culturehomestay.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    TextView txtCheckInDate, txtCheckOutDate, iconSearch;
    EditText txtSearch;

    Calendar c;
    DatePickerDialog dpd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        txtCheckInDate = (TextView) view.findViewById(R.id.txtCheckInDate);
        txtCheckOutDate = (TextView) view.findViewById(R.id.txtCheckOutDate);
        iconSearch = (TextView) view.findViewById(R.id.iconSearch);
        txtSearch = (EditText) view.findViewById(R.id.TextSearch);

        iconSearch.setOnClickListener(SearchClick);
        txtSearch.setOnClickListener(SearchClick);
        txtCheckInDate.setOnClickListener(txtCheckInDateOnClick);
        txtCheckOutDate.setOnClickListener(txtCheckOutDateOnClick);
        return view;
    }

    View.OnClickListener SearchClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener txtCheckInDateOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker Datepicker, int mYear, int mMonth, int mDayOfMonth) {
                    txtCheckInDate.setText(mDayOfMonth + "/" + mMonth + "/" + mYear);
                }
            }, day, month, year);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.show();
        }
    };

    View.OnClickListener txtCheckOutDateOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int mounth = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker Datepicker, int mYear, int mMonth, int mDayOfMonth) {
                    txtCheckOutDate.setText(mDayOfMonth + "/" + mMonth + "/" + mYear);
                }
            }, day, mounth, year);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.show();
        }
    };
}

