package com.nvelich.newsnba.cache;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.nvelich.newsnba.models.Player;

@Component
public class PlayerCache {

    private final Map<String, Player> playerMap = new ConcurrentHashMap<>();

    public Player getPlayer(String playerName) {
        return playerMap.get(playerName);
    }

    public void addPlayer(String playerName, Player player) {
        playerMap.put(playerName, player);
    }

    public void removePlayer(String playerName) {
        playerMap.remove(playerName);
    }
}