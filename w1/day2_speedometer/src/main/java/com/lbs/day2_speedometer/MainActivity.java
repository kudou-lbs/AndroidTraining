package com.lbs.day2_speedometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SpeedometerView currentSpeed;
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        initPermission();

        currentSpeed=findViewById(R.id.current_speed);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        currentSpeed.setSweepAngle(0);
    }

    //初始化权限
    private void initPermission(){
        String[] permissions={
                Manifest.permission.ACTIVITY_RECOGNITION
        };
        try {
            ActivityCompat.requestPermissions(this, permissions,0x0010);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("fuckkk","asdasd");
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private double last_value=0;
    private double current_value=0;
    private boolean motionState=true;

    @Override
    public void onSensorChanged(SensorEvent event) {
        double range=5; //设置一个精度范围
        double normalA=9.74;
        float[] value=event.values;
        current_value =magnitude(value[0],value[1],value[2]); //计算当前的模，也就是加速度值
        currentSpeed.onSpeedChanged((int)(current_value-normalA)*2);
        if(current_value<normalA)currentSpeed.setSweepAngle(0);
        //向上加速的状态
        if(motionState==true){
            if (current_value >= last_value)
                last_value = current_value;
            else {
                //检测到一次峰值
                if(Math.abs(current_value-last_value)>range){
                    motionState=false;
                }
            }

        }
        //向下加速的状态
        if(motionState==false){
            if (current_value <= last_value)
                last_value = current_value;
            else {
                //检测到一次峰值，设置步数+1
                if(Math.abs(current_value-last_value)>range){
                    currentSpeed.setStepCount();
                    motionState=true;
                }
            }
        }
    }

    //取模
    private double magnitude(float x, float y, float z) {
        double m=0;
        m=Math.sqrt(x*x+y*y+z*z);
        return m;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("fuckkk","步数捏???");
    }
}