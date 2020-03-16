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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharecare.API.ApiClient;
import com.example.sharecare.API.ApiInterface;
import com.example.sharecare.ApiResponse.LoginResponse.LoginResponse;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import butterknife.BindView;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.session.Configuration;
import co.chatsdk.core.types.AccountDetails;
import co.chatsdk.firebase.FirebaseNetworkAdapter;
import co.chatsdk.firebase.file_storage.FirebaseFileStorageModule;
import co.chatsdk.ui.manager.BaseInterfaceAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.chatsdk.core.types.AccountDetails.username;

public class LogIn extends AppCompatActivity {


    EditText etEmail;


    EditText etPassword;

    @BindView(R.id.forgot_password_link)
    TextView forgotPassword;

    @BindView(R.id.progressView)
    View mProgressView;

    @BindView(R.id.login_form)
    View mLoginFormView;

    Button mEmailSignInButton;

    @BindView(R.id.sign_up_link)
    TextView registerLink;

    String email;
    String password;

    AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        registerLink = findViewById(R.id.sign_up_link);

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


        etEmail=(EditText) findViewById(R.id.email);
        etPassword=(EditText) findViewById(R.id.password);
        appPreferences = AppPreferences.getInstance(this);
        mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(), SignUp.class);
                startActivity(intent1);
            }
        });
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                validate(email, password);
            }
        });

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

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    private void validate(String email, String password) {
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Enter a valid Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        } else if (isNetworkAvailable(this))
        {
            doLogin(email, password);
            AccountDetails details = username(email, password);
            ChatSDK.auth().authenticate(details).subscribe();
        }


        else
            displayalertnetwork();
    }

    private void doLogin(final String email, String password) {


        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.login(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("onresponse", "onresponse");
                LoginResponse loginResponse;
                if (response.body() != null) {
                    loginResponse = response.body();
                    if (loginResponse.getError().equals("false")) {

                        appPreferences.setToken("bearer"+" "+loginResponse.getData().getToken());
                        appPreferences.saveUser(loginResponse.getData().getUser());
                        Intent intent = new Intent(LogIn.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();


                    } else if (loginResponse.getError().equals("true")) {
                        Toast.makeText(LogIn.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else if (response.code() == 401) {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("onfailure", "onfailure");
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                // progress.dismiss();
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
