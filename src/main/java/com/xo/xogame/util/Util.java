package com.xo.xogame.util;

import java.util.ArrayList;
import java.util.List;

public class Util {

    // odd round = X, even round = O
    public static String getPlayer(Integer round){
        return round%2 == 0 ? "O" : "X";
    }

    public static List<List<String>> transpose(List<List<String>> matrix) {
        if (matrix.isEmpty() || matrix.getFirst().isEmpty()) {
            return new ArrayList<>(); // Return an empty list for empty input
        }

        int rows = matrix.size();
        int cols = matrix.getFirst().size();

        // Initialize the transposed list with empty lists
        List<List<String>> transposed = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            transposed.add(new ArrayList<>());
        }

        // Fill the transposed list
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed.get(j).add(matrix.get(i).get(j));
            }
        }

        return transposed;
    }
}
