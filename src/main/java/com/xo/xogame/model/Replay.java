package com.xo.xogame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "replay")
public class Replay {
    @Id
    @JsonProperty(value = "id")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty(value="winner")
    @Column(name = "winner")
    private String winner;

    @JsonProperty(value="gameRow")
    @Column(name = "gameRow")
    private Integer gameRow;

    @JsonProperty(value="gameCol")
    @Column(name = "gameCol")
    private Integer gameCol;

    @JsonProperty(value="playOrder")
    @Column(name = "playOrder")
    private String playOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Integer getGameRow() {
        return gameRow;
    }

    public void setGameRow(Integer game_row) {
        this.gameRow = game_row;
    }

    public Integer getGameCol() {
        return gameCol;
    }

    public void setGameCol(Integer game_col) {
        this.gameCol = game_col;
    }

    public String getPlayOrder() {
        return playOrder;
    }

    public void setPlayOrder(String play_order) {
        this.playOrder = play_order;
    }
}
