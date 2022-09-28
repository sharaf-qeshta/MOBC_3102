package com.example.sensors_java;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
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

    static Point rightDownCorner = new Point();
    ImageView person;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor s = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        person = findViewById(R.id.person);

        rightDownCorner.x = 1080; // screen resolution
        rightDownCorner.y = 1920; // screen resolution

        sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];

            if (x > 0)
            {
                if (person.getX() > 0)
                    person.setX(person.getX() - x * 3);
            }
            else if (x < 0)
            {
                if (person.getX() < rightDownCorner.x)
                    person.setX(person.getX() - x * 3);
            }

            if (y > 0)
            {
                if (person.getY() < rightDownCorner.y)
                    person.setY(person.getY() + y * 3);
            }
            else if (y < 0)
            {
                if (person.getY() > 0)
                    person.setY(person.getY() + y * 3);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }
}