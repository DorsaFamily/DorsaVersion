package com.psb.dorsaversion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.psb.versioncontrol.CheckVersion;

public class MainActivity extends AppCompatActivity {
    CheckVersion checkVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String BASE_URL = "http://79.175.155.143/fandoghestan/api/VersionsDorsa";

        checkVersion=new CheckVersion(this,BASE_URL,"testDorsa");
        checkVersion.setDebug(true);
        checkVersion.getVersion(new CheckVersion.onTaskFinished() {
            @Override
            public void onFinished(boolean exit) {
                if(exit)finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkVersion.checkActivityResult(requestCode,resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        checkVersion.checkPermission(requestCode);
    }
}
