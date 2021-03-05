package com.example.test.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.test.R;

public class PauseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);
        ImageButton Restart = (ImageButton)findViewById(R.id.restart);
        ImageButton Set = (ImageButton)findViewById(R.id.setting);
        ImageButton Rank = (ImageButton)findViewById(R.id.rank);
        ImageButton Exit = (ImageButton)findViewById(R.id.exit);
        ImageButton close = (ImageButton)findViewById(R.id.close);

        Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PauseActivity.this, PlayingActivity.class);
                startActivity(intent);
            }
        });

        Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PauseActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        Rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PauseActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PauseActivity.this, main_Interface.class);
                startActivity(intent);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }






}
