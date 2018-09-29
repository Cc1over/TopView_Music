package com.hebaiyi.www.topviewmusic.base.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.hebaiyi.www.topviewmusic.R;


public class PermissionActivity extends AppCompatActivity {

    public static int REQUEST_CODE = 33465;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perimission);
        setStatusBarColor();
        String[] permissions = getIntent().getStringArrayExtra("permissions");
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

    public static void actionStart(Activity activity, String[] permissions) {
        Intent i = new Intent(activity, PermissionActivity.class);
        i.putExtra("permissions",permissions);
        activity.startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 33465:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setResult(PackageManager.PERMISSION_GRANTED);
                    finish();
                } else {
                    getAppDetailSettingIntent();
                    setResult(RESULT_OK);
                    finish();
                }
        }
    }

    /**
     * 跳转到权限设置界面
     */
    private void getAppDetailSettingIntent() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

}
