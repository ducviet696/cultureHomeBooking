package com.swp.culturehomestay.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.HomestayImage;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowAlbumPhotoActivity extends AppCompatActivity {


    @BindView(R.id.gvPhotos)
    GridView gridView;
    @BindView(R.id.tvTotalPhoto)
    TextView txtTotalPhoto;
    ArrayList<String> listUrlImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_album_photo);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        listUrlImg =intent.getStringArrayListExtra("listUrlImg");
        txtTotalPhoto.setText(String.valueOf(listUrlImg.size())+" Photos");
        gridView.setAdapter(new ImageAdapter(this,listUrlImg));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(ShowAlbumPhotoActivity.this,DetailPhotoActivity.class);
                intent1.putExtra("position", position);
                intent1.putStringArrayListExtra("listUrlImg", listUrlImg);
                startActivity(intent1);
            }
        });

    }

    @OnClick(R.id.tvBackAb)
    public void onClickBack()
    {
        finish();
    }

    public class ImageAdapter extends BaseAdapter {

        private ArrayList<String> listUrlImg;
        private Context mContex;

        public ImageAdapter(Context mContex, ArrayList<String> listUrlImg ) {
            this.mContex = mContex;
            this.listUrlImg = listUrlImg;
        }

        @Override
        public int getCount() {
            return listUrlImg.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String urlImg = Constants.BASE_URLIMG+listUrlImg.get(position);
//            LayoutInflater layoutInflater = mContex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView imageView = new ImageView(mContex);
            imageView.setLayoutParams(new GridView.LayoutParams(200,200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,10,0,10);
            Utils.loadImge(mContex,imageView,urlImg);
            return imageView;
        }

    }
}
