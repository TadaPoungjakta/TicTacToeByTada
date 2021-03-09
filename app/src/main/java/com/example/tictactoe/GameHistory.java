package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameHistory extends AppCompatActivity {

    ArrayList<BoardData> BD = new ArrayList<>();
    ArrayList<String> PlayDate = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);



        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        Query queryl = database;
        queryl.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BoardData board;
                for(DataSnapshot ds:snapshot.getChildren()){
                    //Log.d("LIST",ds.getValue().toString());
                    //Log.d("Board",ds.child("Board").getValue().toString());
                    board = new BoardData(ds.getKey(),
                            ds.child("PlayTime").getValue().toString(),
                            ds.child("Board").getValue().toString(),
                            ds.child("Winner").getValue().toString());
                    BD.add(board);
                    PlayDate.add(ds.child("PlayTime").getValue().toString());
                }
                GameHistoryAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void GameHistoryAdapter(){
        ArrayAdapter< String > dataAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, PlayDate );
        ListView Listgamehistory = findViewById(R.id.listgamehistory);
        Listgamehistory.setAdapter(dataAdapter);

        Listgamehistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GameHistory.this, GameHistoryDetail.class);
                intent.putExtra("BoardData",BD.get(position).getBoard());
                intent.putExtra("PlayDate",BD.get(position).getPlaydate());
                intent.putExtra("Winner",BD.get(position).getWinner());
                startActivity(intent);
            }
        });

    }
}