package com.swp.culturehomestay.fragments.main;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.activity.TestSendDataFromHomeFragment;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    TextView txtCheckInDate, txtCheckOutDate, iconSearch, textAmount, iconAmount;
    EditText txtSearch;
    Button btnSearch;

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
        iconAmount = (TextView) view.findViewById(R.id.iconAmount);
        textAmount = (TextView) view.findViewById(R.id.textAmount);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(searchBtnClick);
        iconAmount.setOnClickListener(amountClick);
        textAmount.setOnClickListener(amountClick);
        iconSearch.setOnClickListener(searchClick);
        txtSearch.setOnClickListener(searchClick);
        txtCheckInDate.setOnClickListener(txtCheckInDateOnClick);
        txtCheckOutDate.setOnClickListener(txtCheckOutDateOnClick);
        return view;
    }

    View.OnClickListener searchClick = new View.OnClickListener() {
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

    View.OnClickListener amountClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.home_search_amount, null);
            final TextView txtAdd, txtRemove, txtAmountDetail;

            txtAdd = (TextView) mView.findViewById(R.id.add);
            txtRemove = (TextView) mView.findViewById(R.id.remove);
            txtAmountDetail = (TextView) mView.findViewById(R.id.amount);

            // Acction of add click
            txtAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strAmout = txtAmountDetail.getText().toString();
                    txtAmountDetail.setText(Integer.parseInt(strAmout) + 1 + "");
                }
            });

            // Acction of remove click
            txtRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strAmout = txtAmountDetail.getText().toString();
                    if (Integer.parseInt(strAmout) > 0) {
                        txtAmountDetail.setText(Integer.parseInt(strAmout) - 1 + "");
                    } else {

                    }
                }
            });

            mBuilder.setView(mView).setNegativeButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    textAmount.setText(txtAmountDetail.getText() + " people booking");
                }
            });
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        }
    };

    View.OnClickListener searchBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openTestSendDataFromHomeFragmentActivity();
        }
    };

    public void openTestSendDataFromHomeFragmentActivity() {
        

        Intent intent = new Intent(getActivity(), TestSendDataFromHomeFragment.class);
        startActivity(intent);
    }
}

