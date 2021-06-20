package com.example.concern;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static android.text.util.Linkify.ALL;
import static java.util.Spliterator.DISTINCT;

public class Rating extends AppCompatActivity{
    Button button13, button15;
    TextView textView5, textView46,textView44;
    EditText editTextTextPersonName;
    public SQLiteDatabase db;
    static RaTingSQL Hum;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        int Sec;
        int defaultValue = 0;
        Intent intent = getIntent();
        Sec = intent.getIntExtra("Time", defaultValue);
        Hum = new RaTingSQL(this, "Hum", null, 1);
        db = Hum.getReadableDatabase();
        textView5 = findViewById(R.id.textView5);
        textView46 = findViewById(R.id.textView46);
        textView44 = findViewById(R.id.textView44);
        textView44.setText(String.valueOf(Sec));
        button13 = findViewById(R.id.button13);
        button15 = findViewById(R.id.button15);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        button15.setOnClickListener(v->{
            finish();
        });
        button13.setOnClickListener(v->{
            String Time = textView44.getText().toString();
            String Name = editTextTextPersonName.getText().toString();
            if (editTextTextPersonName.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Поля не полные!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent1 = new Intent(this, HumList.class);
                db.execSQL("INSERT INTO RATING(TIME, NAME) VALUES ('"+Time+"','"+Name+"')");
                startActivity(intent1);
            }
        });
    }
}