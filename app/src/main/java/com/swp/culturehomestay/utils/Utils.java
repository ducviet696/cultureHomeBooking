package com.swp.culturehomestay.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class Utils {

    public static ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#ffeead")),
                    new ColorDrawable(Color.parseColor("#93cfb3")),
                    new ColorDrawable(Color.parseColor("#fd7a7a")),
                    new ColorDrawable(Color.parseColor("#faca5f")),
                    new ColorDrawable(Color.parseColor("#1ba798")),
                    new ColorDrawable(Color.parseColor("#6aa9ae")),
                    new ColorDrawable(Color.parseColor("#ffbf27")),
                    new ColorDrawable(Color.parseColor("#d93947"))
            };

    public static ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    public static String DateToTimeFormat(String oldstringDate){
        PrettyTime p = new PrettyTime(new Locale(getCountry()));
        String isTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.ENGLISH);
            Date date = sdf.parse(oldstringDate);
            isTime = p.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isTime;
    }

    public static String DateFormat(String oldstringDate){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }

        return newDate;
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }

    public static void checkStringNull(TextView tv, String s) {
        if(s==null || s.isEmpty()) {
            tv.setText("unknow");
        } else {
            tv.setText(s);
        }
    }

    public static  Date convertLongToDate(long l) {
        Date d = new Date(l);
        return d;
    }
    public static String formatDate(Date date) {
        DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        return DATE_FORMAT.format(date);
    }

    public static String formatDateShort(Date date) {
        DateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd");
        return DATE_FORMAT.format(date);
    }

    public static String formatDayOfWeek(Date date) {
        DateFormat DATE_FORMAT = new SimpleDateFormat("E");
        return DATE_FORMAT.format(date);
    }

    public static String formatDayOfWeekFull(Date date) {
        DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM\nEEEE");
        return DATE_FORMAT.format(date);
    }

    public static String formatPrice(Integer price) {
        NumberFormat PRICE_FORMAT = new DecimalFormat("$###,###.##");
        if(price==null){
            return PRICE_FORMAT.format(10);
        }
        return PRICE_FORMAT.format(price);
    }
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
    //load imgae via glide
    public static void loadImge(Context context, ImageView imageView, String imgeUrl){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loading);
//        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(R.drawable.image_not_found);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions.fitCenter();

        Glide.with(context)
                .load(imgeUrl)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public static void loadProfileImge(Context context, CircleImageView imageView, String imgeUrl){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loading);
//        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(R.drawable.image_not_found);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions.fitCenter();

        Glide.with(context)
                .load(imgeUrl)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    //get API
    public static IApi getAPI() {
        return ApiClient.getApiClient().create(IApi.class);
    }

    //Get UserId
    public static String getUserId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.MyPREFERENCES,Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId",null);
        Log.d("UserId", "UserId: " + userId);
        return userId ;
    }
    //CheckLogin
    public  static boolean checkLogin(Context context){
        if(isNullOrEmpty(getUserId(context))){
            return false;
        }else{
            return true;
        }
    }

    public static boolean emailIsValid (String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$\\_]{6,50}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
}
