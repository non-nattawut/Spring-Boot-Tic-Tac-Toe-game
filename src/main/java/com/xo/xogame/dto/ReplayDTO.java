package com.xo.xogame.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReplayDTO {
    private Integer id;
    private String winner;
    private Integer row;
    private Integer col;
    private String playOrder;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public String getPlayOrder() {
        return playOrder;
    }

    public List<String> getPlayOrderToReplayPage() {
        List<String> playOrderList = Arrays.asList(playOrder.split(","));
        playOrderList = playOrderList.stream().map(s -> "\"" + s + "\"").toList();
        return playOrderList;
    }

    public void setPlayOrder(String playOrder) {
        this.playOrder = playOrder;
    }

}
