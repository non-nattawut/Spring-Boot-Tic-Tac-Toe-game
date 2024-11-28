package com.xo.xogame.service;

import com.xo.xogame.dto.ReplayDTO;
import com.xo.xogame.model.Replay;
import com.xo.xogame.repository.ReplayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplayService {

    @Autowired
    private ReplayRepository replayRepository;

    public List<ReplayDTO> getAllReplay(){
        Iterable<Replay> replayEntities = replayRepository.findAll();

        List<ReplayDTO> replayDTOs = new ArrayList<>();
        for (Replay replay : replayEntities) {
            replayDTOs.add(mapToReplayDTO(replay));
        }

        return replayDTOs;
    }

    @Transactional
    public void saveReplay(String winner, int game_row, int game_col, String play_order) {
        Replay replay = new Replay();
        replay.setWinner(winner);
        replay.setGameRow(game_row);
        replay.setGameCol(game_col);
        replay.setPlayOrder(play_order);

        // Save the replay, the id will be auto-generated
        replayRepository.save(replay);
    }

    private ReplayDTO mapToReplayDTO(Replay replay) {
        ReplayDTO dto = new ReplayDTO();
        dto.setId(replay.getId());
        dto.setWinner(replay.getWinner());
        dto.setRow(replay.getGameRow());
        dto.setCol(replay.getGameCol());
        dto.setPlayOrder(replay.getPlayOrder());
        return dto;
    }
}
