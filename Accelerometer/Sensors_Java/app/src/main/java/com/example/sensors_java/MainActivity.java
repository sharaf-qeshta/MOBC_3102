package com.example.sensors_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @author Sharaf Qeshta
 * */
public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    ImageView xStatus, yStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xStatus = findViewById(R.id.xStatus);
        yStatus = findViewById(R.id.yStatus);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach(e -> System.out.println(e.getName()));

        Sensor s = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(s != null)
            System.out.println("Sensor Accelerometer is found ☺");
        else
            System.out.println("Sensor Accelerometer is not found!!!");


        sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];

            xStatus.setBackground(null);
            yStatus.setBackground(null);

            if (x != 0)
            {
                if (x > 0)
                    xStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_left));
                else
                    xStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_right));
            }

            if (y != 0)
            {
                if (y > 0)
                    yStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_down));
                else
                    yStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_up));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }
}