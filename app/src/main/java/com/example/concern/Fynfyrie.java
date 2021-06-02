package com.example.concern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
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

import static java.lang.Thread.sleep;

public class Fynfyrie extends AppCompatActivity {
    TextView textView27, textView19, textView22, textView23, textView26, textView21, textView17, textView24, textView28, textView33;
    Boolean ROn = true;

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
        new DEE().execute();
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

    class DEE extends AsyncTask {

        @Override
        protected Void doInBackground(Object[] objects) {
            while (ROn) {
                try {
                    //
                    textView27.setBackgroundColor(Color.GREEN);
                    textView22.setBackgroundColor(Color.GREEN);
                    textView26.setBackgroundColor(Color.GREEN);
                    textView17.setBackgroundColor(Color.GREEN);
                    //
                    textView19.setBackgroundColor(Color.RED);
                    textView23.setBackgroundColor(Color.RED);
                    textView21.setBackgroundColor(Color.RED);
                    textView24.setBackgroundColor(Color.RED);
                    //
                    sleep(250);
                    //
                    textView27.setBackgroundColor(Color.RED);
                    textView22.setBackgroundColor(Color.RED);
                    textView26.setBackgroundColor(Color.RED);
                    textView17.setBackgroundColor(Color.RED);
                    //
                    textView19.setBackgroundColor(Color.GREEN);
                    textView23.setBackgroundColor(Color.GREEN);
                    textView21.setBackgroundColor(Color.GREEN);
                    textView24.setBackgroundColor(Color.GREEN);
                    //
                    sleep(250);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }

                    });
                } catch (Exception e) {
                }
                }
                return null;
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
    @SuppressLint("SetTextI18n")
    private void slyxach(int pitchInHz){
        if (pitchInHz > 100 && pitchInHz < 150) {
            textView28.setText("Совет №"  +pitchInHz + ". Всегда Играйте стоя, таким образом вы создаёте себе зону комфорта.");
        }
        if (pitchInHz > 150 && pitchInHz < 200) {
            textView28.setText("Совет №"  +pitchInHz + ". Никогда не верьте гитаристам, обсуждающих влияние дерева на звук элестрогитары.");
        }
        if (pitchInHz > 200 && pitchInHz < 250){
            textView28.setText("Совет №"  +pitchInHz + ". Держите медиатор так, как захотите. Есть преподователи, которые рассказывают про стандарты хвата. Это слушать не стоит, все мы разные.");
        }
    }
}