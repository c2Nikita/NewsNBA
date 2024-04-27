package com.nvelich.newsnba.service;

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
        try {
            log.info("Сохранение игрока в базу данных: {}", player);
            Player savedPlayer = playerRepository.save(player);
            playerCache.addPlayer(savedPlayer.getName(), savedPlayer); // Добавление в кэш
            log.info("Игрок успешно сохранен: {}", savedPlayer);
            return savedPlayer;
        } catch (Exception ex) {
            log.error("Ошибка при сохранении игрока: {}", ex.getMessage());
            throw new RuntimeException("Ошибка при сохранении игрока", ex);
        }
    }

    public List<Player> getAllPlayers() {
        try {
            log.info("Запрос всех игроков из базы данных");
            List<Player> players = playerRepository.findAll();
            for (Player player : players) {
                playerCache.addPlayer(player.getName(), player); // Добавление в кэш
            }
            log.info("Получено {} игроков", players.size());
            return players;
        } catch (Exception ex) {
            log.error("Ошибка при получении всех игроков: {}", ex.getMessage());
            throw new RuntimeException("Ошибка при получении всех игроков", ex);
        }
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
            return player;

    }

    @Transactional
    public void deletePlayerById(Long id) {
        try {
            log.info("Удаление игрока по ID: {}", id);
            Player player = getPlayerById(id); // Проверяем, существует ли игрок с таким ID
            playerRepository.delete(player);
            playerCache.removePlayer(String.valueOf(id)); // Удаление из кэша
            log.info("Игрок успешно удален по ID: {}", id);
        } catch (NotFoundException ex) {
            throw ex; // Пробрасываем исключение NotFoundException дальше
        } catch (Exception ex) {
            log.error("Ошибка при удалении игрока по ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Ошибка при удалении игрока по ID", ex);
        }
    }
}
