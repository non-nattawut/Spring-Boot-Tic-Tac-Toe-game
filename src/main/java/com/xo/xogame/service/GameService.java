package com.xo.xogame.service;

import com.xo.xogame.gameEnum.buttonEnum;
import com.xo.xogame.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    // Create global var mean all web page will refer same data, for further adjust like real webpage, need to create session for every player
    // and keep this data in database like sessionID, and get - store this data for that session
    List<List<String>> buttonTable = new ArrayList<>();
    Integer row = 3;
    Integer col = 3;
    Integer round = 1; // X = odd, O = even
    String play_order = "";

    @Autowired
    ReplayService replayService;

    public List<List<String>> createButtonTable(){
        List<List<String>> newButtonTable = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            List<String> new_row = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                if (!buttonTable.isEmpty()){
                    new_row.add(buttonTable.get(i).get(j));
                }else{
                    new_row.add(i + buttonEnum.getCell_separator() + j);
                }
            }
            newButtonTable.add(new_row);
        }
        buttonTable = newButtonTable;
        return buttonTable;
    }

    public String checkWinner(){
        if (buttonTable.isEmpty()){
            return null;
        }

        // Horizontal and Diagonal win
        for (int i = 0; i < row; i++) {
            String winner = checkWinnerHorizontal(buttonTable.get(i), 1, col);

            // fixed col
            if (winner == null && i <= row - col){
                winner = checkWinnerDiagonalLeftToRight(i, 0, i, 0, col);
            }
            // fixed col
            if (winner == null && i <= row - col){
                winner = checkWinnerDiagonalRightToLeft(i, col - 1, i, col - 1, row);
            }

            if (winner != null){
                return winner;
            }
        }

        // Diagonal win
        for (int i = 0; i < col; i++) {
            // fixed row
            if (i <= col - row){
                String winner = checkWinnerDiagonalLeftToRight(0, i, 0, i, row + i);
                if (winner != null){
                    return winner;
                }
            }
            // fixed row
            if (i >= row - 1){
                String winner = checkWinnerDiagonalRightToLeft(0, i, 0, i, row);
                if (winner != null){
                    return winner;
                }
            }
        }

        // Vertical win
        List<List<String>> transpose = Util.transpose(buttonTable);
        int t_row = transpose.size();
        int t_col = transpose.getFirst().size();
        for (int i = 0; i < t_row; i++) {
            String winner = checkWinnerHorizontal(transpose.get(i), 1, t_col);

            if (winner != null){
                return winner;
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (buttonTable.get(i).get(j).contains(buttonEnum.getCell_separator())){
                    return null;
                }
            }
        }

        return "DRAW";
    }

    private String checkWinnerHorizontal(List<String> buttonTable1D, int j, int col){
        if(buttonTable1D.get(j).equalsIgnoreCase(buttonTable1D.getFirst())){
            if (j == col - 1){
                return buttonTable1D.getFirst();
            }
            return checkWinnerHorizontal(buttonTable1D, j + 1, col);
        }else{
            return null;
        }
    }

    private String checkWinnerDiagonalLeftToRight(int i, int j, int init_i, int init_j, int bound){
        if (buttonTable.get(i).get(j).equalsIgnoreCase(buttonTable.get(init_i).get(init_j))){
            if (j == bound - 1){
                return buttonTable.get(init_i).get(init_j);
            }
            return checkWinnerDiagonalLeftToRight(i + 1, j + 1,init_i, init_j, bound);
        }else{
            return null;
        }
    }

    private String checkWinnerDiagonalRightToLeft(int i, int j, int init_i, int init_j, int row){
        if (buttonTable.get(i).get(j).equalsIgnoreCase(buttonTable.get(init_i).get(init_j))){
            if (j == 0 || j == init_j - (row - 1)){ // j == 0 for fixed row case
                return buttonTable.get(init_i).get(init_j);
            }
            return checkWinnerDiagonalRightToLeft(i + 1, j - 1,init_i, init_j, row);
        }else{
            return null;
        }
    }

    public void setWinnerAlert(Model model, String winner){
        if(winner != null && winner.equalsIgnoreCase("DRAW")){
            replayService.saveReplay(winner, row, col, play_order);
            resetValue();
            model.addAttribute("alertMessage", "DRAW!");
        }else if(winner != null && (winner.equalsIgnoreCase("X") || winner.equalsIgnoreCase("O"))){
            replayService.saveReplay(winner, row, col, play_order);
            resetValue();
            model.addAttribute("alertMessage", winner + " WIN THIS GAME!");
        }
    }

    public void adjustButtonTable(String buttonValue){
        if (buttonValue.contains(buttonEnum.getCell_separator())){
            if (round == 1){
                play_order += buttonValue;
            }else{
                play_order += buttonEnum.getButton_separator() + buttonValue;
            }
            String[] split = buttonValue.split(buttonEnum.getCell_separator());
            buttonTable.get(Integer.parseInt(split[0])).set(Integer.parseInt(split[1]), Util.getPlayer(round));
            round++;
        }

        System.out.println(play_order);
    }

    public void resetValue(Integer row_value, Integer col_value){
        play_order = "";
        buttonTable = new ArrayList<>();
        row = row_value;
        col = col_value;
        round = 1;
    }

    public void resetValue(){
        resetValue(3,3);
    }

    public Integer getRound() {
        return round;
    }
}
