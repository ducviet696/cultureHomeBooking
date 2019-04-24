package com.swp.culturehomestay.utils;

import com.swp.culturehomestay.models.Wishlist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Constants {
    public static final String BASE_URLIMG = "http://222.252.30.110:8765/homestay/files/homestay/image?path=";
    public static final String USER_ID ="624daf76-c874-4ff3-a6ad-caf1bacce6e6";
    public static final String BASE_URL = "http://222.252.30.110:8765/";
    public static final String HOMESTAY_ID = "HOMESTAY_ID";
    public static final String ACTIVITY_NAME = "ACTIVITY_NAME";
    public static final String CULTURE_ID_LIST = "cultureIdList";
    public static final String BOOKINGHOMEDETAILACTIVITY = "BookingHomeDetailActivity";
    public static final String HOME_FRAGMENT = "HomeFragment";
    public static final String CANCEL_TYPE = "CANCEL_TYPE";
    public static final String CANCEL_FLEX = "flex";
    public static final String CANCEL_MOD = "mod";
    public static final String BOOKING_RES = "res";
    public static final String BOOKING_INS = "ins";
    public static final String HOMETYPE_SAP = "sap";
    public static final String HOMETYPE_VLA = "vla";
    public static final String HOMETYPE_ENT = "hou";
    public static final String HOMETYPE_AP = "ap";
    public static final int REQUEST_CODE = 9696;
    public static final int REQUEST_CODE_HOME_FRAGMENT = 1102;
    public static final String BUNDLE = "BUNDLE";
    public static final int RESULT_CODE_CHANGE_GUEST = 22 ;
    public static final String LIST_DATE_BOOKING = "LIST_DATE_BOOKING";
    public static List<Date> dateList = new ArrayList<>();
    public static List<Wishlist> wishlists  = new ArrayList<>();
    public static ArrayList<Integer> cultureIdList = new ArrayList<>();
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String LANG = "en";
    public static final String CODE_OK = "00";
    public static final String CODE_FAIL = "06";
}
