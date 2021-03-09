package com.example.tictactoe;

public class BoardData {
    public String id;
    public String playdate;
    public String board;
    public String winner;

    public BoardData(String id, String playdate, String board, String winner) {
        this.id = id;
        this.playdate = playdate;
        this.board = board;
        this.winner = winner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaydate() {
        return playdate;
    }

    public void setPlaydate(String playdate) {
        this.playdate = playdate;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
