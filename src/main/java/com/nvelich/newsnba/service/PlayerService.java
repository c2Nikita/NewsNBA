package com.nvelich.newsnba.service;

import com.nvelich.newsnba.models.Player;
import com.nvelich.newsnba.repositories.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElse(null);
    }
    @Transactional
    public void deletePlayerById(Long id) {
        playerRepository.deleteById(id);
    }
}