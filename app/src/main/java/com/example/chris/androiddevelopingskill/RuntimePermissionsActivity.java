package com.example.chris.androiddevelopingskill;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.permission;

/**
 * Created by Admin on 2017/6/11.
 */

public class RuntimePermissionsActivity extends AppCompatActivity {

    private static final String TAG = "RuntimePermission";

    // 所需的全部权限
    private static final String[] PERMISSIONS = new String[] { android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_CODE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isMarshmallow()) {
            String[] permissions = checkPermissions(PERMISSIONS);
            if (permissions.length > 0) {
                requestPermissions(permissions, REQUEST_WRITE_EXTERNAL_STORAGE_CODE);
            } else {
                //需要权限才能操作的代码
                Log.i(TAG, permission + "is ok");
            }
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
    @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (permission.equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        Log.i(TAG, permission + "is true");
                    } else {
                        Log.i(TAG, permission + "is false");
                    }
                }
            }
        }
    }

    public String[] checkPermissions(String ... permissions) {
        List<String> deniedPermissionList = new ArrayList<>();
        for(String permission : permissions) {
            if(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissionList.add(permission);
            }
        }
        return deniedPermissionList.toArray(new String[0]);
    }


    public boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
