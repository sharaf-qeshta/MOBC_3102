package com.example.sensors_java;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    SensorManager sensorManager;
    Sensor sensor;
    CameraManager cameraManager;
    String cameraID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        cameraManager = (CameraManager)
                getSystemService(Context.CAMERA_SERVICE);
        try
        {
            cameraID = cameraManager.getCameraIdList()[0];
        }
        catch (CameraAccessException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            int value = (int) sensorEvent.values[0];
            // test whether the flash is available or not
            if (this.getPackageManager()
                    .hasSystemFeature(PackageManager
                            .FEATURE_CAMERA_FLASH))
            {
                if (value == 0)
                {
                    try
                    {
                        cameraManager.setTorchMode(cameraID,
                                true);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    try
                    {
                        cameraManager.setTorchMode(cameraID,
                                false);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            else
                Toast.makeText(this,
                        "Flash is not available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}