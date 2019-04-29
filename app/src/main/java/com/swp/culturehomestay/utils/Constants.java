package com.swp.culturehomestay.utils;

import com.swp.culturehomestay.models.Wishlist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Constants {
    public static final String BASE_URLIMG = "http://222.252.30.110:8765/homestay/files/homestay/image?path=";
    public static final String USER_ID ="624daf76-c874-4ff3-a6ad-caf1bacce6e6";
    public static final String BASE_URL = "http://222.252.30.110:8765/";
//    public static final String BASE_URL = "http://192.168.1.190:8765/";
    public static final String HOMESTAY_ID = "HOMESTAY_ID";
    public static final String HOST_ID = "HOST_ID";
    public static final String ACTIVITY_NAME = "ACTIVITY_NAME";
    public static final String CULTURE_ID_LIST = "cultureIdList";
    public static final String BOOKINGHOMEDETAILACTIVITY = "BookingHomeDetailActivity";
    public static final String HOME_FRAGMENT = "HomeFragment";
    public static final String ADVANCE_SEARCH_ACTIVITY = "AdvanceSearchActivity";
    public static final String CANCEL_TYPE = "CANCEL_TYPE";
    public static final String CANCEL_FLEX = "flex";
    public static final String CANCEL_MOD = "mod";
    public static final String BOOKING_RES = "res";
    public static final String BOOKING_INS = "ins";
    public static final String HOMETYPE_SAP = "sap";
    public static final String HOMETYPE_VLA = "vla";
    public static final String HOMETYPE_ENT = "hou";
    public static final String HOMETYPE_AP = "ap";
    public static final String ROOMTYPE_ENTIRE = "ent";
    public static final String ROOMTYPE_SINGLE= "singe";
    public static final int REQUEST_CODE = 9696;
    public static final int REQUEST_CODE_HOME_FRAGMENT = 1102;
    public static final int REQUEST_CODE_ADVANCE_SEARCH = 1101;
    public static final int REQUEST_CODE_BOOKING = 1100;
    public static final String BUNDLE = "BUNDLE";
    public static final int RESULT_CODE_CHANGE_GUEST = 22 ;
    public static final int RESULT_CODE_CHANGE_ROOM = 11 ;
    public static final int RESULT_CODE_CHANGE_DATE = 33 ;
    public static final int RESULT_CODE_CHANGE_FILTER = 44 ;
    public static final String LIST_DATE_BOOKING = "LIST_DATE_BOOKING";
    public static final String LIST_DATE_DISABLE = "LIST_DATE_DISABLE";
    public static final String LIST_AMENITY_SELECTED = "LIST_AMENITY_SELECTED";
    public static final String LIST_CULTURE_SELECTED = "LIST_CULTURE_SELECTED";
    public static final String LIST_HOME_TYPE = "LIST_HOME_TYPE";
    public static final String LIST_ROOM_TYPE = "LIST_ROOM_TYPE";
    public static List<Date> dateList = new ArrayList<>();
    public static List<Wishlist> wishlists  = new ArrayList<>();
    public static ArrayList<Integer> cultureIdList = new ArrayList<>();
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String LANG = "en";
    public static final String GUEST = "guest";
    public static final String ROOM = "room";
    public static final String CODE_OK = "00";
    public static final String CODE_FAIL = "06";
    public static final int MIN_PRICE = 10;
    public static final int MAX_PRICE = 2000;
}
