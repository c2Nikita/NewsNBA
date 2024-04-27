package com.nvelich.newsnba.cache;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.nvelich.newsnba.models.Player;

@Component
public class PlayerCache {

    public static final int MAX_CACHE_SIZE = 15;
    private final Map<String, Player> playerMap = new ConcurrentHashMap<>();

    public Player getPlayer(String playerName) {
        return playerMap.get(playerName);
    }

    public void addPlayer(String playerName, Player player) {
        if (playerMap.size() >= MAX_CACHE_SIZE) {
            evictOldestPlayer(); // Удаление старого игрока
        }
        playerMap.put(playerName, player);
    }

    private void evictOldestPlayer() {
        if (!playerMap.isEmpty()) {
            String oldestPlayerName = playerMap.keySet().iterator().next();
            playerMap.remove(oldestPlayerName);
        }
    }

    public void removePlayer(String playerName) {
        playerMap.remove(playerName);
    }
}