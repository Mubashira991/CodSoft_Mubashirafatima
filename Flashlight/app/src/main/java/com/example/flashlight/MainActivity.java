package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageButton toggle;
    boolean flash = false;
    boolean hasCameraflash = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggle=findViewById(R.id.imageButton2);
        hasCameraflash=getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasCameraflash){
                    if(flash){
                        flash=false;
                        toggle.setImageResource(R.drawable.baseline_flashlight_off_24);
                        try {
                            flashLightOff();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    else{
                        flash=true;
                        toggle.setImageResource(R.drawable.baseline_flashlight_on_24);
                        try {
                            flashLightOn();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Device Flashlight not found!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    void flashLightOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager!=null;
        String CameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(CameraId,true);
        Toast.makeText(MainActivity.this, "Flashlight is ON", Toast.LENGTH_SHORT).show();
    }

    public void flashLightOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager!=null;
        String CameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(CameraId,false);
        Toast.makeText(MainActivity.this, "Flashlight is OFF", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        // Turn off flashlight when the back button is pressed
        if (flash) {
            flash = false;
            toggle.setImageResource(R.drawable.baseline_flashlight_off_24);
            try {
                flashLightOff();
            } catch (CameraAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        // Turn off flashlight when the app is paused
        if (flash) {
            flash = false;
            toggle.setImageResource(R.drawable.baseline_flashlight_off_24);
            try {
                flashLightOff();
            } catch (CameraAccessException e) {
                throw new RuntimeException(e);
            }
        }

        super.onPause();
    }
}