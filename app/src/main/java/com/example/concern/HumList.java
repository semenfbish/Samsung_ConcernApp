package com.example.concern;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HumList extends AppCompatActivity {
    ListView userList;
    TextView header;
    public SQLiteDatabase db;
    static RaTingSQL RAT;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header = (TextView) findViewById(R.id.header);
        userList = (ListView) findViewById(R.id.list);

       // databaseHelper = new RaTingSQL(getApplicationContext());
        // db = RaTingSQL.getReadableDatabase();
        RAT = new RaTingSQL(this, "RAT", null, 1);
        db = RAT.getReadableDatabase();
    }
/*
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + RAT, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{RaTingSQL.TIME, RaTingSQL.NAME};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        header.setText("Найдено элементов: " + userCursor.getCount());
        userList.setAdapter(userAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }*/
}