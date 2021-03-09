package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameOver extends AppCompatActivity {

    String Winner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        Winner = intent.getStringExtra("Winner");
        TextView WinnerTxt = findViewById(R.id.result);
        if(Winner!=null){
            WinnerTxt.setText("Winner:    "+Winner);
        }else{
            WinnerTxt.setText("Draw");
        }

        Button home = findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Button history = findViewById(R.id.histroyBtn);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this,GameHistory.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed(){
        Toast.makeText(GameOver.this,"Please Press Home Button",Toast.LENGTH_LONG).show();
    }
}