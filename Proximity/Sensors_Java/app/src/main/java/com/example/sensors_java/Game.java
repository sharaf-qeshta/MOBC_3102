package com.example.sensors_java;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Game extends AppCompatActivity implements SensorEventListener
{
    Thread questionsChanger;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);

        // register Accelerometer
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        // register proximity
        Sensor proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);

        TextView question = findViewById(R.id.tvQuestion);

        ArrayList<String> questions = (ArrayList<String>)
                getIntent().getSerializableExtra("Questions");

        question.setText(questions.get(0));

        questionsChanger = new Thread(() ->
        {
            try
            {
                int i = 1;
                while (true)
                {
                    if (i >= questions.size())
                        i = 0;

                    Thread.sleep(500); // 0.5 second
                    int finalI = i;
                    question.post(() ->
                            question.setText(questions.get(finalI)));
                    i++;
                }

            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        });

        questionsChanger.start();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            if (sensorEvent.values[2] < 0)
            {
                sensorManager.unregisterListener(this);
                super.onBackPressed();
            }
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY)
        {
            if ((int) sensorEvent.values[0] < 2)
            {
                try
                {
                    questionsChanger.interrupt();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
            else
            {
                if (questionsChanger.isInterrupted())
                    questionsChanger.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }
}