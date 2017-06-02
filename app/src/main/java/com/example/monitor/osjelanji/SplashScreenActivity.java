package com.example.monitor.osjelanji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

/**
 * Created by monitor on 24/05/2017.
 */

public class SplashScreenActivity extends Activity {
    public static final int segundos = 5;
    public static final int milisegundos=segundos*1000;
    public static final int delay=2;
    private ProgressBar progressBar3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        progressBar3= (ProgressBar) findViewById(R.id.progressBar3);
        progressBar3.setMax(maximo_progreso());

        empezaranimacion();
    }
    public void empezaranimacion(){
        new CountDownTimer(milisegundos,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                progressBar3.setProgress(establecer_progreso(millisUntilFinished));

            }

            @Override
            public void onFinish() {
            Intent nuevofrom=new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(nuevofrom);
                finish();
            }
        }.start();
    }

    public int establecer_progreso(long miliseconds){
        return (int)((milisegundos-miliseconds)/1000);
    }

    public int maximo_progreso(){
        return segundos-delay;
    }
}





/**public class SplashScreenActivity extends Activity {

    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Establece la orientacion de la pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Oculta el titulo
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_screen);

        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                //Inicia la actividad
                Intent mainIntent  = new Intent().setClass(SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);


    }
} */
