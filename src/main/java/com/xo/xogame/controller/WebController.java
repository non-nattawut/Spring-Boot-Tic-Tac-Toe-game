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
    @Autowired
    private GameService gameService;

    @GetMapping("/game")
    public String mainPage(Model model) {
        gameService.setWinnerAlert(model, gameService.checkWinner());
        model.addAttribute("message", "Tic Tac Toe GAME!");
        model.addAttribute("round", "Round " + gameService.getRound() + " : It's " + Util.getPlayer(gameService.getRound()) + " Turn!");
        model.addAttribute("buttons", gameService.createButtonTable());
        return "game"; // Refers to the game.html file in the templates folder
    }

    @PostMapping("/submit-button")
    public String handleButtonClick(Model model, @RequestParam("buttonValue") String buttonValue) {
        System.out.println("Button clicked: " + buttonValue);
        gameService.adjustButtonTable(buttonValue);
        return "redirect:/game"; // Redirect to another page or reload
    }

    @PostMapping("submit-rowcol")
    public String handleRowColInput(@RequestParam("rowValue") int rowValue, @RequestParam("colValue") int colValue) {
        System.out.println("Received integer: " + rowValue);
        gameService.resetValue(rowValue, colValue);
        return "redirect:/game"; // Redirect to a success page
    }

    // reset value should and be in game service
    @PostMapping("submit-reset")
    public String handleResetGame(){
        gameService.resetValue();
        return "redirect:/game";
    }
}
