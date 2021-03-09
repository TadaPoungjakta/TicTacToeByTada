package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    int boardSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},103);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},103);


        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        Button GameNav = findViewById(R.id.button);
        Button GameHistory = findViewById(R.id.historybtn);
        GameNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText BoardSize = findViewById(R.id.boardsize);

                boardSize = Integer.parseInt(BoardSize.getText().toString());

                if(boardSize<=1){
                    Toast.makeText(MainActivity.this,"Please Enter Number More Than 1",Toast.LENGTH_LONG).show();
                }else if(boardSize>1){
                    Intent intent = new Intent(MainActivity.this,Game.class);
                    intent.putExtra("BoardSize",boardSize);
                    startActivity(intent);
                }
            }
        });

        GameHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GameHistory.class);
                startActivity(intent);
            }
        });

    }

}