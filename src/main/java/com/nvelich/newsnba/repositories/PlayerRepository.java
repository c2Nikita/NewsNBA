package com.nvelich.newsnba.repositories;

import com.nvelich.newsnba.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    // Additional custom queries for Player entity
}
