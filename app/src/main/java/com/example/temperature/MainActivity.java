package com.example.temperature;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
SensorManager sm;
Sensor thermometerSensor;
TextView Temp,HeadlineTemp;
ProgressBar temperatureBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);

        thermometerSensor=sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

         Temp=(TextView)findViewById(R.id.displayTemp);

         HeadlineTemp=(TextView)findViewById(R.id.textView);

         temperatureBar=(ProgressBar)findViewById(R.id.progressBar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTemperature();
    }

    public void loadTemperature() {
        if(thermometerSensor!=null){
            sm.registerListener(this,thermometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(MainActivity.this,"Sensor not available",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        sm.unregisterListener(this,thermometerSensor);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSensorChanged(SensorEvent event) {
    float[] values=event.values;
    float temp=values[0];
    int progressTemp= (int) values[0];

    Temp.setText(String.valueOf(temp));


    temperatureBar.setProgress(progressTemp);

    if (temp>40){
        temperatureBar.setBackgroundColor(Color.RED);
        HeadlineTemp.setText("Temperature is high");

    }else if(temp<30 && temp>0){
        temperatureBar.setBackgroundColor(Color.GREEN);

        HeadlineTemp.setText("Temperature is medium");
    }else{
        temperatureBar.setBackgroundColor(Color.BLUE);
        HeadlineTemp.setText("Temperature is low");
    }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
