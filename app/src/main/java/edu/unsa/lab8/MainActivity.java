package edu.unsa.lab8;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private final float[] gravity = new float[3];
    private final float[] linearAcceleration = new float[3];
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TextView result;
    private TextView aceleracionX;
    private TextView aceleracionY;
    private TextView aceleracionZ;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.result = findViewById(R.id.result);
        this.aceleracionX = findViewById(R.id.aceleracionX);
        this.aceleracionY = findViewById(R.id.aceleracionY);
        this.aceleracionZ = findViewById(R.id.aceleracionZ);
        this.view = findViewById(R.id.back);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.8f;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        linearAcceleration[0] = event.values[0] - gravity[0];
        linearAcceleration[1] = event.values[1] - gravity[1];
        linearAcceleration[2] = event.values[2] - gravity[2];
        aceleracionX.setText("X: " + linearAcceleration[0]);
        aceleracionY.setText("Y: " +linearAcceleration[1]);
        aceleracionZ.setText("Z: "+ linearAcceleration[2]);
        double resultAcceleration = Math.sqrt(Math.pow(linearAcceleration[0], 2.0) + Math.pow(linearAcceleration[1], 2.0) + Math.pow(linearAcceleration[2], 2.0));
        resultAcceleration = ((int) (resultAcceleration * 1000)) / 1000;
        result.setText(resultAcceleration + "");
        if(resultAcceleration == 0){
            view.setBackgroundColor(Color.RED);
        }else{
            view.setBackgroundColor(Color.WHITE);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}