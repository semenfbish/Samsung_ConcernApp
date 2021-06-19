package com.example.concern;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

import static java.lang.Thread.sleep;

public class MainGame extends AppCompatActivity {
    Button button4, button5;
    TextView textView3, textView45;
    EditText editText3;
    String A = "samsung";
    int Folt = 0;
    int T = 0;
    boolean Time = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        new Tim().execute();
        editText3 = findViewById(R.id.editText3);
        textView3 = findViewById(R.id.textView3);
        textView45 = findViewById(R.id.textView45);
        //textView3.setText("samsung is better than iphone!");
        textView3.setText(A);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

        button4.setOnClickListener(v -> {
            finish();
        });
        button5.setOnClickListener(v -> {
            if (editText3.getText().toString().equalsIgnoreCase(A)) {
                Toast.makeText(getApplicationContext(),
                        "Биг зур яхши",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainGame.this, Rating.class);
                intent.putExtra("Time", T);
                startActivity(intent);


            } else {
                Toast.makeText(getApplicationContext(),
                        "бик начар, яз! +5 секунд!",
                        Toast.LENGTH_SHORT).show();
                try {
                    T = T + 5;
                    textView45.setText(String.valueOf(T));
                } catch (Exception E) {

                }
            }
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
        }
    }
    class Tim extends AsyncTask {

        @Override
        protected Void doInBackground(Object[] objects) {
            while (Time) {
                try {
                    sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView45.setText(String.valueOf(T));
                            T++;
                        }
                    });
                } catch (Exception e) {}
            }
            return null;
        }
    }
}