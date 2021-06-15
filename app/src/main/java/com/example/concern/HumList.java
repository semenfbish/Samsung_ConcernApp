package com.example.concern;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.concern.RaTingSQL.RATING;

public class HumList extends AppCompatActivity {
    ListView userList;
    TextView header;
    public SQLiteDatabase db;
    static RaTingSQL Hum;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hum_list);
        header = (TextView) findViewById(R.id.header);
        userList = (ListView) findViewById(R.id.list);
       // databaseHelper = new RaTingSQL(getApplicationContext());
        // db = RaTingSQL.getReadableDatabase();
        Hum = new RaTingSQL(this, "Hum", null, 1);
        db = Hum.getReadableDatabase();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + RATING , null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{RaTingSQL.NAME, RaTingSQL.TIME};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        header.setText("Rating: " + userCursor.getCount());
        userList.setAdapter(userAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}