package com.example.concern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    Button button2, button3,button6,button9,button10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button6 = findViewById(R.id.button6);
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, MainActivity.class);
            startActivity(intent);
        });
        button3.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, MainGame.class);
            startActivity(intent);
        });
        button6.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, Menzura.class);
            startActivity(intent);
        });
        button9.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, Fynfyrie.class);
            startActivity(intent);
        });
        button10.setOnClickListener(v ->{
            Intent intent = new Intent(MainMenu.this, Sprav.class);
            startActivity(intent);
        });
    }
    
}