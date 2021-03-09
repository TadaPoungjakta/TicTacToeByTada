package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class GameHistoryDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history_detail);
        Intent intent = getIntent();
        String GameData = intent.getStringExtra("BoardData");
        String PlayTime = intent.getStringExtra("PlayDate");
        String Winner = intent.getStringExtra("Winner");

        String[] ss = GameData.split("[|]");
        //System.out.println("OUTPUT:      "+Arrays.toString(ss));

        ArrayAdapter < String > dataAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, ss );
        ListView Board = findViewById(R.id.Board);
        Board.setAdapter(dataAdapter);

        TextView playtime = findViewById(R.id.playdatetxt);
        TextView winner = findViewById(R.id.winnertxt);

        playtime.setText(PlayTime);
        winner.setText("Winner:     "+Winner);

    }
}