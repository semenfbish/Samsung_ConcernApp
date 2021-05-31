package com.example.concern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class Fynfyrie extends AppCompatActivity {
    TextView textView27, textView19, textView22, textView23, textView26, textView21, textView17, textView24, textView25,textView28,textView33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fynfyrie);
        textView27 = findViewById(R.id.textView27);
        textView19 = findViewById(R.id.textView19);
        textView22 = findViewById(R.id.textView22);
        textView23 = findViewById(R.id.textView23);
        textView26 = findViewById(R.id.textView26);
        textView21 = findViewById(R.id.textView21);
        textView17 = findViewById(R.id.textView17);
        textView24 = findViewById(R.id.textView24);
        textView25 = findViewById(R.id.textView25);
        textView28 = findViewById(R.id.textView28);
        textView33 = findViewById(R.id.textView33);
        textView27.setBackgroundColor(Color.RED);
        textView19.setBackgroundColor(Color.RED);
        textView22.setBackgroundColor(Color.RED);
        textView23.setBackgroundColor(Color.RED);
        textView26.setBackgroundColor(Color.RED);
        textView21.setBackgroundColor(Color.RED);
        textView17.setBackgroundColor(Color.RED);
        textView24.setBackgroundColor(Color.RED);
        textView25.setBackgroundColor(Color.RED);
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
                        textView33.setText(""+ (int) pitchInHz);
                    }
                });
            }
        };
        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, SAMPLE_RATE, BUFFER_SIZE, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);

        Thread audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();
    }
    public void display(float pitchInHz){

        slyxach((int) pitchInHz);
    }
    private void slyxach(int pitchInHz){
        if (pitchInHz > 100 && pitchInHz < 150) {
            textView27.setBackgroundColor(Color.GREEN);
            textView28.setText("Совет №"  +pitchInHz + ". Слышится хорошо засушёный клён, ваша гитара скорее всего уже отвинтажилась со временем.");
        }
        if (pitchInHz > 0 && pitchInHz < 51) {
            textView27.setBackgroundColor(Color.GREEN);
            textView28.setText("Совет №"  +pitchInHz + ". Cлышатся нотки Болотного Ясеня, а вот это уже не хорошо, советую обратиться к мастеру с целью замены его на другое более благородное дерево. Но, есть и хорошая новость, ваш топ гитары состоит из двух досок, а это координально влияет на сустейн гитары.");
        }
    }
}