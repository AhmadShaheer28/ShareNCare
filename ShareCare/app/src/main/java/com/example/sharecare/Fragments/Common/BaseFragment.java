package com.example.sharecare.Fragments.Common;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.sharecare.BaseActivity;
import com.example.sharecare.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;





public abstract class BaseFragment extends Fragment {

    private static final int STORAGE_ACCESS_REQUEST_CODE = 999;

    protected static final String TAG = "BaseFragment";
//    FirebaseAuth firebaseAuth;
//    FirebaseAnalytics firebaseAnalytics;
//    private SessionManager session;
//    private FirebaseRemoteConfig firebaseRemoteConfig;
//    CommunicationIntentService communicationIntentService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        session = new SessionManager(getActivity().getApplicationContext());
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        firebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
//
        getActivity().setTitle(R.string.challengenow);
        //communicationIntentService = new CommunicationIntentService();
    }

//    @Deprecated
//    public ChallengeNowApiClient getChallengeNowApiClient() {
//        return ((BaseActivity) getActivity()).getChallengeNowApiClient();
//    }

//    protected FirebaseAuth getFirebaseAuth() {
//        return firebaseAuth;
//    }

    protected void replaceFragment(Fragment fragment) {

        ((BaseActivity) getActivity()).replaceFragment(fragment);
    }


    protected void signUpWithGoogle(int requestCode) {
        ((BaseActivity) getActivity()).signInWithGoogle(requestCode);
    }



    protected void shareContent(String subject, String content, String dialogTitle) {

        ((BaseActivity) getActivity()).shareContentWithIntent(subject, content, dialogTitle);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show, final View listView, final View progressView) {
        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        listView.setVisibility(show ? View.GONE : View.VISIBLE);

    }

    public void copyToClipBoard(Context context, String payload) {
        ClipboardManager clipMan = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(getString(R.string.copied_to_clipboard), payload);
        clipMan.setPrimaryClip(clipData);
        Toast.makeText(context, getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show();
    }

//    protected SessionManager getSession() {
//        return session;
//    }
//
//    public void setSession(SessionManager session) {
//        this.session = session;
//    }


    protected void startActivity(Class<? extends Activity> clazz) {
        Intent k = new Intent(getContext(), clazz);
        startActivity(k);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public File shareScreenShotWithView(View view) throws IOException {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Bitmap bitmap = getScreenshot(view);
            String mPath = Environment.getExternalStorageDirectory() + File.separator + "screen_" + System.currentTimeMillis() + ".png";
            File imageFile = new File(mPath);
            OutputStream fout = null;
            try {
                fout = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
                fout.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fout.close();
            }
            return imageFile;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_ACCESS_REQUEST_CODE);
            return null;
        }
    }

    private Bitmap getScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }
}
