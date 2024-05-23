package com.nvelich.newsnba.service;

import com.nvelich.newsnba.exceptions.YourFriendly404Exception;
import com.nvelich.newsnba.models.Player;
import com.nvelich.newsnba.repositories.PlayerRepository;
import com.nvelich.newsnba.cache.PlayerCache;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerCache playerCache;

    @Transactional
    public Player savePlayer(Player player) {
        log.info("Сохранение игрока в базу данных: {}", player);
        Player savedPlayer = playerRepository.save(player);
        playerCache.addPlayer(savedPlayer.getName(), savedPlayer); // Добавление в кэш
        log.info("Игрок успешно сохранен: {}", savedPlayer);
        return savedPlayer;

    }

    public List<Player> getAllPlayers() {

        log.info("Запрос всех игроков из базы данных");
        List<Player> players = playerRepository.findAll();
        for (Player player : players) {
            playerCache.addPlayer(player.getName(), player); // Добавление в кэш
        }
        log.info("Получено {} игроков", players.size());
        return players;

    }

    public Player getPlayerById(Long id) {

            log.info("Запрос игрока по ID: {}", id);
            Player player = playerCache.getPlayer(String.valueOf(id));
            if (player == null) {
                player = playerRepository.findById(id).orElse(null);
                if (player != null) {
                    playerCache.addPlayer(player.getName(), player); // Добавление в кэш
                }
            }
            if (player != null) {
                return player;
            } else {
                log.info("Игрок не найден в базе по ID:{}", id);
                throw new YourFriendly404Exception("Игрок не найден в базе", 404);
            }
    }

    @Transactional
    public void deletePlayerById(Long id) {

        log.info("Удаление игрока по ID: {}", id);
        Player player = getPlayerById(id);
        playerRepository.delete(player);
        playerCache.removePlayer(String.valueOf(id));
        log.info("Игрок успешно удален по ID: {}", id);

    }

    @Transactional
    public void savePlayers(List<Player> playerList) {
        playerList.stream()
                .forEach(player -> {
                    log.info("Сохранение игрока в базу данных: {}", player);
                    Player savedPlayer = playerRepository.save(player);
                    playerCache.addPlayer(savedPlayer.getName(), savedPlayer); // Добавление в кэш
                    log.info("Игрок успешно сохранен: {}", savedPlayer);
                });
    }
}
