package com.example.concern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class Menzura extends AppCompatActivity {
    Button button7;
    TextView textView29,textView30,textView31,textView32,textView34,textView35,textView36,textView37,textView38,textView39,textView40,textView41,textView42,textView43;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menzura);
        button7 = findViewById(R.id.button7);
        button7.setOnClickListener(v -> {
            Intent intent = new Intent(Menzura.this, MainMenu.class);
            startActivity(intent);
        });
        textView29 = findViewById(R.id.textView29);
        textView30 = findViewById(R.id.textView30);
        textView31 = findViewById(R.id.textView31);
        textView32 = findViewById(R.id.textView32);
        textView34 = findViewById(R.id.textView34);
        textView35 = findViewById(R.id.textView35);
        textView36 = findViewById(R.id.textView36);
        textView37 = findViewById(R.id.textView37);
        textView38 = findViewById(R.id.textView38);
        textView39 = findViewById(R.id.textView39);
        textView40 = findViewById(R.id.textView40);
        textView41 = findViewById(R.id.textView41);
        textView42 = findViewById(R.id.textView42);
        textView43 = findViewById(R.id.textView43);
        /*textView29.setBackgroundColor(Color.RED);
        textView30.setBackgroundColor(Color.RED);
        textView31.setBackgroundColor(Color.RED);
        textView32.setBackgroundColor(Color.RED);
        textView34.setBackgroundColor(Color.RED);
        textView35.setBackgroundColor(Color.RED);
        textView36.setBackgroundColor(Color.RED);
        textView37.setBackgroundColor(Color.RED);
        textView38.setBackgroundColor(Color.RED);*/
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
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        display((float) pitchInHz);
                        textView39.setText("" + (int) pitchInHz);
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
        if (pitchInHz > 640 && pitchInHz < 675 ) {
            textView41.setText("1 Струна");
            textView42.setText(" E = МИ");
            First((int)pitchInHz);
            E1((int)pitchInHz);
        }
        if (pitchInHz > 275 && pitchInHz < 350) {
            textView41.setText("1 Струна");
            textView42.setText(" E = МИ");
            First((int)pitchInHz);
            E12((int)pitchInHz);
        }
        //2
        if (pitchInHz > 480 && pitchInHz < 505) {
            textView41.setText("2 Струна");
            textView42.setText("H = СИ");
            Second((int)pitchInHz);
            H2((int)pitchInHz);
        }
        if (pitchInHz > 225 && pitchInHz < 275) {
            textView41.setText("2 Струна");
            textView42.setText("H = СИ");
            Second((int)pitchInHz);
            H12((int)pitchInHz);
        }
        //3
        if (pitchInHz > 380 && pitchInHz < 400) {
            textView41.setText("3 Струна");
            textView42.setText("G = СОЛЬ");
            Third((int)pitchInHz);
            G3((int)pitchInHz);
        }
        if (pitchInHz > 175 && pitchInHz < 225) {
            textView41.setText("3 Струна");
            textView42.setText("G = СОЛЬ");
            Third((int)pitchInHz);
            G32((int)pitchInHz);
        }
        //4
        if (pitchInHz > 280 && pitchInHz < 300) {
            textView41.setText("4 Струна");
            textView42.setText("D = РЕ");
            Fourth((int)pitchInHz);
            D4((int) pitchInHz);
        }
        if (pitchInHz > 125 && pitchInHz < 175) {
            textView41.setText("4 Струна");
            textView42.setText("D = РЕ");
            Fourth((int)pitchInHz);
            D42((int)pitchInHz);
        }
        //5
        if (pitchInHz > 210 && pitchInHz < 225) {
            textView41.setText("5 Струна");
            textView42.setText("A = ЛЯ");
            Fifth((int)pitchInHz);
            A5((int) pitchInHz);
        }
        if (pitchInHz > 98 && pitchInHz < 125) {
            textView41.setText("5 Струна");
            textView42.setText("A = ЛЯ");
            Fifth((int)pitchInHz);
            A52((int)pitchInHz);
        }
        //6
        if (pitchInHz > 155 && pitchInHz < 175) {
            textView41.setText("6 Струна");
            textView42.setText(" E = МИ");
            Sixth((int) pitchInHz);
            E6((int) pitchInHz);
        }
        if (pitchInHz > 0 && pitchInHz < 98) {
            textView41.setText("6 Струна");
            textView42.setText(" E = МИ");
            Sixth((int) pitchInHz);
            E62((int)pitchInHz);
        }
    }

    private void E62(int pitchInHz) {
        //***********************************
        if(pitchInHz >= 20 && pitchInHz < 40){
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 40 && pitchInHz < 60){
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 60 && pitchInHz < 75){
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 75 && pitchInHz <= 81){
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 82){
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 83 && pitchInHz < 85){
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 85 && pitchInHz < 90){
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 90 && pitchInHz < 95){
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 95 && pitchInHz < 98){
            textView38.setBackgroundColor(Color.GREEN);
        }else{
            textView38.setBackgroundColor(Color.RED);
        }
    }

    private void A52(int pitchInHz) {
        //******************************************
        if(pitchInHz >= 98 && pitchInHz < 102){
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 102 && pitchInHz < 104){
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 104 && pitchInHz < 106){
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 106 && pitchInHz <= 108){
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 109){
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 110 && pitchInHz < 115){
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 115 && pitchInHz < 120){
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 120 && pitchInHz < 122){
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 122 && pitchInHz < 125){
            textView38.setBackgroundColor(Color.GREEN);
        }else{
            textView38.setBackgroundColor(Color.RED);
        }
    }

    private void D42(int pitchInHz) {
        //*****************************************
        if(pitchInHz >= 125 && pitchInHz < 130){
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 130 && pitchInHz < 135){
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 135 && pitchInHz < 140){
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 140 && pitchInHz <= 145){
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 146){
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 147 && pitchInHz < 150){
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 150 && pitchInHz < 155){
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 155 && pitchInHz < 160){
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 160 && pitchInHz < 175){
            textView38.setBackgroundColor(Color.GREEN);
        }else{
            textView38.setBackgroundColor(Color.RED);
        }
    }

    private void G32(int pitchInHz) {
        //**************************************
        if(pitchInHz >= 175 && pitchInHz < 180){
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 180 && pitchInHz < 185){
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 185 && pitchInHz < 190){
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 190 && pitchInHz <= 194){
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 195){
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 196 && pitchInHz < 200){
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 200 && pitchInHz < 205){
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 205 && pitchInHz < 210){
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 210 && pitchInHz < 225){
            textView38.setBackgroundColor(Color.GREEN);
        }else{
            textView38.setBackgroundColor(Color.RED);
        }
    }

    private void H12(int pitchInHz) {
        //**************************************
        if(pitchInHz >= 225 && pitchInHz < 230){
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 230 && pitchInHz < 235){
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 235 && pitchInHz < 240){
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 240 && pitchInHz < 245){
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 245 && pitchInHz <= 247){
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 246){
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 247 && pitchInHz < 255){
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 255 && pitchInHz < 260){
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 260 && pitchInHz < 275){
            textView38.setBackgroundColor(Color.GREEN);
        }else{
            textView38.setBackgroundColor(Color.RED);
        }
    }

    private void E12(int pitchInHz) {
        //****************************************
        if(pitchInHz >= 275 && pitchInHz < 295){
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 295 && pitchInHz < 315){
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 315 && pitchInHz < 325){
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 325 && pitchInHz <= 327){
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if(pitchInHz == 328){
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 329 && pitchInHz < 335){
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 335 && pitchInHz < 340){
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 340 && pitchInHz < 345){
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if(pitchInHz >= 340 && pitchInHz < 350){
            textView38.setBackgroundColor(Color.GREEN);
        }else{
            textView38.setBackgroundColor(Color.RED);
        }
    }

    private void E1(int pitchInHz) {

        if (pitchInHz >= 640 && pitchInHz < 648) {
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 648 && pitchInHz < 656) {
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 656 && pitchInHz < 658) {
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 658) {
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 659) {
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 660) {
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 661 && pitchInHz < 663) {
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 663 && pitchInHz < 668) {
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 668 && pitchInHz < 676) {
            textView38.setBackgroundColor(Color.GREEN);
        }else {
            textView38.setBackgroundColor(Color.RED);
        }


    }

    private void H2(int pitchInHz) {
        if (pitchInHz >= 480 && pitchInHz < 484) {
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 484 && pitchInHz < 488) {
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 488 && pitchInHz < 492) {
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 492) {
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 493) {
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 494) {
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 495 && pitchInHz < 497) {
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 497 && pitchInHz < 500) {
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 500 && pitchInHz < 506) {
            textView38.setBackgroundColor(Color.GREEN);
        }else {
            textView38.setBackgroundColor(Color.RED);
        }

    }

    private void G3(int pitchInHz) {
        if (pitchInHz >= 380 && pitchInHz < 384) {
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 384 && pitchInHz < 388) {
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 388 && pitchInHz < 391) {
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 391) {
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 392) {
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 393) {
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 394 && pitchInHz < 396) {
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 396 && pitchInHz < 398) {
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 398 && pitchInHz < 401) {
            textView38.setBackgroundColor(Color.GREEN);
        }else {
            textView38.setBackgroundColor(Color.RED);
        }

    }

    private void D4(int pitchInHz) {
        if (pitchInHz >= 280 && pitchInHz < 284) {
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 284 && pitchInHz < 287) {
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 287 && pitchInHz < 290) {
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 290) {
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 291) {
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 292) {
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 293 && pitchInHz < 295) {
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 295 && pitchInHz < 298) {
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 298 && pitchInHz < 301) {
            textView38.setBackgroundColor(Color.GREEN);
        }else{
            textView38.setBackgroundColor(Color.RED);
        }

    }

    private void A5(int pitchInHz) {
        if (pitchInHz >= 210 && pitchInHz < 213) {
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 213 && pitchInHz < 215) {
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 215 && pitchInHz < 217) {
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 218) {
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 219) {
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 220) {
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 221 && pitchInHz < 223) {
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 223 && pitchInHz < 225) {
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 225 && pitchInHz < 227) {
            textView38.setBackgroundColor(Color.GREEN);
        }else{
            textView38.setBackgroundColor(Color.RED);
        }

    }

    private void E6(int pitchInHz) {
        if (pitchInHz >= 155 && pitchInHz < 158) {
            textView29.setBackgroundColor(Color.GREEN);
        }else{
            textView29.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 158 && pitchInHz < 161) {
            textView30.setBackgroundColor(Color.GREEN);
        }else{
            textView30.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 161 && pitchInHz < 163) {
            textView31.setBackgroundColor(Color.GREEN);
        }else{
            textView31.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 163) {
            textView32.setBackgroundColor(Color.GREEN);
        }else{
            textView32.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 164) {
            textView34.setBackgroundColor(Color.GREEN);
        }else{
            textView34.setBackgroundColor(Color.RED);
        }
        if (pitchInHz == 165) {
            textView35.setBackgroundColor(Color.GREEN);
        }else{
            textView35.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 166 && pitchInHz < 168) {
            textView36.setBackgroundColor(Color.GREEN);
        }else{
            textView36.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 168 && pitchInHz < 171) {
            textView37.setBackgroundColor(Color.GREEN);
        }else{
            textView37.setBackgroundColor(Color.RED);
        }
        if (pitchInHz >= 171 && pitchInHz < 175) {
            textView38.setBackgroundColor(Color.GREEN);
        }else{
            textView38.setBackgroundColor(Color.RED);
        }

    }


    @SuppressLint("SetTextI18n")
    private void Sixth(int pitchInHz) {
        textView40.setText("Требуемое значение на 12 ладу: 164 ");
        if(pitchInHz < 164) {
            textView43.setText("Уменьшите мензуру");
        }
        if (pitchInHz > 164){
            textView43.setText("Увеличте мензуру");
        }
        if (pitchInHz == 164){
            textView43.setText("Идеально");
        }
    }
    @SuppressLint("SetTextI18n")
    private void Fifth(int pitchInHz) {
        textView40.setText("Требуемое значение на 12 ладу: 219 ");
        if(pitchInHz < 219) {
            textView43.setText("Уменьшите мензуру");
        }
        if (pitchInHz > 219){
            textView43.setText("Увеличте мензуру");
        }
        if (pitchInHz == 219){
            textView43.setText("Идеально");
        }
    }
    @SuppressLint("SetTextI18n")
    private void Fourth(int pitchInHz) {
        textView40.setText("Требуемое значение на 12 ладу: 293 ");
        if(pitchInHz < 293) {
            textView43.setText("Уменьшите мензуру");
        }
        if (pitchInHz > 293){
            textView43.setText("Увеличте мензуру");
        }
        if (pitchInHz == 293){
            textView43.setText("Идеально");
        }
    }
    @SuppressLint("SetTextI18n")
    private void Third(int pitchInHz) {
        textView40.setText("Требуемое значение на 12 ладу: 392 ");
        if(pitchInHz < 392) {
            textView43.setText("Уменьшите мензуру");
        }
        if (pitchInHz > 392){
            textView43.setText("Увеличте мензуру");
        }
        if (pitchInHz == 392){
            textView43.setText("Идеально");
        }
    }
    @SuppressLint("SetTextI18n")
    private void Second(int pitchInHz) {
        textView40.setText("Требуемое значение на 12 ладу: 493 ");
        if(pitchInHz < 493) {
            textView43.setText("Уменьшите мензуру");
        }
        if (pitchInHz > 493){
            textView43.setText("Увеличте мензуру");
        }
        if (pitchInHz == 493){
            textView43.setText("Идеально");
        }
    }
    @SuppressLint("SetTextI18n")
    private void First(int pitchInHz) {
        textView40.setText("Требуемое значение на 12 ладу: 659 ");
        if(pitchInHz < 659) {
            textView43.setText("Уменьшите мензуру");
        }
        if (pitchInHz > 659){
            textView43.setText("Увеличте мензуру");
        }
        if (pitchInHz == 659){
            textView43.setText("Идеально");
        }
    }
}