package com.xo.xogame.controller;

import com.xo.xogame.gameEnum.buttonEnum;
import com.xo.xogame.repository.ReplayRepository;
import com.xo.xogame.service.GameService;
import com.xo.xogame.service.ReplayService;
import com.xo.xogame.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {
    List<List<String>> buttonLabels = new ArrayList<>();
    Integer row = 3;
    Integer col = 3;
    Integer round = 1; // X = odd, O = even
    String play_order = "";

    @Autowired
    private ReplayService replayService;

    @GetMapping("/game")
    public String mainPage(Model model) {
        String winner = GameService.checkWinner(buttonLabels, row, col);
        if(winner != null && winner.equalsIgnoreCase("DRAW")){
            replayService.saveReplay(winner, row, col, play_order);
            resetValue();
            model.addAttribute("alertMessage", "DRAW!");
        }else if(winner != null && (winner.equalsIgnoreCase("X") || winner.equalsIgnoreCase("O"))){
            replayService.saveReplay(winner, row, col, play_order);
            resetValue();
            model.addAttribute("alertMessage", winner + " WIN THIS GAME!");
        }

        // Pass data to the view
        model.addAttribute("message", "Tic Tac Toe GAME!");
        model.addAttribute("round", "Round " + round + " : It's " + Util.getPlayer(round) + " Turn!");

        List<List<String>> new_buttonLabels = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            List<String> new_row = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                if (!buttonLabels.isEmpty()){
                    new_row.add(buttonLabels.get(i).get(j));
                }else{
                    new_row.add(i + buttonEnum.getCell_separator() + j);
                }
            }
            new_buttonLabels.add(new_row);
        }
        buttonLabels = new_buttonLabels;
        model.addAttribute("buttons", buttonLabels);
        return "game"; // Refers to the game.html file in the templates folder
    }

    @PostMapping("/submit-button")
    public String handleButtonClick(Model model, @RequestParam("buttonValue") String buttonValue) {
        System.out.println("Button clicked: " + buttonValue);
        if (buttonValue.contains(buttonEnum.getCell_separator())){
            if (round == 1){
                play_order += buttonValue;
            }else{
                play_order += buttonEnum.getButton_separator() + buttonValue;
            }
            String[] split = buttonValue.split(buttonEnum.getCell_separator());
            buttonLabels.get(Integer.parseInt(split[0])).set(Integer.parseInt(split[1]), Util.getPlayer(round));
            round++;
        }

        System.out.println(play_order);
        return "redirect:/game"; // Redirect to another page or reload
    }

    @PostMapping("submit-rowcol")
    public String handleRowColInput(@RequestParam("rowValue") int rowValue, @RequestParam("colValue") int colValue) {
        System.out.println("Received integer: " + rowValue);
        resetValue(rowValue, colValue);
        return "redirect:/game"; // Redirect to a success page
    }

    @PostMapping("submit-reset")
    public String handleResetGame(){
        resetValue();
        return "redirect:/game";
    }

    public void resetValue(Integer row_value, Integer col_value){
        play_order = "";
        buttonLabels = new ArrayList<>();
        row = row_value;
        col = col_value;
        round = 1;
    }

    public void resetValue(){
        resetValue(3,3);
    }
}
