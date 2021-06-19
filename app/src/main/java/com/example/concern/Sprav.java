package com.example.concern;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Sprav extends AppCompatActivity {
Button button8,button11,button12;
    ImageView imageView,imageView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprav);
        imageView = findViewById(R.id.imageView);
        imageView3 = findViewById(R.id.imageView3);
        button8 = findViewById(R.id.button8);
        button11 = findViewById(R.id.button11);
        button12 = findViewById(R.id.button12);
        button8.setOnClickListener(v ->{
            finish();
        });
        button11.setOnClickListener(v -> {
            new DownloadImageTask(imageView, "EN_Key").execute("https://raw.githubusercontent.com/semenfbish/ShuraleGi/main/rus_key.png");
        });
        button12.setOnClickListener(v -> {
            new DownloadImageTask(imageView3, "Rus_Key").execute("https://raw.githubusercontent.com/semenfbish/ShuraleGi/main/En_Key.png");
        });

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        String filename;
        public DownloadImageTask(ImageView bmImage, String filename) {
            this.bmImage = bmImage;
            this.filename = filename;
        }

        @SuppressLint("LongLogTag")
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                SaveImage(mIcon11, filename);
            } catch (Exception e) {
                Log.e("Ошибка передачи изображения", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }
    private void SaveImage(Bitmap finalBitmap, String fileName) throws IOException {


        File root = new File(this.getExternalFilesDir(null).getPath());
        File myDir = new File(root + "/saved_images");
        myDir.mkdir();

        String fname = fileName + ".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}