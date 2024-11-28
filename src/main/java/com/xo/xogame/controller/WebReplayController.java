package com.xo.xogame.controller;

import com.xo.xogame.dto.ReplayDTO;
import com.xo.xogame.service.ReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.xo.xogame.gameEnum.buttonEnum;

import java.util.Arrays;
import java.util.List;

@Controller
public class WebReplayController {
    @Autowired
    private ReplayService replayService;

    @GetMapping("/replays")
    public String mainPage(Model model) {
        List<ReplayDTO> replayDTOList = replayService.getAllReplay();
        model.addAttribute("replays", replayDTOList);
        return "replay";
    }

    @PostMapping("/watchReplay")
    public String watchReplay(@ModelAttribute ReplayDTO replayDTO, Model model) {
        model.addAttribute("replay", replayDTO);
        List<String> moves = List.of(replayDTO.getPlayOrder().split(buttonEnum.getButton_separator()));
        System.out.println(moves);
        model.addAttribute("winner", replayDTO.getWinner());
        model.addAttribute("moves", moves);
        return "watchReplay";
    }
}
