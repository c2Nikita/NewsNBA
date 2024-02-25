package com.nvelich.newsnba.services.database;

import com.nvelich.newsnba.services.database.model.Player;
import com.nvelich.newsnba.services.database.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerDatabaseService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerDatabaseService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Integer getSearchQueriesCountForPlayer(String username) {
        Player player = playerRepository.findByUsername(username);

        if (player != null) {
            return player.getSearchQueriesCount();
        } else {
            return 0;
        }
    }

    public void updateSearchQueriesCountForPlayer(String username) {
        Player player = playerRepository.findByUsername(username);

        if (player != null) {
            Integer currentCount = player.getSearchQueriesCount();
            player.setSearchQueriesCount(currentCount + 1);
            playerRepository.save(player);
        } else {
            // create new player if he does not exist
            Player newPlayer = new Player();
            newPlayer.setUsername(username);
            newPlayer.setSearchQueriesCount(1);
            playerRepository.save(newPlayer);
        }
    }
}
