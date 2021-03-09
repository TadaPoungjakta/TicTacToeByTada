package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Rectangle;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Game extends AppCompatActivity {

    int BoardSize;
    int turn = 0;
    String turntxt = "O";
    int col;
    int rownum;
    int turncount = 0;
    int maxturn = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        BoardSize = intent.getIntExtra("BoardSize",0);
        String [][] field = new String [BoardSize][BoardSize];
        String [][] btnPosition = new String [(BoardSize*BoardSize)+1][3];
        String [][] BoardPostion = new String [BoardSize][BoardSize];

        HorizontalScrollView scroll = new HorizontalScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        TextView Turn = new TextView(this);
        layout.addView(Turn);
        Turn.setText("Turn:  O");
        for(rownum = 0;rownum<BoardSize;rownum++){
            LinearLayout row = new LinearLayout(this);
            for(col =0;col<BoardSize;col++){
                Button button = new Button(this);

                int id = col+1+(rownum*BoardSize);
                btnPosition[id][0] = String.valueOf(rownum);
                btnPosition[id][1] = String.valueOf(col);
                button.setText(""+id);
                button.setId(id);
                button.setGravity(Gravity.CENTER);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //System.out.println("BTNID___"+id);
                        if(turn % 2 == 0){
                            //System.out.println("SIGN O"+"ID:  "+id);
                            turn = turn + 1;
                            button.setText("O");
                            turntxt = "X";
                            button.setEnabled(false);
                            Turn.setText("Turn:   "+turntxt);
                            btnPosition[id][2] = "O";
                            button.setBackgroundColor(Color.GREEN);
                            //System.out.println("COLUMN   "+btnPosition[id][0]+"ROW    "+btnPosition[id][1]);
                            BoardPostion[Integer.parseInt(btnPosition[id][0])][Integer.parseInt(btnPosition[id][1])]="O";
                            //System.out.println(Arrays.deepToString(BoardPostion));

                            turncount++;
                        }else{
                            //System.out.println("SIGN X"+"ID:  "+id);
                            turn = turn + 1;
                            button.setText("X");
                            turntxt = "O";
                            button.setEnabled(false);
                            Turn.setText("Turn:   "+turntxt);
                            //System.out.println("COLUMN   "+btnPosition[id][0]+"ROW    "+btnPosition[id][1]);
                            btnPosition[id][2] = "X";
                            button.setBackgroundColor(Color.RED);
                            BoardPostion[Integer.parseInt(btnPosition[id][0])][Integer.parseInt(btnPosition[id][1])]="X";
                            //System.out.println(Arrays.deepToString(BoardPostion));
                            turncount++;
                        }
                        calWinner(BoardPostion);
                    }
                });
                row.addView(button);
            }

            layout.addView(row);
        }
        scroll.addView(layout);
        setContentView(scroll);
    }

    public void calWinner(String[][]BoardPosition) {
        int match = 0;
        if (turncount == (BoardSize * BoardSize)) {
            Intent intent = new Intent(this, GameOver.class);
            //startActivity(intent);
        }
        //HorizoltalWin
        for(int r=0;r<BoardSize;r++){
            for(int c=0;c<BoardSize;c++){
                System.out.println("ROW:  "+r+"COL:  "+c+"CHAR:     "+BoardPosition[r][c]+"         "+"ROW:  "+r+"COL:  "+(BoardSize-1)+"CHAR:     "+BoardPosition[r][BoardSize-1]+"     MATCH:    "+match);
                if(BoardPosition[r][c]==BoardPosition[r][BoardSize-1]&&BoardPosition[r][c]!=null){
                    match = match+1;
                    if(match>=BoardSize){
                        //System.out.println("BREAK HORIZONTAL WIN COMPLETE");
                        //GameOver(BoardPosition[r][c]);
                        savegamedata(BoardPosition,BoardPosition[r][c]);
                        break;
                    }
                }else{
                    match = 0;
                }
            }
        }
        //VerticalWin
        for(int c=0;c<BoardSize;c++){
            for(int r=0;r<BoardSize;r++){
                System.out.println(BoardPosition[r][c]+"      "+BoardPosition[BoardSize-1][c]);
                if(BoardPosition[r][c]==BoardPosition[BoardSize-1][c]&&BoardPosition[r][c]!=null){
                    match++;
                    if(match>=BoardSize){
                        //System.out.println("BREAK VERTICAL WIN COMPLETE");
                        //GameOver(BoardPosition[r][c]);
                        savegamedata(BoardPosition,BoardPosition[r][c]);
                        break;
                    }
                }else{
                    match=0;
                }
            }

        }
        //TopLtoBottomR
        for(int i=0;i<BoardSize;i++){
            if(BoardPosition[i][i]==BoardPosition[BoardSize-1][BoardSize-1]&&BoardPosition[i][i]!=null){
                match++;
                if(match>=BoardSize){
                    //System.out.println("BREAK TLtoBR WIN COMPLETE");
                    //GameOver(BoardPosition[i][i]);
                    savegamedata(BoardPosition,BoardPosition[i][i]);
                    break;
                }
            }else{
                match=0;
            }
        }
        //TopRToBottomL
        int max = BoardSize-1;
        for(int r=0;r<BoardSize;r++){
                System.out.println(r+"  "+max+"       "+BoardPosition[r][max]+"        "+BoardPosition[BoardSize-1][0]);
                if(BoardPosition[r][max]==BoardPosition[BoardSize-1][0]&&BoardPosition[r][max]!=null){
                    match++;
                    if(match>=BoardSize){
                        //System.out.println("BREAK TRtoBL WIN COMPLETE");
                        //GameOver(BoardPosition[r][max]);
                        savegamedata(BoardPosition,BoardPosition[r][max]);
                        break;
                    }
                }else{
                    match=0;
                }
            max--;
        }
    }

    public void savegamedata(String[][] BoardPosition,String Winner){

        String Gdata = "";
        for(int i=0;i<BoardSize;i++){
            for(int c=0;c<BoardSize;c++){
                if(BoardPosition[i][c]==null){
                    if(c==BoardSize-1){
                        System.out.println("|");
                        String gd = "-";
                        Gdata = Gdata+gd+"|";
                    }else{
                        String gd = "-";
                        Gdata = Gdata+gd+"     ";
                    }

                }else{
                    if(c==BoardSize-1){
                        System.out.println("|");
                        String gd = BoardPosition[i][c];
                        Gdata = Gdata+gd+"|";
                    }else{
                        String gd = BoardPosition[i][c];
                        Gdata = Gdata+gd+"     ";
                    }
                }
                //System.out.println("OutPut:       "+Gdata);
            }
        }

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        long time= System.currentTimeMillis();
        Date currentTime = Calendar.getInstance().getTime();
        database.child(String.valueOf(time)).child("Board").setValue(Gdata);
        database.child(String.valueOf(time)).child("Winner").setValue(Winner);
        database.child(String.valueOf(time)).child("PlayTime").setValue(String.valueOf(currentTime));

        GameOver(Winner);
    }

    public void GameOver(String Winnner){
        Intent intent = new Intent(this,GameOver.class);
        intent.putExtra("Winner",Winnner);
        startActivity(intent);
    }
}