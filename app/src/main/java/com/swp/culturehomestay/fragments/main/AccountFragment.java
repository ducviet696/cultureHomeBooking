package com.swp.culturehomestay.fragments.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.activity.CustomerProfileActivity;
import com.swp.culturehomestay.activity.LoginActivity;
import com.swp.culturehomestay.models.UserDetail;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {


    public AccountFragment() {
        // Required empty public constructor
    }

    AccessToken accessToken;
    View viewNoLoginAccount;
    View viewLoginAccount;
    View view;
    Button loginBtn;
    Button logoutBtn;
    UserDetail userDetail;
    TextView userName;
    RelativeLayout btnCusProfile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        view = inflater.inflate(R.layout.fragment_account, container, false);
        viewNoLoginAccount = (LinearLayout)view.findViewById(R.id.noLogin_Account);
        viewLoginAccount = (LinearLayout) view.findViewById(R.id.login_Account);
        if(isLoggedIn){
            viewNoLoginAccount.setVisibility(View.GONE);
            userDetail = new UserDetail("anhndv","Viet Anh","Nguyen Dung","anhndvse04243@gmail.com", new Date(),true,"","+84333834191","","Hanoi, Vietnam" );
            userName = (TextView) view.findViewById(R.id.lbl_userName);
            userName.setText(userDetail.getFirstName()+" "+userDetail.getLastName());
            viewLoginAccount.setVisibility(View.VISIBLE);
        }else{
            viewNoLoginAccount.setVisibility(View.VISIBLE);
            viewLoginAccount.setVisibility(View.GONE);
        }
        loginBtn = (Button) viewNoLoginAccount.findViewById(R.id.btn_signin_create);
        loginBtn.setOnClickListener(onSignInClick);
        btnCusProfile = (RelativeLayout) view.findViewById(R.id.btn_cusProfile);
        btnCusProfile.setOnClickListener(onCustomerProfileClick);
//        logoutBtn = (Button) view.findViewById(R.id.btn_logout);
//        logoutBtn.setOnClickListener(onLogoutClick);
        return view;
    }
    View.OnClickListener onCustomerProfileClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), CustomerProfileActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener onSignInClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.putExtra("prePos",3);
            startActivity(intent);
        }
    };
    View.OnClickListener onLogoutClick = new View.OnClickListener() {
        public void onClick(View v) {
            disconnectFromFacebook();
        }
    };

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.pager);
        pager.setCurrentItem(0);
    }
}