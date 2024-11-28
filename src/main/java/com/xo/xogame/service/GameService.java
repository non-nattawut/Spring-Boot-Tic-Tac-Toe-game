package com.xo.xogame.service;

import com.xo.xogame.gameEnum.buttonEnum;
import com.xo.xogame.util.Util;

import java.util.ArrayList;
import java.util.List;

public class GameService {
    public static String checkWinner(List<List<String>> buttonLabels, int row, int col){
        if (buttonLabels.isEmpty()){
            return null;
        }

        // Horizontal and Diagonal win
        for (int i = 0; i < row; i++) {
            String winner = checkWinnerHorizontal(buttonLabels.get(i), 1, col);

            // fixed col
            if (winner == null && i <= row - col){
                winner = checkWinnerDiagonalLeftToRight(buttonLabels, i, 0, i, 0, col);
            }
            // fixed col
            if (winner == null && i <= row - col){
                winner = checkWinnerDiagonalRightToLeft(buttonLabels, i, col - 1, i, col - 1, row);
            }

            if (winner != null){
                return winner;
            }
        }

        // Diagonal win
        for (int i = 0; i < col; i++) {
            // fixed row
            if (i <= col - row){
                String winner = checkWinnerDiagonalLeftToRight(buttonLabels, 0, i, 0, i, row + i);
                if (winner != null){
                    return winner;
                }
            }
            // fixed row
            if (i >= row - 1){
                String winner = checkWinnerDiagonalRightToLeft(buttonLabels,0, i, 0, i, row);
                if (winner != null){
                    return winner;
                }
            }
        }

        // Vertical win
        List<List<String>> transpose = Util.transpose(buttonLabels);
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
                if (buttonLabels.get(i).get(j).contains(buttonEnum.getCell_separator())){
                    return null;
                }
            }
        }

        return "DRAW";
    }

    private static String checkWinnerHorizontal(List<String> buttonLabels, int j, int col){
        if(buttonLabels.get(j).equalsIgnoreCase(buttonLabels.getFirst())){
            if (j == col - 1){
                return buttonLabels.getFirst();
            }
            return checkWinnerHorizontal(buttonLabels, j + 1, col);
        }else{
            return null;
        }
    }

    private static String checkWinnerDiagonalLeftToRight(List<List<String>> buttonLabels, int i, int j, int init_i, int init_j, int bound){
        if (buttonLabels.get(i).get(j).equalsIgnoreCase(buttonLabels.get(init_i).get(init_j))){
            if (j == bound - 1){
                return buttonLabels.get(init_i).get(init_j);
            }
            return checkWinnerDiagonalLeftToRight(buttonLabels, i + 1, j + 1,init_i, init_j, bound);
        }else{
            return null;
        }
    }

    private static String checkWinnerDiagonalRightToLeft(List<List<String>> buttonLabels, int i, int j, int init_i, int init_j, int row){
        if (buttonLabels.get(i).get(j).equalsIgnoreCase(buttonLabels.get(init_i).get(init_j))){
            if (j == 0 || j == init_j - (row - 1)){ // j == 0 for fixed row case
                return buttonLabels.get(init_i).get(init_j);
            }
            return checkWinnerDiagonalRightToLeft(buttonLabels, i + 1, j - 1,init_i, init_j, row);
        }else{
            return null;
        }
    }
}
