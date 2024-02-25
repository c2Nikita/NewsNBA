package com.nvelich.newsnba.services.database.repository;

import com.nvelich.newsnba.services.database.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByUsername(String username);
}
