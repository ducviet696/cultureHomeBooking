package com.swp.culturehomestay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.swp.culturehomestay.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchViaMapActivity extends AppCompatActivity {

    @BindView(R.id.edSearch)
    EditText edSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_via_map);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.tvBack)
    public void onBack(){
        finish();
    }
}
