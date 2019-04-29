package com.swp.culturehomestay.fragments.main;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.activity.CustomerProfileActivity;
import com.swp.culturehomestay.activity.LoginActivity;
import com.swp.culturehomestay.activity.SettingActivity;
import com.swp.culturehomestay.activity.SignUpActivity;
import com.swp.culturehomestay.models.AuthenticatioModel;
import com.swp.culturehomestay.models.PostFileBody;
import com.swp.culturehomestay.models.SignUpResModel;
import com.swp.culturehomestay.models.UserDetailModel;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.FileUtils;
import com.swp.culturehomestay.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {


    public final static int PICK_IMAGE_REQUEST = 1;
    private static final int READ_EXTERNAL_REQUEST = 2;

    public AccountFragment() {
        // Required empty public constructor
    }

    private static final String TAG = "Account";
    public static final int PICK_FROM_GALLERY = 1;
    AccessToken accessToken;
    View viewNoLoginAccount;
    View viewLoginAccount;
    View view;
    Button loginBtn;
    Button logoutBtn;
    Button signUpBtn;
    CircleImageView proImage;
    UserDetailModel userDetailModel;
    TextView userName;
    RelativeLayout btnCusProfile;
    RelativeLayout btnSetting;
    SharedPreferences sharedpreferences;
    private LinearLayout account_frag;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle;
    private TextView errorMessage;
    private TextView balance;
    private TextView balanceTransfer;
    private Button btnRetry;
    private Context context;
    private LinearLayout changeProImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        context = view.getContext();
        account_frag = (LinearLayout) view.findViewById(R.id.login_Account);
        errorLayout = (RelativeLayout) view.findViewById(R.id.errorLayout);
        errorImage = (ImageView) view.findViewById(R.id.errorImage);
        errorTitle = (TextView) view.findViewById(R.id.errorTitle);
        errorMessage = (TextView) view.findViewById(R.id.errorMessage);
        btnRetry = (Button) view.findViewById(R.id.btnRetry);
        sharedpreferences = this.getActivity().getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        proImage = (CircleImageView) view.findViewById(R.id.profileImage);
        btnCusProfile = (RelativeLayout) view.findViewById(R.id.btn_cusProfile);
        btnCusProfile.setOnClickListener(onCustomerProfileClick);
        btnSetting = (RelativeLayout) view.findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(onSettingClick);
        userName = (TextView) view.findViewById(R.id.lbl_userName);
        balance = (TextView) view.findViewById(R.id.balanceLbl);
        balanceTransfer = (TextView) view.findViewById(R.id.balanceTransferLbl);
        changeProImage = (LinearLayout) view.findViewById(R.id.changeProImage);
        changeProImage.setOnClickListener(onChangProImageClick);
        loadData();
        return view;
    }

    View.OnClickListener onCustomerProfileClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), CustomerProfileActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onSettingClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onLogoutClick = new View.OnClickListener() {
        public void onClick(View v) {
            disconnectFromFacebook();
        }
    };

    View.OnClickListener onChangProImageClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selectImage();
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

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        Bitmap profileImage = null;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                profileImage = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return profileImage;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            proImage.setImageBitmap(bitmap);
        }
    }

    private boolean checkLogin() {
        if (sharedpreferences.getString("userId", "") == null || sharedpreferences.getString("userId", "").isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void loadData() {
        if (checkLogin()) {
            userDetailModel = new UserDetailModel();
            String userId = Utils.getUserId(context);
            Call<UserDetailModel> call = Utils.getAPI().getUserDetailById(userId);
            call.enqueue(new Callback<UserDetailModel>() {
                @Override
                public void onResponse(Call<UserDetailModel> call, Response<UserDetailModel> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            userDetailModel = response.body();
                            if (!Utils.isNullOrEmpty(userDetailModel.getImangeUrl())) {
//                        new LoadImage().execute(userDetailModel.getImangeUrl());
                                Utils.loadProfileImge(context, proImage, userDetailModel.getImangeUrl());
                                Log.d(TAG, userDetailModel.getImangeUrl());
                            }
                            if (!Utils.isNullOrEmpty(userDetailModel.getFullName())) {
                                userName.setText(userDetailModel.getFullName());
                            }
                            if (userDetailModel.getTenant() != null) {
                                balance.setText(userDetailModel.getTenant().getBalance() + "$");
                                balanceTransfer.setText(userDetailModel.getTenant().getBalanceTranfer() + "$");
                            } else {

                                balance.setText("0$");
                                balanceTransfer.setText("0$");
                            }
                        } else {
                            String errorCode;
                            switch (response.code()) {
                                case 404:
                                    errorCode = "404 not found";
                                    break;
                                case 500:
                                    errorCode = "500 server broken";
                                    break;
                                default:
                                    errorCode = "unknown error";
                                    break;
                            }
                            Log.d(TAG, errorCode);
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<UserDetailModel> call, Throwable t) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                }
            });
        } else {
            showMessageNotLogin(R.drawable.sad, "Please login to see your account", "");
            showErrorLayout();
        }
    }

    public void showMessageNotLogin(int imageView, String title, String message) {

        showErrorLayout();
        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);
        btnRetry.setText("Login/SignUp");

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void showErrorLayout() {
        account_frag.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_GALLERY);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_GALLERY && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), selectedImage);
                proImage.setImageBitmap(bitmap);
                File f = FileUtils.getFile(getContext(),selectedImage);
                RequestBody filePath = RequestBody.create(MultipartBody.FORM,"filePath");
                RequestBody requestBody = RequestBody.create(
                        MediaType.parse(getActivity().getApplicationContext().getContentResolver().getType(selectedImage)),
                        f);
                Log.d(TAG, "onActivityResult: "+f +"fname: "+ f.getName()+" rqbd "+requestBody.toString());
                MultipartBody.Part filePart =
                        MultipartBody.Part.createFormData("file", f.getName(), requestBody);
                Call<ResponseBody> call = Utils.getAPI().uploadFile(filePart,filePath);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response == null || response.body() == null) {
                            Log.d(TAG, "onResponse: fail");
                            return;
                        }
                        try {
                            String responseUrl = response.body().string();
                            Log.d(TAG, "onResponse: "+responseUrl);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d(TAG, "onResponse: "+t);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    public void saveImage(UserDetailModel userDetail) {
        retrofit2.Call<UserDetailModel> call = Utils.getAPI().updateUserInfo(userDetailModel);
        call.enqueue(new Callback<UserDetailModel>() {
            @Override
            public void onResponse(Call<UserDetailModel> call, Response<UserDetailModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast toast = Toast.makeText(getContext(), "Update user information successfully", Toast.LENGTH_SHORT);
                        toast.show();
                        Log.d(TAG, "Update successfully");
                    } else {
                        String errorCode;
                        switch (response.code()) {
                            case 404:
                                errorCode = "404 not found";
                                break;
                            case 500:
                                errorCode = "500 server broken";
                                break;
                            default:
                                errorCode = "unknown error";
                                break;
                        }
                        Log.d(TAG, errorCode);
                        Toast toast = Toast.makeText(getContext(), errorCode, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    Toast toast = Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<UserDetailModel> call, Throwable t) {

            }
        });
    }
}