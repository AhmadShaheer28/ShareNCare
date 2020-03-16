package com.example.sharecare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sharecare.API.ApiClient;
import com.example.sharecare.API.ApiInterface;
import com.example.sharecare.ApiResponse.SignupResponse.SignUpRequest;
import com.example.sharecare.ApiResponse.SignupResponse.SignUpResponse;
import com.fxn.utility.Utility;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.session.Configuration;
import co.chatsdk.core.types.AccountDetails;
import co.chatsdk.firebase.FirebaseNetworkAdapter;
import co.chatsdk.firebase.file_storage.FirebaseFileStorageModule;
import co.chatsdk.ui.manager.BaseInterfaceAdapter;
import io.fotoapparat.parameter.Flash;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.chatsdk.core.types.AccountDetails.signUp;

public class SignUp extends AppCompatActivity {

    Button signUpBtn;
    EditText etUserName;
    EditText etEmail;
    EditText etPassword;
    EditText etConfirmPassword;
    EditText etPhoneNo;


    String name;
    String email;
    String password;
    String gender="male";
    String phone_no;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpBtn = findViewById(R.id.signup_btn);
        etUserName = (EditText) findViewById(R.id.name);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        etPhoneNo = (EditText) findViewById(R.id.phoneNumber);

        radioGroup=(RadioGroup) findViewById(R.id.radio);

        Context context = getApplicationContext();

        try {
            // Create a new configuration
            Configuration.Builder builder = new Configuration.Builder();

            // Perform any other configuration steps (optional)
            builder.firebaseRootPath("prod");

            // Initialize the Chat SDK
            ChatSDK.initialize(context, builder.build(), FirebaseNetworkAdapter.class, BaseInterfaceAdapter.class);

            // File storage is needed for profile image upload and image messages
            FirebaseFileStorageModule.activate();

            // Push notification module
            //FirebasePushModule.activate();

            // Activate any other modules you need.
            // ...

        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                  @Override
                                                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                      radioButton = (RadioButton) findViewById(checkedId);
                                                      if (radioButton.getText().equals("Male"))
                                                      {
                                                          gender="male";
                                                      }
                                                      else
                                                      {
                                                          gender="female";
                                                      }
                                                  }
                                              }
        );


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etUserName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                phone_no = etPhoneNo.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                validate(name,email,password,phone_no,confirmPassword);

            }
        });
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    private void validate(String name, String email, String password, String phone, String confirmPassword) {

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(email)) {
            Toast.makeText(this, "Enter a valid Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        } else if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
            return;
        } else if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
            return;
        } else if (phone.isEmpty()) {
            Toast.makeText(this, "Enter Your Phone No", Toast.LENGTH_SHORT).show();
            return;
        } else if (!confirmPassword.equals(password)) {
            Toast.makeText(this, "Password and Confirm Password are not matched", Toast.LENGTH_SHORT).show();
            return;
        } else {

            doSignUp(new SignUpRequest(name, email, password, gender, phone_no));
            AccountDetails details = signUp(email, password);
            ChatSDK.auth().authenticate(details).subscribe();

        }
    }


    public static boolean isNetworkAvailable(Context context) {

        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            boolean flag = networkInfo != null && networkInfo.isAvailable()
                    && networkInfo.isConnected();
            if (flag) {
                boolean inetAddress = new AddressChecker().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get(); //You can replace it with your name
                return inetAddress;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class AddressChecker extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                try (Socket soc = new Socket()) {
                    soc.connect(new InetSocketAddress("www.google.com", 443), 2000);
                }
                return true;
            } catch (IOException ex) {
                return false;
            }
        }
    }


    private void doSignUp(SignUpRequest signUpRequest) {
        if (!isNetworkAvailable(this)) {
            displayalertnetwork();
            return;
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SignUpResponse> call = apiInterface.signUp(signUpRequest);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                SignUpResponse signUpResponse;
                if (response.body() != null) {
                    signUpResponse = response.body();
                    if (signUpResponse.getError().equals("false")) {
                        Intent intent = new Intent(SignUp.this, LogIn.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(SignUp.this, signUpResponse.getMessage(), Toast.LENGTH_LONG).show();

                } else if (response.code() == 401) {
                    Toast.makeText(SignUp.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                } else if (response.code() == 400) {
                    Toast.makeText(SignUp.this, "Email already exists, try with another email", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(SignUp.this, "Invalid Credentials", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void displayalertnetwork() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        final View mview = getLayoutInflater().inflate(R.layout.nointernetlayout, null);
        final Button ok = (Button) mview.findViewById(R.id.ok);
        builder.setView(mview);


        final AlertDialog alertDialog = builder.create();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }


}
