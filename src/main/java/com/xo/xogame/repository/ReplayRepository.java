package com.xo.xogame.repository;

import com.xo.xogame.model.Replay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplayRepository extends CrudRepository<Replay, Integer> {
    @Override
    Iterable<Replay> findAll();

    @Override
    Optional<Replay> findById(Integer integer);

    Optional<Replay> findByWinner(String winner);
}
