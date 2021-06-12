package com.example.concern;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Map;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

import static android.view.KeyEvent.KEYCODE_1;
import static android.view.KeyEvent.KEYCODE_3;
import static android.view.KeyEvent.KEYCODE_4;
import static android.view.KeyEvent.KEYCODE_ALT_LEFT;
import static android.view.KeyEvent.KEYCODE_SWITCH_CHARSET;

public class MainActivity extends AppCompatActivity {
    // list of all the notes , string names and their frequencies
    TextView tt, textView7, textView8, textView9, textView10, textView11, textView12, textView13, textView14, textView15, textView16, textView18;
    Button button;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tt = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textView11 = findViewById(R.id.textView11);
        textView12 = findViewById(R.id.textView12);
        textView13 = findViewById(R.id.textView13);
        textView14 = findViewById(R.id.textView14);
        textView15 = findViewById(R.id.textView15);
        textView16 = findViewById(R.id.textView16);
        textView18 = findViewById(R.id.textView18);
        /*textView7.setBackgroundColor(Color.RED);
        textView8.setBackgroundColor(Color.RED);
        textView9.setBackgroundColor(Color.RED);
        textView10.setBackgroundColor(Color.RED);
        textView11.setBackgroundColor(Color.RED);
        textView12.setBackgroundColor(Color.RED);
        textView13.setBackgroundColor(Color.RED);
        textView14.setBackgroundColor(Color.RED);
        textView15.setBackgroundColor(Color.RED);*/
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainMenu.class);
            startActivity(intent);
        });
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        1);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        1);
            }

        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now
            start();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    start();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    public void start() {
        // getting pitch from live audio , and sending it to processPitch()

        int SAMPLE_RATE = 44100;
        int BUFFER_SIZE = 1024 * 4;
        int OVERLAP = 768 * 4;

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(SAMPLE_RATE, BUFFER_SIZE, OVERLAP);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                final float pitchInHz = res.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tt.setText("" + (int) pitchInHz);
                        display((float) pitchInHz);

                    }
                });
            }
        };
        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, SAMPLE_RATE, BUFFER_SIZE, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);

        Thread audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();
    }

    @SuppressLint("LongLogTag")
    private void display(float pitchInHz) {
        TextView textView = findViewById(R.id.textView);
        textView.setBackgroundColor(Color.RED);
        if (pitchInHz > 0) {
            textView.setBackgroundColor(Color.GREEN);
        }
        //1
        if (pitchInHz > 275 && pitchInHz < 350) {
            textView16.setText("1 Струна");
            textView18.setText(" E = МИ");
            First((int)pitchInHz);
        }
        //2
        if (pitchInHz > 225 && pitchInHz < 275) {
            textView16.setText("2 Струна");
            textView18.setText("H = СИ");
            Second((int)pitchInHz);
        }
        //3
        if (pitchInHz > 175 && pitchInHz < 225) {
            textView16.setText("3 Струна");
            textView18.setText("G = СОЛЬ");
            Third((int)pitchInHz);
        }
        //4
        if (pitchInHz > 125 && pitchInHz < 175) {
            textView16.setText("4 Струна");
            textView18.setText("D = РЕ");
            Fourth((int)pitchInHz);
        }
        //5
        if (pitchInHz > 98 && pitchInHz < 125) {
            textView16.setText("5 Струна");
            textView18.setText("A = ЛЯ");
            Fifth((int)pitchInHz);
        }
        //6
        if (pitchInHz > 0 && pitchInHz < 98) {
            textView16.setText("6 Струна");
            textView18.setText(" E = МИ");
            Sixth((int) pitchInHz);
        }
    }

    private void Sixth(int pitchInHz) {
        //6**********************************************************************
        if(pitchInHz >= 20 && pitchInHz < 40){
            textView7.setBackgroundColor(Color.GREEN);
        }else{
            textView7.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 40 && pitchInHz < 60){
            textView12.setBackgroundColor(Color.GREEN);
        }else{
            textView12.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 60 && pitchInHz < 75){
            textView10.setBackgroundColor(Color.GREEN);
        }else{
            textView10.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 75 && pitchInHz <= 81){
            textView9.setBackgroundColor(Color.GREEN);
        }else{
            textView9.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 82){
            textView11.setBackgroundColor(Color.GREEN);
        }else{
            textView11.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 83 && pitchInHz < 85){
            textView14.setBackgroundColor(Color.GREEN);
        }else{
            textView14.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 85 && pitchInHz < 90){
            textView8.setBackgroundColor(Color.GREEN);
        }else{
            textView8.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 90 && pitchInHz < 95){
            textView13.setBackgroundColor(Color.GREEN);
        }else{
            textView13.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 95 && pitchInHz < 98){
            textView15.setBackgroundColor(Color.GREEN);
        }else{
            textView15.setBackgroundColor(Color.RED);
        }
    }

    private void Fifth(int pitchInHz) {
        //5************************************************************************
        if(pitchInHz >= 98 && pitchInHz < 102){
            textView7.setBackgroundColor(Color.GREEN);
        }else{
            textView7.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 102 && pitchInHz < 104){
            textView12.setBackgroundColor(Color.GREEN);
        }else{
            textView12.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 104 && pitchInHz < 106){
            textView10.setBackgroundColor(Color.GREEN);
        }else{
            textView10.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 106 && pitchInHz <= 108){
            textView9.setBackgroundColor(Color.GREEN);
        }else{
            textView9.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 109){
            textView11.setBackgroundColor(Color.GREEN);
        }else{
            textView11.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 110 && pitchInHz < 115){
            textView14.setBackgroundColor(Color.GREEN);
        }else{
            textView14.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 115 && pitchInHz < 120){
            textView8.setBackgroundColor(Color.GREEN);
        }else{
            textView8.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 120 && pitchInHz < 122){
            textView13.setBackgroundColor(Color.GREEN);
        }else{
            textView13.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 122 && pitchInHz < 125){
            textView15.setBackgroundColor(Color.GREEN);
        }else{
            textView15.setBackgroundColor(Color.RED);
        }
    }

    private void Fourth(int pitchInHz) {
        //4********************************************************
        if(pitchInHz >= 125 && pitchInHz < 130){
            textView7.setBackgroundColor(Color.GREEN);
        }else{
            textView7.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 130 && pitchInHz < 135){
            textView12.setBackgroundColor(Color.GREEN);
        }else{
            textView12.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 135 && pitchInHz < 140){
            textView10.setBackgroundColor(Color.GREEN);
        }else{
            textView10.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 140 && pitchInHz < 145){
            textView9.setBackgroundColor(Color.GREEN);
        }else{
            textView9.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 146){
            textView11.setBackgroundColor(Color.GREEN);
        }else{
            textView11.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 147 && pitchInHz < 150){
            textView14.setBackgroundColor(Color.GREEN);
        }else{
            textView14.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 150 && pitchInHz < 155){
            textView8.setBackgroundColor(Color.GREEN);
        }else{
            textView8.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 155 && pitchInHz < 160){
            textView13.setBackgroundColor(Color.GREEN);
        }else{
            textView13.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 160 && pitchInHz < 175){
            textView15.setBackgroundColor(Color.GREEN);
        }else{
            textView15.setBackgroundColor(Color.RED);
        }
    }

    private void Third(int pitchInHz) {
        //3******************************************************
        if(pitchInHz >= 175 && pitchInHz < 180){
            textView7.setBackgroundColor(Color.GREEN);
        }else{
            textView7.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 180 && pitchInHz < 185){
            textView12.setBackgroundColor(Color.GREEN);
        }else{
            textView12.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 185 && pitchInHz < 190){
            textView10.setBackgroundColor(Color.GREEN);
        }else{
            textView10.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 190 && pitchInHz <= 194){
            textView9.setBackgroundColor(Color.GREEN);
        }else{
            textView9.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 195){
            textView11.setBackgroundColor(Color.GREEN);
        }else{
            textView11.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 196 && pitchInHz < 200){
            textView14.setBackgroundColor(Color.GREEN);
        }else{
            textView14.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 200 && pitchInHz < 205){
            textView8.setBackgroundColor(Color.GREEN);
        }else{
            textView8.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 205 && pitchInHz < 210){
            textView13.setBackgroundColor(Color.GREEN);
        }else{
            textView13.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 210 && pitchInHz < 225){
            textView15.setBackgroundColor(Color.GREEN);
        }else{
            textView15.setBackgroundColor(Color.RED);
        }
    }

    private void Second(int pitchInHz) {
        //2***********************************************************
        if(pitchInHz >= 225 && pitchInHz < 230){
            textView7.setBackgroundColor(Color.GREEN);
        }else{
            textView7.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 230 && pitchInHz < 235){
            textView12.setBackgroundColor(Color.GREEN);
        }else{
            textView12.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 235 && pitchInHz < 240){
            textView10.setBackgroundColor(Color.GREEN);
        }else{
            textView10.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 240 && pitchInHz <= 245){
            textView9.setBackgroundColor(Color.GREEN);
        }else{
            textView9.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 246){
            textView11.setBackgroundColor(Color.GREEN);
        }else{
            textView11.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 247 && pitchInHz < 250){
            textView14.setBackgroundColor(Color.GREEN);
        }else{
            textView14.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 250 && pitchInHz < 255){
            textView8.setBackgroundColor(Color.GREEN);
        }else{
            textView8.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 255 && pitchInHz < 260){
            textView13.setBackgroundColor(Color.GREEN);
        }else{
            textView13.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 260 && pitchInHz < 275){
            textView15.setBackgroundColor(Color.GREEN);
        }else{
            textView15.setBackgroundColor(Color.RED);
        }
    }

    private void First(int pitchInHz) {
        //1********************************************************
        if(pitchInHz >= 275 && pitchInHz < 295){
            textView7.setBackgroundColor(Color.GREEN);
        }else{
            textView7.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 295 && pitchInHz < 315){
            textView12.setBackgroundColor(Color.GREEN);
        }else{
            textView12.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 315 && pitchInHz < 325){
            textView12.setBackgroundColor(Color.GREEN);
        }else{
            textView12.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 325 && pitchInHz <= 327){
            textView9.setBackgroundColor(Color.GREEN);
        }else{
            textView9.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 328){
            textView11.setBackgroundColor(Color.GREEN);
        }else{
            textView11.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 329 && pitchInHz < 335){
            textView14.setBackgroundColor(Color.GREEN);
        }else{
            textView14.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 335 && pitchInHz < 340){
            textView8.setBackgroundColor(Color.GREEN);
        }else{
            textView8.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 340 && pitchInHz < 345){
            textView13.setBackgroundColor(Color.GREEN);
        }else{
            textView13.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 340 && pitchInHz < 350){
            textView15.setBackgroundColor(Color.GREEN);
        }else{
            textView15.setBackgroundColor(Color.RED);
        }
    }
}